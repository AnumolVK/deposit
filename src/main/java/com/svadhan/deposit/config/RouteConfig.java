package com.svadhan.deposit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class RouteConfig {
    @Bean
    public Supplier<String> ping() {
        return () -> "Pong @ ".concat(new Date().toString());
    }
    @Bean
    public Function<String, String> test() {
        return "Hi "::concat;
    }
}
