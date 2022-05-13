package com.mcr.usercenter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static final String SIGN = "user-center";

    /**
     * 生成token
     *
     * @param map 用户信息
     * @return token
     */
    public static String getToken(Map<String,String> map){
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,1);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(SIGN));
    }

    /**
     * toke认证
     *
     * @param token token令牌
     * @return 用户信息
     */
    public static DecodedJWT verify(String token){
        JWTVerifier build = JWT.require(Algorithm.HMAC256(SIGN)).build();
        return build.verify(token);
    }

    /**
     * 获取用户id
     *
     * @param request
     * @return 用户id
     */
    public static Long getUserId(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        Claim idClaim = verify.getClaim("id");
        JsonNode id = idClaim.as(JsonNode.class);
        return id.asLong();
    }

    /**
     * 获取用户身份
     *
     * @param request
     * @return 用户身份码
     */
    public static int getUserRole(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return Integer.parseInt(verify.getClaim("userRole").asString());
    }
}
