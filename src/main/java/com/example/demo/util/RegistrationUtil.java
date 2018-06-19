package com.example.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationUtil {

    private RegistrationUtil() {
    }

    public static boolean checkPhoneNumber(String phone) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(phone);
        if (phone.isEmpty() || !m.matches()) {
            return false;
        }
        return true;
    }
    
}
