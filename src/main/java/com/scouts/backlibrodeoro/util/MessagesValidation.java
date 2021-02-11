package com.scouts.backlibrodeoro.util;

public class MessagesValidation {

    public static final String ERROR_GRUPO_NO_EXISTE = "El grupo no existe";
    public static final String ERROR_RAMA_NO_EXISTE = "La rama no existe";
    public static final String ERROR_SECCION_NO_EXISTE = "La secci\u00F3n no existe";
    public static final String ERROR_CARGO_NO_EXISTE = "El cargo no existe";
    public static final String ERROR_USUARIO_NO_EXISTE = "El usuario no existe";
    public static final String ERROR_ANECDOTA_NO_EXISTE = "La an\u00E9cdota no existe";

    public static final String ERROR_CODIGO_RECOVERED_CONTRASENA_NO_VALIDO = "El c\u00F3digo de recuperaci\u00F3n no esta disponible";
    public static final String ERROR_CORREO_RECEPTOR_NO_VALIDO = "El correo del receptor no es valido";

    public static final String VALIDATION_NOMBRE_OBLIGATORIO = "El nombre es obligatorio";
    public static final String VALIDATION_TODOS_CAMPOS_OBLIGATORIOS = "Debe diligenciar todos los campos obligatorios: ";
    public static final String VALIDATION_EDAD_MINIMA_MAXIMA =
            "Revise la edad m\u00E1xima y m\u00EDnima no debe ser un numero menor a 0 ni superar los 150";
    public static final String VALIDATION_EDAD_MAXIMA_MAJOR_EDAD_MINIMA =
            "La edad m\u00EDnima no puede ser mayor a la edad m\u00E1xima";

    public static final String VALIDATION_GRUPO_RAMAS_ACTIVAS = "El grupo posee ramas activas";
    public static final String VALIDATION_GRUPO_CARGOS_ACTIVOS = "El grupo posee cargos activos";
    public static final String VALIDATION_GRUPO_TRAYECTORIAS_ACTIVAS = "El grupo posee trayectorias activas";

    public static final String VALIDATION_RAMA_SECCIONES_ACTIVAS = "La rama posee secciones activas";
    public static final String VALIDATION_RAMA_CARGOS_ACTIVOS = "La rama posee cargos activos";
    public static final String VALIDATION_RAMA_TRAYECTORIAS_ACTIVAS = "La rama posee trayectorias activas";
    public static final String VALIDATION_RAMA_ANECDOTAS_ACTIVAS = "La rama posee anecdotas activas";
    public static final String VALIDATION_RAMA_EDAD_MINIMA = "La edad m\u00EDnima es obligatoria";
    public static final String VALIDATION_RAMA_EDAD_MAXIMA = "La edad m\u00E1xima es obligatoria";


    public static final String VALIDATION_SECCION_CARGOS_ACTIVOS = "La secci\u00F3n posee cargos activos";
    public static final String VALIDATION_SECCION_TRAYECTORIAS_ACTIVAS = "La secci\u00F3n posee trayectorias activas";
    public static final String VALIDATION_SECCION_ANECDOTAS_ACTIVAS = "La secci\u00F3n posee anecdotas activas";

    public static final String VALIDATION_CARGO_TRAYECTORIAS_ACTIVAS = "EL cargo posee trayectorias activas";

    public static final String NOT_FOUND_USUARIO_CONTRASENA = "Usuario y contrase\u00F1a incorrectos";
    public static final String DILIGENCIAR_USUARIO_CONTRASENA =
            "Debe diligenciar correctamente usuario y contrase\u00F1a";

    public static final String VALIDATION_USUARIO_EXISTE = "Este usuario ya existe";
    public static final String VALIDATION_CONFIRM_CONTRASENA =
            "La contrase\u00F1a y el confirmar contrase\u00F1a no coinciden";

