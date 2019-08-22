package com.spro.controller.job;

import com.spro.common.BaseController;
import com.spro.service.job.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "/jobController")
@RestController
public class JobController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/initJobInfo",method = RequestMethod.GET)
    public Map<String,Object> initJobInfo(){
        return jobService.initJobInfo();
    }

    @RequestMapping(value = "/saveOrUpdateJobInfo",method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdateJobInfo(@RequestParam(value = "form") String form){

        logger.info("form>>>>>>>" + form);
        return null;
    }
}
