package com.teachingplan.console.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by v_yanfzhang on 2018/2/2.
 */
@Configuration
public class CommonConfig {

    /**
     * 限制最大上传10M文件
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(10 * 1024L * 1024L);
        return factory.createMultipartConfig();
    }
}
