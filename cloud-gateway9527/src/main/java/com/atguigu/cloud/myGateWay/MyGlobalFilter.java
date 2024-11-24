package com.atguigu.cloud.myGateWay;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    public static final String START_TIME = "start_time";



    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.记录开始时间
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        //2.执行过滤器链,返回统一的结果给后台
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime != null) {

                log.info("访问接口主机:" + exchange.getRequest().getURI().getHost());
                log.info("访问接口端口:" + exchange.getRequest().getURI().getPort());
                log.info("访问接口URL:" + exchange.getRequest().getURI().getPath());
                log.info("访问接口URL后面参数:" + exchange.getRequest().getURI().getRawQuery());
                log.info("MyGlobalFilter 耗时: " + (System.currentTimeMillis() - startTime));
                log.info("=======================" );
            }
        }));
    }

    /**
     *  数字越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
