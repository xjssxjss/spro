package com.spro.controller.au;

import com.alibaba.fastjson.JSON;
import com.spro.common.BaseController;
import com.spro.util.HttpClientUtil;
import com.spro.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sean on 2019/6/17.
 */
@RestController
@RequestMapping("/wxLoginController")
public class WxLoginController extends BaseController{

    Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    @PostMapping("/wxLogin")
    public Map<String,Object> wxLogin(@RequestParam(value = "code") String code){
        logger.info("小程序登录>>>>>>>>>>>>>>>>>>"+code);
        String url = resourceMap.get("wx_url");
        Map<String,String> param = new HashMap<String,String>();

        //给appid进行加密
        String wxSort = resourceMap.get("wx_sort");

        logger.info(MD5Util.crypt(resourceMap.get("appid")));
        param.put("appid", MD5Util.crypt(resourceMap.get("appid")));
        param.put("secret",resourceMap.get("secret"));
        param.put("js_code",code);
        param.put("grant_type",resourceMap.get("grant_type"));

        String result = HttpClientUtil.doGet(url,param);
        System.out.println(result);

        Map<String,Object> map = (Map<String,Object>)JSON.parse(result);
        return map;
    }

    public static void main(String[] args) {
        String appid = MD5Util.crypt("123456");
        System.out.println(appid);
    }
}
