package com.spro.interceptor;

import com.spro.util.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

public class TokenInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JedisUtil jedisUtil = JedisUtil.getInstance();
        JedisUtil.Keys keys = jedisUtil.new Keys();
        //实例化jedis Strings对象
        JedisUtil.Strings jedis = jedisUtil.new Strings();
        logger.info("校验token>>>>>>>>>>>>>>>>>>>>>>>>>>");
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.save();
                if (needSaveSession) {
                    try {
                        jedis.set("formTokenKey", UUID.randomUUID().toString());
                        jedis.set("formTokenValue", UUID.randomUUID().toString());
                        //request.getSession().setAttribute("token", UUID.randomUUID().toString());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                boolean needRemoveSession = annotation.remove();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                        return false;
                    }
                    keys.del("token");
                   // request.getSession().removeAttribute("token");
                }
            }
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        JedisUtil jedisUtil = JedisUtil.getInstance();
        //实例化jedis Strings对象
        JedisUtil.Strings jedis = jedisUtil.new Strings();
        String serverToken = "";
        try{
            serverToken = (String) jedis.get("token");
            //serverToken = (String) request.getSession().getAttribute("token");
        } catch (Exception e){
            e.printStackTrace();
        }
        if (serverToken == null) {
            return true;
        }
        /*String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }*/
        return false;
    }
}