    public static final String VALIDATION_USUARIO_REQUIRED = "El usuario es obligatorio";
    public static final String VALIDATION_USUARIO_CONTRASENA = "La contrase\u00F1a es obligatoria";
    public static final String VALIDATION_APELLIDO_REQUIRED = "El apellido es obligatorio";
    public static final String VALIDATION_TIPO_INTEGRANTE_REQUIRED = "El tipo de integrante debe ser uno valido";
    public static final String VALIDATION_TIPO_USUARIO_REQUIRED = "El tipo de usuario debe ser uno valido";
    public static final String VALIDATION_CORREO_REQUIRED = "El tipo de correo debe ser uno valido";
    public static final String VALIDATION_TELEFONO_REQUIRED =
                                "El tel\u00E9fono es obligatorio y adem\u00E1s debe tener entre 7 y 12 n\u00FAmeros";
    public static final String VALIDATION_DIRECCION_REQUIRED = "La direcci\u00F3n es obligatoria";
    public static final String VALIDATION_CIUDAD_REQUIRED = "La ciudad es obligatoria";

    public static final String VALIDATION_TRAYECTORIA_REQUIRED =
            "Los campos obligatorios son: </br> Grupo </br> Rama </br> A\u00F1o ingreso";
    public static final String VALIDATION_ANIO_INGRESO_VALIDA =
            "La fecha ingreso en la trayectoria debe estar entre 1940 y el a\u00F1o actual";
    public static final String VALIDATION_ANIO_INGRESO_MAJOR_TO_ANIO_RETIRO =
            "La fecha ingreso no debe ser mayor a la fecha de retiro en la trayectoria";

    public static final String VALIDATION_ANECDOTA_DESCRIPCION = "La descripci\u00F3n es obligatoria";
    public static final String VALIDATION_ANECDOTA_FECHA =
            "La fecha de la an\u00E9cdota no puede ser superior a la fecha actual";
    public static final String VALIDATION_ANECDOTA_CATALOG_DATES = "La fecha inicio no puede ser superior a la fecha final";
    public static final String VALIDATION_TIPO_ESTADO_ANECDOTA = "Este estado de an\u00E9cdota no es valido";
    public static final String VALIDATION_RAMA_ANECDOTA = "La an\u00E9cdota debe estar asociada a una rama o secci\u00F3n";
    public static final String VALIDATION_ATTACHED_ANECDOTA = "El archivo adjunto no existe";
    public static final String VALIDATION_ATTACHED_FORMAT_ANECDOTA = "El formato de los adjuntos debe ser .PNG .JPG .JPEG";
    public static final String VALIDATION_VIDEO_FORMAT_ANECDOTA = "El nombre y la url son obligatorios para adjuntar videos";

    public static final String VALIDATION_FORMAT_FECHA = "El formato de la fecha no es el correcto";

    public static final String VALIDATION_ESTADO_VISUALIZACION_ANECDOTA = "El campo Visualizaci\u00F3n es obligatorio";
    public static final String VALIDATION_USUARIO_MODIFICACION_ANECDOTA =
            "Si el estado es pendiente modificaci\u00F3n debe agregar un usuario";

    public static final String VALIDATION_TYPE_CHANGE_CONTRASENA = "Este tipo de cambio de contrase\u00F1a no es valido";
    public static final String VALIDATION_TYPE_CHANGE_CONTRASENA_MODIFICATION_REQUIRED =
            "Los campos obligatorios son </br> Contrase\u00F1a Actual </br> Nueva Contrase\u00F1a </br> Confirmar Contrase\u00F1a ";
    public static final String VALIDATION_TYPE_CHANGE_CONTRASENA_RECOVERED_REQUIRED =
            "Los campos obligatorios son </br> Contrase\u00F1a Actual </br> Nueva Contrase\u00F1a";
    public static final String VALIDATION_ACTUAL_CONTRASENA = "La contrase\u00F1a actual no coincide";
}
