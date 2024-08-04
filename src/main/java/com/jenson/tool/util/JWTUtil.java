package com.jenson.tool.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    private static final String secretKey = "jenson";

    /**
     * 获取token
     *
     * @param map 数据
     * @return token
     */
    public static String getToken(Map<String, String> map) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 2);

        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);
        return builder
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * 通过token查询用户
     *
     * @param token token
     * @return HashMap(string, string)
     */
    public static Map<String, String> getContext(String token) {
        Map<String,String> map = new HashMap<>();
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(secretKey))
                .build()
                .verify(token);
        verify.getClaims().forEach((key,value) ->{
            map.put(key, String.valueOf(verify.getClaim(key)));
        });
        return  map;

    }

}
