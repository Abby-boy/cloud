package com.atguigu.cloud.myGateWay;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.support.ShortcutConfigurable;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;


/**
 * 需求说明，自定义配置会员等级userType,按照钻/金/银 和 yml配置的会员等级，以适配是否可以访问
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public static final String USER_TYPE = "userType";

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList(USER_TYPE);
    }


    // 这个Config 类就是我们的路由断言规则
    @Validated
    public static class Config{

        @Getter
        @Setter
        @NotEmpty
        private String userType;   // 会员等级

    }
    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                // 检查request 的参数里面，userType 是否等于配置的userType，符合配置就通过
                String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
                // 如果说参数存在，就和 config的数据进行比较
                if (userType != null && userType.equalsIgnoreCase(config.getUserType())){
                    return true;
                }
                return false;
            }
        };

    }
}
