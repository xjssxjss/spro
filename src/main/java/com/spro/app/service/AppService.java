package com.spro.app.service;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.spro.entity.MobilePush;
import com.spro.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppService extends BaseService<MobilePush> {
    private Logger logger = LoggerFactory.getLogger(AppService.class);
    
    //定义常量, appId、appKey、masterSecret 采用本文档 "第二步 获取访问凭证 "中获得的应用配置
    private static String url = "http://sdk.open.api.igexin.com/apiex.htm";

    /**
     * 手机端消息推送
     */
    public Map<String,Object> appPush(String title, String content){
        IGtPush push = new IGtPush(url, resourceMap.get("appKey"), resourceMap.get("masterSecret"));

        // 定义"点击链接打开通知模板"，并设置标题、内容、链接
        LinkTemplate template = new LinkTemplate();
        template.setAppId(resourceMap.get("appId"));
        template.setAppkey(resourceMap.get("appKey"));
        template.setTitle(title);
        template.setText(content);
        template.setUrl("http://getui.com");

        List<String> appIds = new ArrayList<String>();
        appIds.add(resourceMap.get("appId"));

        // 定义"AppMessage"类型消息对象，设置消息内容模板、发送的目标App列表、是否支持离线发送、以及离线消息有效期(单位毫秒)
        AppMessage message = new AppMessage();
        message.setData(template);
        message.setAppIdList(appIds);
        message.setOffline(true);
        message.setOfflineExpireTime(1000 * 600);

        IPushResult ret = push.pushMessageToApp(message);

        return ret.getResponse();
    }
}
