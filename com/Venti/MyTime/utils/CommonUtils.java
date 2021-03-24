package com.Venti.MyTime.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static String getFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }
}
