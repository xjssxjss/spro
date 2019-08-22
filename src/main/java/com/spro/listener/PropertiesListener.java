package com.spro.listener;

import com.spro.common.GlobalConstant;
import com.spro.service.DictionaryEntriesService;
import com.spro.util.PropertiesListenerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PropertiesListener implements ApplicationListener<ApplicationStartedEvent>{

    private Logger logger = LoggerFactory.getLogger(PropertiesListener.class);
    private String propertyFileName;

    @Autowired
    private DictionaryEntriesService dictionaryEntriesService;

    public PropertiesListener() {
        this.propertyFileName = GlobalConstant.BASE_FILE_NAMES;
    }

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        //把所有指定文件中的key，value放入缓存map中
        PropertiesListenerConfig.loadAllProperties(propertyFileName);

        //初始化redis库中的数据
        //JedisInitData.initJedisData(dictionaryEntriesService);
    }
}
