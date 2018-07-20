package com.ins.csdn.tool;

public class StringUtil {

    /**
     * 判断字符串是否为空，是否为空字符串，true 代表是；
     *
     * @param str
     * @return
     */
    public static boolean checkNull(String str) {

        if (str != null && !str.trim().isEmpty() && !str.equals("null")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 判断字符串为null返回""
     * 不为null直接返回
     *
     * @param string
     * @return String
     * @throws
     * @author HongyanShan
     * @date 2017/10/24 13:44
     */
    public static String returnString(String string) {
        if (checkNull(string)) {
            return "";
        }
        return string;
    }
}
