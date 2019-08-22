package com.spro.service.au;

import com.spro.common.GlobalConstant;
import com.spro.entity.au.Role;
import com.spro.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoleService extends BaseService<Role>{
    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    /**
     * 通过参数获取角色列表
     * @param paramMap
     * @return
     */
    public Map<String,Object> queryRoleListByParams(Map<String,Object> paramMap){
        try{
            Map map = new HashMap();
            map.put("dataList",queryByParams(paramMap));
            map.put("totalCount",10);
            data = map;
            success = true;
            message = GlobalConstant.SUCCESS_MESSAGE;
        }catch (Exception e){
            success = false;
            message = e.getMessage();
        }
        return result();
    }
}
