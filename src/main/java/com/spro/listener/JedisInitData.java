package com.spro.listener;

import com.spro.service.DictionaryEntriesService;
import com.spro.util.JedisKeyBuilderUtil;
import com.spro.util.JedisUtil;
import com.spro.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class JedisInitData {
    private Logger logger = LoggerFactory.getLogger(JedisInitData.class);

    static JedisUtil jedisUtil = null;
    static {
        jedisUtil = JedisUtil.getInstance();

        //清空存在的所有key
        JedisUtil.Keys keys = jedisUtil.new Keys();
        keys.flushAll();
    }

    public static void initJedisData(DictionaryEntriesService dictionaryEntriesService){
        //序列化类
        JedisUtil.Strings strings = jedisUtil.new Strings();

        //放入序列化对象
        Map map = dictionaryEntriesService.getDictionaryEntries();

        strings.set(JedisKeyBuilderUtil.keyBuilder("com.spro","entiry","DictionaryEntries"), SerializeUtil.serialize(map));
    }
}
