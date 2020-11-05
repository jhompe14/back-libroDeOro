package com.scouts.backlibrodeoro.validator;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.model.Trayectoria;
import com.scouts.backlibrodeoro.types.TypeException;
import com.scouts.backlibrodeoro.util.MessagesValidation;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
public class TrayectoriaValidator implements IValidator{
    @Override
    public <T> void validator(T trayectoria) throws NegocioException {
        Trayectoria trayectoriaValidation = (Trayectoria) trayectoria;
        if(!validateRequired(trayectoriaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_TRAYECTORIA_OBLIGATORIO,
                    TypeException.VALIDATION);
        }
        if(!validateAnioIngresoValid(trayectoriaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_ANIO_INGRESO_VALIDA,
                    TypeException.VALIDATION);
        }
        if(!validateAnioIngresoMajorToAnioRetiro(trayectoriaValidation)){
            throw new NegocioException(MessagesValidation.VALIDATION_ANIO_INGRESO_MAJOR_TO_ANIO_RETIRO,
                    TypeException.VALIDATION);
        }

    }

    private Boolean validateRequired(Trayectoria trayectoriaValidation){
        return Optional.ofNullable(trayectoriaValidation).map(r ->
                    Optional.ofNullable(r.getAnioIngreso()).isPresent() &&
                    Optional.ofNullable(r.getGrupo()).isPresent()
        ).orElse(false);
    }

    private Boolean validateAnioIngresoValid(Trayectoria trayectoriaValidation){
        return Optional.ofNullable(trayectoriaValidation).map(r -> r.getAnioIngreso() >= 1940 &&
                r.getAnioIngreso() <= getCurrentYear()).orElse(false);
    }

    private Boolean validateAnioIngresoMajorToAnioRetiro(Trayectoria trayectoriaValidation){
        return Optional.ofNullable(trayectoriaValidation).map(r ->
                Optional.ofNullable(r.getAnioRetiro()).isPresent() &&
                (r.getAnioRetiro() == 0 ||
                r.getAnioIngreso() <= r.getAnioRetiro())).orElse(false);
    }

    private Integer getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.YEAR);
    }
}
