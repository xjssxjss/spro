package com.spro;

import com.spro.interceptor.SproInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@EnableScheduling
@SpringBootApplication( exclude = SecurityAutoConfiguration.class)	//屏蔽冲突的包
//@ComponentScan(basePackages = {"com.spro.controller","com.spro.service"})
@MapperScan(basePackages = {"com.spro.dao"})
@EnableTransactionManagement
//@Configuration
//@ComponentScan(useDefaultFilters = true)
public class SproApplication extends SpringBootServletInitializer implements WebMvcConfigurer {

	private static Logger logger = LoggerFactory.getLogger(SproApplication.class);
	public static void main(String[] args) {
		logger.info("spro project begin>>>>>>>>>>>>>>");
		SpringApplication.run(SproApplication.class, args);
		logger.info("spro project end>>>>>>>>>>>>>>");
	}

	@Override
	protected final SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(SproApplication.class);
	}

	/**
	 * 全局拦截器
	 * @param registry
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		//registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
        /*registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/xxx1/**");
        registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/xxx2/**");*/

        registry.addInterceptor(new SproInterceptor()).addPathPatterns("/**");
		//registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
	}

	/*@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
				.maxAge(3600)
				.allowCredentials(true);
	}*/
	/**
	 * springboot 解决跨域访问问题
	 * @return
	 */
	@Bean
	public FilterRegistrationBean corsFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		ArrayList<String> objects = new ArrayList<>();
		objects.add("*");
		config.setAllowedOrigins(objects);
		config.setAllowedHeaders(objects);
		config.setAllowedMethods(objects);
//        source.registerCorsConfiguration("/**", config);
		Map<String, CorsConfiguration> corsConfigurations = new HashMap<>();
		corsConfigurations.put("/**",config);
		source.setCorsConfigurations(corsConfigurations);
		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}
