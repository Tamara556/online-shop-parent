package com.online.store.onlineshoprest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan(basePackages = {"com.online.store.onlineshoprest", "com.online.store.onlineshopcommon"})
@EntityScan( basePackages = "com.online.store.onlineshopcommon.entity")
@EnableJpaRepositories(basePackages = "com.online.store.onlineshopcommon.repository")
@EnableAsync
@SpringBootApplication
public class OnlineShopRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopRestApplication.class, args);
    }

}
