package com.yijie.com.kindergartenapp.utils;

public class CheckUserUtils {
    public static boolean checkString(String s) {
        //^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$  正则表达式：必须包含数字和字母，长度为6位-16位
        return s.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$");
    }

}
