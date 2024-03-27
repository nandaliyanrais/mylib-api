package com.nandaliyan.mylibapi.util;

public class StringUtil {

    public static String formatNameForUrl(String name) {
        return name.toLowerCase().replace(".", "").replace(" ", "-");
    }
    
}
