package com.spro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Created by Sean on 2019/7/22.
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket prodApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("spro")
                .genericModelSubstitutes(DeferredResult.class)
                //.genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(false)
                .pathMapping("/")//api测试请求地址
                .select()
                .paths(PathSelectors.regex("/*.*"))//过滤的接口
                .build()
                .apiInfo(prodApiInfo());
    }

    /**
     * swagger-ui配置
     * @return
     */
    private ApiInfo prodApiInfo() {
        Contact contact = new Contact("SPRO", "http://xsxt.free.idcfengye.com/spro", "yahui.xie@163.com");
        ApiInfo apiInfo = new ApiInfo("Spring Boot SPRO项目 API接口管理",//大标题
                "Spro项目RestApi",//小标题
                "1.0",//版本
                "http://www.baidu.com",//服务网址条款
                contact,//作者
                "去百度",//链接显示文字
                "http://www.baidu.com"//网站链接
        );
        return apiInfo;
    }
}
