package com.scrum.training.configure;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * MultipartFileに関する設定
 */
@Configuration
public class MultiPartConfigure {

    /**
     * この設定はあくまでSpringにおけるデフォルトの上限を制御するもの。
     * 普通に上限超えたら例外を吐くのでバリデーションなどは別途実装する必要あり。
     * {@link com.scrum.training.validation.FileRequired}
     * {@link com.scrum.training.validation.FileRequiredValidator}
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(2L));
        factory.setMaxRequestSize(DataSize.ofMegabytes(100L));
        return factory.createMultipartConfig();
    }
}
