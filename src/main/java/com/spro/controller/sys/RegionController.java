package com.spro.controller.sys;

import com.spro.common.BaseController;
import com.spro.service.sys.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/regionController")
public class RegionController extends BaseController{

    @Autowired
    private RegionService regionService;

    @RequestMapping(value = "/queryAllRegions",method = RequestMethod.GET)
    public Map<String,Object> queryAllRegions() {
        return regionService.queryAllRegions();
    }
}
