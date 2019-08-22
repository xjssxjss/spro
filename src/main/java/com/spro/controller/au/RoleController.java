package com.spro.controller.au;

import com.spro.common.BaseController;
import com.spro.service.au.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/roleController")
@RestController
public class RoleController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/queryRoleListByParams",method = RequestMethod.GET)
    public Map<String,Object> queryRoleListByParams(String conditions){
        return roleService.queryRoleListByParams(null);
    }
}
