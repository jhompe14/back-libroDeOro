package com.scouts.backlibrodeoro.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class UtilLibroOro {

    private static final String FORMAT_DATE = "dd/MM/yyyy";

    public static String setFormatDate(Date date){
        return Optional.ofNullable(date).map(d -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_DATE);
            return simpleDateFormat.format(d);
        }).orElse("");
    }

}
