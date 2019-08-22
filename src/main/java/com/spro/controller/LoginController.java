package com.spro.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by yahui.xie 20190319
 */
@EnableSwagger2
@RequestMapping("/loginController")
@Controller
public class LoginController{

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    @ApiOperation(value = "登录" , notes = "用户登录方法")
    public String login(@RequestParam(value = "username",required = true) String username,
                          @RequestParam(value = "password",required = true) String password,
                          HttpServletRequest req,
                          HttpServletResponse resp) throws Exception{
        req.setAttribute("user",username);
        return "../index.html";
    }

    @RequestMapping(value = "/free")
    public String free(ModelAndView mv) {
        logger.info("====>>跳转freemarker页面");
        mv.addObject("name", "jack");
        mv.setViewName("index");
        return "freemarker/index";
    }


}
