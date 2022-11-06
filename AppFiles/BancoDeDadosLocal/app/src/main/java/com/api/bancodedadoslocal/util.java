package com.api.bancodedadoslocal;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class util {

    public static String sysdate(){
        return formatDate(Calendar.getInstance());
    }

    public static String formatDate(Calendar date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date.getTime()).toString();
    }


}
