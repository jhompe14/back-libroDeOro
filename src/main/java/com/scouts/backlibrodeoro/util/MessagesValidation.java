package com.scouts.backlibrodeoro.util;

public class MessagesValidation {

    public static final String ERROR_GRUPO_NO_EXISTE = "El grupo no existe";
    public static final String ERROR_RAMA_NO_EXISTE = "La rama no existe";
    public static final String ERROR_SECCION_NO_EXISTE = "La seccion no existe";
    public static final String ERROR_CARGO_NO_EXISTE = "El cargo no existe";

    public static final String VALIDATION_NOMBRE_OBLIGATORIO = "El nombre es obligatorio";
    public static final String VALIDATION_TODOS_CAMPOS_OBLIGATORIOS = "Debe diligenciar todos los campos obligatorios: ";
    public static final String VALIDATION_EDAD_MINIMA_MAXIMA =
            "Revise la edad maxima y minima no debe ser un numero menor a 0 ni superar los 150";
    public static final String VALIDATION_EDAD_MAXIMA_MAJOR_EDAD_MINIMA =
            "La edad minima no puede ser mayor a la edad maxima";

    public static final String VALIDATION_GRUPO_RAMAS_ACTIVAS = "El grupo posee ramas activas";
    public static final String VALIDATION_GRUPO_CARGOS_ACTIVOS = "El grupo posee cargos activos";

    public static final String VALIDATION_RAMA_SECCIONES_ACTIVAS = "La rama posee secciones activas";
    public static final String VALIDATION_RAMA_CARGOS_ACTIVOS = "La rama posee cargos activos";
    public static final String VALIDATION_RAMA_EDAD_MINIMA = "La edad minima es obligatoria";
    public static final String VALIDATION_RAMA_EDAD_MAXIMA = "La edad maxima es obligatoria";


    public static final String VALIDATION_SECCION_CARGOS_ACTIVOS = "La seccion posee cargos activos";

    public static final String NOT_FOUND_USUARIO_CONTRASENA = "Usuario y contrasena incorrectos";
    public static final String DILIGENCIAR_USUARIO_CONTRASENA =
            "Debe diligenciar correctamente usuario y contrase\u00F1a";

    public static final String VALIDATION_USUARIO_EXISTE = "Este usuario ya existe";
    public static final String VALIDATION_CONFIRM_CONTRASENA =
            "La contrase\u00F1a y el confirmar contrase\u00F1a no coinciden";

    public static final String VALIDATION_USUARIO_OBLIGATORIO = "El usuario es obligatorio";
    public static final String VALIDATION_USUARIO_CONTRASENA = "La contrase\u00F1a es obligatoria";
    public static final String VALIDATION_APELLIDO_OBLIGATORIO = "El apellido es obligatorio";
    public static final String VALIDATION_TIPO_INTEGRANTE_OBLIGATORIO = "El tipo de integrante debe ser uno valido";
    public static final String VALIDATION_TIPO_USUARIO_OBLIGATORIO = "El tipo de usuario debe ser uno valido";
    public static final String VALIDATION_CORREO_OBLIGATORIO = "El tipo de correo debe ser uno valido";
    public static final String VALIDATION_TELEFONO_OBLIGATORIO = "El telefono es obligatorio";
    public static final String VALIDATION_DIRECCION_OBLIGATORIO = "La direccion es obligatoria";
    public static final String VALIDATION_CIUDAD_OBLIGATORIO = "La ciudad es obligatoria";

    public static final String VALIDATION_TRAYECTORIA_OBLIGATORIO = "Debe agregar almenos una trayectoria";

    public static final String VALIDATION_TRAYECTORIA_ANIO_INGRESO = "La fecha ingreso en la trayectoria es obligatoria";
    public static final String VALIDATION_ANIO_INGRESO_VALIDA =
            "La fecha ingreso en la trayectoria debe estar entre 1940 y el a\u00F1o actual";
    public static final String VALIDATION_ANIO_INGRESO_MAJOR_TO_ANIO_RETIRO =
            "La fecha ingreso no debe ser mayor a la fecha de retiro en la trayectoria";
}
