package com.study.user.util;

public class StringUtil {
    public static String lPad(String headStr, int num, String padStr) {
        return String.format("%" + num + "s", headStr).replace(" ", padStr);
    }
    public static String rPad(String headStr, int num, String padStr) {
        return String.format("%-" + num + "s", headStr).replace(" ", padStr);
    }
}
