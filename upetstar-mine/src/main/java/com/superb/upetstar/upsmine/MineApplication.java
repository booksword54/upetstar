package com.superb.upetstar.upsmine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author hym
 * @description
 */
@RefreshScope
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication
public class MineApplication {
    public static void main(String[] args) {
        SpringApplication.run(MineApplication.class, args);
    }
}
