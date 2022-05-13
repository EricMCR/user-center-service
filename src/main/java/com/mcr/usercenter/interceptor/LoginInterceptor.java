package com.mcr.usercenter.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcr.usercenter.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录检查
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        map.put("status",403);
        map.put("success", false);

        if (token == null || token.equals("")) {
            map.put("msg", "请先登录");
        }else {
            try{
                JWTUtils.verify(token);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                map.put("msg","token认证失败");
            }
        }

        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
