package com.yangwei.common.utils;

/**
 * @author 杨威
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str 输入的字符串
     * @return  返回布尔值
     */
    public static boolean isEmpty(String str) {
        return (null == str) || (str.trim().length() < 1);
    }
}
