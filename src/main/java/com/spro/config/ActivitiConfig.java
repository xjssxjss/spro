package com.spro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration//声名为配置类，继承Activiti抽象配置类 extends AbstractProcessEngineAutoConfiguration
public class ActivitiConfig{

//    @Bean
//    @Primary
//    //@ConfigurationProperties(prefix = "spring.datasource")
//    public DataSource activitiDataSource() {
//        return DataSourceBuilder.create().build();
//    }

}