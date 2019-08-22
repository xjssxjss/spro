package com.spro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(value = "/freemarkerController")
@Controller
public class FreemarkerController {

    @RequestMapping(value = "/freemarker",method = RequestMethod.GET)
    public ModelAndView freemarker(){
        ModelAndView mv = new ModelAndView("index");
        List list = new ArrayList();
        list.add("java");
        list.add("pathon");
        list.add("php");
        mv.addObject("name","freemarker");
        mv.addObject("lanList",list);
        return mv;
}
}
