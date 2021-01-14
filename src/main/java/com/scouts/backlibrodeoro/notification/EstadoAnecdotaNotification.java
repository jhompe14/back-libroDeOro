package com.scouts.backlibrodeoro.notification;

import com.scouts.backlibrodeoro.model.Anecdota;
import com.scouts.backlibrodeoro.model.Usuario;
import com.scouts.backlibrodeoro.types.TypeEstadoAnecdota;
import com.scouts.backlibrodeoro.util.LibroOroUtil;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EstadoAnecdotaNotification implements Runnable{

    private Anecdota anecdota;
    private TypeEstadoAnecdota typeEstadoAnecdota;
    private Usuario usuario;
    private final String SUBJECT_EMAIL = "subject";
    private final String CONTENT_EMAIL_OWNER_ANECDOTA = "content_owner_anecdota";
    private final String CONTENT_EMAIL_IN_CHANGE_ANECDOTA = "content_in_change_anecdota";

    public EstadoAnecdotaNotification(Anecdota anecdota, TypeEstadoAnecdota typeEstadoAnecdota, Usuario usuario) {
        this.anecdota = anecdota;
        this.typeEstadoAnecdota = typeEstadoAnecdota;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        Map<String, String> messageEmail = getMessageEmailByEstadoAnecdota();
        try {
            LibroOroUtil.sendEmail(anecdota.getUsuario().getCorreo(), messageEmail.get(SUBJECT_EMAIL),
                    messageEmail.get(CONTENT_EMAIL_OWNER_ANECDOTA));
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        if(typeEstadoAnecdota.equals(TypeEstadoAnecdota.PA) || typeEstadoAnecdota.equals(TypeEstadoAnecdota.PM)) {
            try {
                LibroOroUtil.sendEmail(usuario.getCorreo(),  messageEmail.get(SUBJECT_EMAIL),
                        messageEmail.get(CONTENT_EMAIL_IN_CHANGE_ANECDOTA));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

    private Map<String, String> getMessageEmailByEstadoAnecdota(){
        Map<String, String> contentMailMap= new HashMap<>();
        contentMailMap.put(SUBJECT_EMAIL, "LA AN\u00C9CDOTA CON CODIGO "+anecdota.getId()+" HA SIDO CAMBIADA DE ESTADO");

        StringBuilder messageOwnerAnecdota = new StringBuilder()
                .append(getMessageGenericEmailByEstadoAnecdota());
        StringBuilder messageInChangeAnecdota = new StringBuilder()
                .append(getMessageGenericEmailByEstadoAnecdota());

        switch (typeEstadoAnecdota){
            case PA:
                messageOwnerAnecdota.append("<br> Espere que el administrador del sistema la apruebe o la rechace ");
                messageInChangeAnecdota.append("<br> Ingrese por favor al listado de an\u00E9cdotas para aprobarla o rechazarla ");
                break;
            case AP:
                messageOwnerAnecdota.append("<br> Ya se podr\u00E1 visualizar la an\u00E9codta en el libro de oro ");
                break;
            case RE:
                messageOwnerAnecdota.append("<br> La an\u00E9codta no se podr\u00E1 visualizar en el libro de oro ni ser modificada, " +
                        "para mas informaci\u00F3n por favor comun\u00EDquese con el administrador ");
                break;
            case PM:
                messageOwnerAnecdota.append("<br> La an\u00E9cdota ser\u00E1 cambiada por el Usuario: <b>"+usuario.getUsuario()+"</b> " +
                        "<br> Nombre: <b>"+usuario.getNombres()+" "+usuario.getApellidos()+"</b> ");
                messageInChangeAnecdota.append("<br> Esta ha sido asignada a usted, por favor Ingrese por favor al " +
                        "listado de an\u00E9cdotas a realizar los cambios pertinentes ");
                break;
        }

        contentMailMap.put(CONTENT_EMAIL_OWNER_ANECDOTA, messageOwnerAnecdota.toString());
        contentMailMap.put(CONTENT_EMAIL_IN_CHANGE_ANECDOTA, messageInChangeAnecdota.toString());

        return contentMailMap;
    }

    private String getMessageGenericEmailByEstadoAnecdota(){
        StringBuilder messageGenericContent = new StringBuilder();
        messageGenericContent.append("La an\u00E9cdota con codigo <b>"+anecdota.getId()+"</b> ");
        if(Optional.ofNullable(anecdota.getNombre()).isPresent()){
            messageGenericContent.append("y con nombre <b>"+anecdota.getNombre()+"</b> ");
        }
        messageGenericContent.append("ha sido actualizada al estado <b>"+typeEstadoAnecdota.getValue()+"</b> ");
        return messageGenericContent.toString();
    }
}
