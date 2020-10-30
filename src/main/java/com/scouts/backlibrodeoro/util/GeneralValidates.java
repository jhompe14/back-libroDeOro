package com.scouts.backlibrodeoro.util;

import com.scouts.backlibrodeoro.exception.NegocioException;
import com.scouts.backlibrodeoro.types.TypeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralValidates {

    private static final String FORMAT_DATE = "dd/MM/yyyy";

    public static boolean validateStringNotIsEmpty(String valor){
        return Optional.ofNullable(valor).isPresent() &&
                !valor.isEmpty();
    }

    public static boolean validateCorreoIsCorrect(String correo){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(correo);
        return mather.find();
    }

    public static Date validateFormatDate(String fecha){
        return Optional.ofNullable(fecha).map(f -> {
            try {
                if(!f.isEmpty()){
                    return new SimpleDateFormat(FORMAT_DATE).parse(f);
                }
            } catch (ParseException e) {
                try {
                    throw new NegocioException(MessagesValidation.VALIDATION_FORMAT_FECHA, TypeException.VALIDATION);
                } catch (NegocioException negocioException) {
                    throw new RuntimeException(negocioException);
                }
            }
            return null;
        }).orElse(null);
    }

}
