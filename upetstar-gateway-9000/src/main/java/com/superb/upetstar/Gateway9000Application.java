package com.superb.upetstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hym
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Gateway9000Application {
    public static void main(String[] args) {
        SpringApplication.run(Gateway9000Application.class, args);
    }
}
