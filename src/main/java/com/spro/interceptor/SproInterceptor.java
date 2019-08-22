package com.spro.interceptor;

import com.spro.entity.au.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class SproInterceptor  extends HandlerInterceptorAdapter  {
    private Logger logger = LoggerFactory.getLogger(SproInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("执行业务之前调用>>>>>>>>>>>>>>>>>>>>>>>>>>");

//        if(handler instanceof HandlerMethod){
//            HandlerMethod hm = (HandlerMethod) handler;
//            System.out.println("当前执行的对象是"+hm.getMethod());
//        }
//
//        HttpSession session = request.getSession();
//        //判断用户是否登录
//        //User user = (User)session.getAttribute("user");
//
//        String username = (String)session.getAttribute("user");
//        //说明用户还没有登录
//        if(null == username){
//            //跳转到登录界面
//            response.sendRedirect(request.getContextPath() + "/login.html");
//            return false;
//        }

        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        //System.out.println(">>>MyInterceptor1>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");

        //super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
           //System.out.println(">>>MyInterceptor1>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
        //super.afterCompletion(request, response, handler, ex);
    }
}
