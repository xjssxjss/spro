package com.spro.common;

import com.spro.util.JedisUtil;
import com.spro.util.PropertiesListenerConfig;

import java.util.Map;

public class BaseObject {
    //获取ResourceBox的实例对象
    public static Map<String,String> resourceMap = PropertiesListenerConfig.propertiesMap;

    public static JedisUtil jedisUtil = JedisUtil.getInstance();

    public JedisUtil.Keys getKeyObj(){
        JedisUtil.Keys keys = jedisUtil.new Keys();
        return keys;
    }

    public JedisUtil.Strings getStringObj(){
        JedisUtil.Strings strs = jedisUtil.new Strings();
        return strs;
    }
}
