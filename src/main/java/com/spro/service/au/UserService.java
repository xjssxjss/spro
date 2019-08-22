package com.spro.service.au;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.spro.common.GlobalConstant;
import com.spro.dao.UserMapper;
import com.spro.entity.au.User;
import com.spro.service.BaseService;
import com.spro.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User>{
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;
    /**
     * 根据查询条件查询用户信息
     * @return
     */
    public Map<String,Object> queryUserListByParams(Map<String,Object> paramMap){
        if(null != paramMap){
            String phone = (String)paramMap.get("phone");
            paramMap.put("phone",StringUtil.likeParam(phone));

            String email = (String)paramMap.get("email");
            paramMap.put("email",StringUtil.likeParam(email));

            System.out.println(paramMap.get("proxyLevelId"));

            Integer proxyLevelId = null;
            if(!StringUtil.isEmpty(paramMap.get("proxyLevelId")==null?"":paramMap.get("proxyLevelId").toString())){
                proxyLevelId = (Integer) paramMap.get("proxyLevelId");
                paramMap.put("proxyLevelId",proxyLevelId);
            } else {
                paramMap.put("proxyLevelId",proxyLevelId);
            }
            //paramMap.put("isValid",StringUtil.booleanParam(paramMap.get("isValid")));
            //开启分页
            startPage(paramMap);
        }
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("dataList",queryByParams(paramMap));
            map.put("totalCount",userMapper.queryUsersCountByParams(paramMap));
            message = GlobalConstant.SUCCESS_MESSAGE;
            success = true;
            data = map;
        } catch (Exception e){
            message = e.getMessage();
            success = false;
        }
        return result();
    }

    /**
     *
     * 新增或更新用户信息
     * @return
     */
    @Transactional
    public Map<String,Object> saveOrUpdateUser(String userForm){
        //获取对象参数
        try {
            User user = JSON.parseObject(userForm, new TypeReference<User>() {});
            if(!StringUtil.isEmpty(user.getLoginName())){
                if(null == user.getId()){
                    //从用户表中查看用户是否存在
                    if(!checkUserIsRepeat(user.getLoginName())){
                        //持久化用户数据
                        //insert(user);
                        //需要配置相关权限

                        success = true;
                        message = GlobalConstant.SUCCESS_INSERT_MESSAGE;
                    } else {
                        success = false;
                        message = GlobalConstant.FAIL_USER_REPEAR;
                    }
                } else {
                    update(user);
                    success = true;
                    message = GlobalConstant.SUCCESS_UPDATE_MESSAGE;
                }

            }
        } catch (Exception e){
            success = false;
            message = e.getMessage();
        }

        return result();
    }

    /**
     * 校验用户是否已存在
     * @return
     */
    public boolean checkUserIsRepeat(String loginName){
        paramMap.put("loginName",loginName);
        try {
            List listUser = queryByParams(paramMap);
            if(null != listUser && listUser.size()>0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
