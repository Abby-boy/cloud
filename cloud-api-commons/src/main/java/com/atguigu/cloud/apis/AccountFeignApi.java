package com.atguigu.cloud.apis;

import com.atguigu.cloud.resp.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "seata-account-service")
public interface AccountFeignApi {

    //扣减账户余额
    @PostMapping("/account/decrease")
    R decrease(@RequestParam("userId") Long userId, @RequestParam("money") Long money);
}
