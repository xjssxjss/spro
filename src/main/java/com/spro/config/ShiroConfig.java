package com.spro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Sean on 2019/7/22.
 * shiro配置类
 */
@Configuration
public class ShiroConfig {
    /**
     * 开启MD5加密
     * @return
     */
    @Bean
    public HashedCredentialsMatcher getMatcher(){
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        return matcher;
    }
}
