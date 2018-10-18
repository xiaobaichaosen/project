package com.yijie.com.studentapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUserUtils {
    public static boolean checkString(String s) {
        //^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$  正则表达式：必须包含数字和字母，长度为6位-16位
        return s.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$");
    }

    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
