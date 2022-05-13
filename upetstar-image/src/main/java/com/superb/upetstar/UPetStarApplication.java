package com.superb.upetstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author hym
 * @description
 */
@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class UPetStarApplication {
    public static void main(String[] args) {
        SpringApplication.run(UPetStarApplication.class, args);
    }
}
