package com.atguigu.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.atguigu.cloud.mapper")//import tk.mybatis.spring.annotation.MapperScan;
@EnableDiscoveryClient
@RefreshScope    // 动态刷新配置 当 consul 中 data 数据发生变化时，会自动刷新    实际生产环境不需要， consul 会自动通知
public class Main8002 {
    public static void main(String[] args) {
        SpringApplication.run(Main8002.class, args);
    }
}