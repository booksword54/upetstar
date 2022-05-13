package com.superb.upetstar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author hym
 * @date 2022/3/11
 * @description Swagger2配置文件
 */
@Configuration
@EnableSwagger2 // 开启Swagger2功能
public class Swagger2Config {

    /**
     * 文档构建器，配置api文档
     */
    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2) // 构建Swagger2文档
                .groupName("adminApi") // 组名
                .apiInfo(adminApiInfo()) // api文档信息
                .select() // 选择指定接口生成文档
                // 路径正则匹配
                .apis(RequestHandlerSelectors.basePackage("com.superb.upetstar.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 生成api文档信息
     */
    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("U宠星后台管理系统-API文档") // 文档标题
                .description("本文档描述了U宠星后台管理系统接口") // 描述
                .version("1.0") // 版本
                .contact(new Contact("hym", "http://superb.com", "ymworkmail2021@163.com")) // 联系方式
                .build();
    }
}