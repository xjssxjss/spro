package com.spro.interceptor;


import com.spro.common.BaseObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @description 防止表单重复提交拦截器
 */
@Aspect
@Component
public class DuplicateSubmitAspect extends BaseObject{
    public static final String DUPLICATE_TOKEN_KEY = "duplicate_token_key";

    @Pointcut("execution(public * com.spro.controller..*(..))")

    public void webLog() {
    }

    @Before("webLog() && @annotation(token)")
    public void before(final JoinPoint joinPoint, DuplicateSubmitToken token) {
        if (token != null) {
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            //HttpServletResponse response = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    request = (HttpServletRequest) args[i];
                }
               /* if (args[i] instanceof HttpServletResponse) {
                    response = (HttpServletResponse) args[i];
                }*/
            }

            boolean isSaveSession = token.save();
            if (isSaveSession) {
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                //Object t = getStringObj().get(key);
                if (null == t) {
                    String uuid = UUID.randomUUID().toString();
                    request.getSession().setAttribute(key.toString(), uuid);
                    try {
                       // getStringObj().set(key.toString(),uuid);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                } else {
                    throw new DuplicateSubmitException("重复请求！");
                }
            }

        }
    }

    /**
     * 获取重复提交key-->duplicate_token_key+','+请求方法名
     *
     * @param joinPoint
     * @return
     */
    public String getDuplicateTokenKey(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder key = new StringBuilder(DUPLICATE_TOKEN_KEY);
        key.append(",").append(methodName);
        return key.toString();
    }

    @AfterReturning("webLog() && @annotation(token)")
    public void doAfterReturning(JoinPoint joinPoint, DuplicateSubmitToken token) {
        // 处理完请求，返回内容
        if (token != null) {
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    request = (HttpServletRequest) args[i];
                }
            }
            boolean isSaveSession = token.save();
            if (isSaveSession) {
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                //Object t = getStringObj().get(key);
                if (null != t) {
                    //方法执行完毕移除请求重复标记
                    //getKeyObj().del(key);
                    request.getSession(false).removeAttribute(key);
                }
            }
        }
    }

    /**
     * 异常
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "webLog()&& @annotation(token)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e, DuplicateSubmitToken token) {
        if (null != token
                && e instanceof DuplicateSubmitException == false) {
            //处理处理重复提交本身之外的异常
            Object[] args = joinPoint.getArgs();
            HttpServletRequest request = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    request = (HttpServletRequest) args[i];
                }
            }
            boolean isSaveSession = token.save();
            //获得方法名称
            if (isSaveSession) {
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                //Object t = getStringObj().get(key);
                if (null != t) {
                    //方法执行完毕移除请求重复标记
                    request.getSession(false).removeAttribute(key);
                    //getKeyObj().del(key);
                }
            }
        }
    }
}
