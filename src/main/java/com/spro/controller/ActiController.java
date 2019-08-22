package com.spro.controller;

import com.spro.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ac")
@RestController
public class ActiController extends BaseController{

    /*@Autowired
    private ActivitiService activitiService;
    @RequestMapping(value = "/startTask")
    public void startTask(){
        activitiService.statrtTask();
    }*/
}
