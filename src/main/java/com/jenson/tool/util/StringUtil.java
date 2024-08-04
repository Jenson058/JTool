package com.jenson.tool.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

public class StringUtil {
    /**
     * 随机字符串
     *
     * @param length 随机数长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * 字符串补位
     *
     * @param str    补位字符串
     * @param length 长度
     * @param number 最后的数
     * @return 字符串
     */
    public static String coverPosition(String str, int length, int number) {
        return String.format(str + "%" + "0" + length + "d", number);
    }

    /**
     * 字符串是否为空或者空字符串
     *
     * @param str 需判断的字符串
     * @return 是/否
     */
    public static Boolean isEmpty(String str) {
        if (Objects.isNull(str))
            return true;
        return Strings.isEmpty(str);
    }
}
