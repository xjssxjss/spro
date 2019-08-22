package com.spro.common;

import com.alibaba.fastjson.JSONObject;
import com.spro.util.JedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Map;

/**
 * Created by yahui.xie on 2019/09/09
 */
@RestController
@RequestMapping("/baseController")
@EnableSwagger2
public class BaseController extends BaseObject{


    public JedisUtil.Strings jedisStr = jedisUtil.new Strings();

    public Map<String,Object> getResultMap(Map<String,Object> resultMap){
        resultMap.put("success",resultMap.get("success"));
        resultMap.put("data",resultMap.get("data"));
        resultMap.put("message",resultMap.get("message") == null || Boolean.parseBoolean(resultMap.get("success")==null ? "" : resultMap.get("success").toString()) ? "数据访问成功" : "数据访问失败!");
        return resultMap;
    }

    /**
     * 封装查询条件
     * @param conditions
     * @return
     */
    public Map<String,Object> getSearchMap(String conditions){
        Map<String,Object> object = null;
        try{
            object = (Map<String,Object>) JSONObject.parse(conditions);

            if(null != object){
                if(null == object.get("currentPage")){
                    object.put("currentPage",1);
                }
                if(null == object.get("pageSize")){
                    object.put("pageSize",99999999);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return object;
    }

}
