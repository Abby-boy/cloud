package com.atguigu.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeginConfig {

    @Bean
    public Retryer myRetryer() {

        return Retryer.NEVER_RETRY;    // Feign 默认配置是不走重试策略的

        // 最大请求次数为3，初始间隔为100ms，每次重试间隔为1ms
        //return new Retryer.Default(50, 1, 3);
    }


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
