package com.online.store.onlineshopweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.online.store.onlineshopweb", "com.online.store.onlineshopcommon"})
@EntityScan( basePackages = "com.online.store.onlineshopcommon.entity")
@EnableJpaRepositories(basePackages = "com.online.store.onlineshopcommon.repository")
public class OnlineShopWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShopWebApplication.class, args);
    }

}
