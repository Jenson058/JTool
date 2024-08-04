package com.jenson.tool.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogUtil {

    public static void info(String value){
        log.info(value);
    }

    public static void war(String value){
        log.warn(value);
    }

    public static void err(String value){
        log.error(value);
    }

}
