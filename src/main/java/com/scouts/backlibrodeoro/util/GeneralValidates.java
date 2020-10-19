package com.scouts.backlibrodeoro.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralValidates {

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

}
