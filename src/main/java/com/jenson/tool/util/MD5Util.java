package com.jenson.tool.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;


public class MD5Util {

    public static String random16String(){
        return RandomStringUtils.randomAlphabetic(16);
    }

    /**
     * 将字符串加密为MD5值
     *
     * @param text 加密前的字符串
     * @param key 密钥
     * @return 加密后的值
     */
    public static String cryptographicString(String text, String key){
        String secretKey = text + key;
        return DigestUtils.md5Hex(secretKey);
    }

}
