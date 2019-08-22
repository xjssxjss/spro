package com.spro.controller.au;

import com.spro.common.BaseController;
import com.spro.service.au.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/userController")
@RestController
public class UserController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/queryUserListByParams",method = RequestMethod.POST)
    public Map<String,Object> queryUserListByParams(String conditions){
        return userService.queryUserListByParams(getSearchMap(conditions));
    }

    /**
     * 新增或更新用户信息
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateUser",method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdateUser(String userForm){
        logger.info("userForm>>>>>>>>>" + userForm);
        return userService.saveOrUpdateUser(userForm);
    }
}
