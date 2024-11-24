package com.atguigu.cloud.apis;


import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.R;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.List;

//@FeignClient(value = "cloud-payment-service")
@FeignClient(value = "cloud-gateway")
public interface PayFeignApi {

    /**
     * 新增一个
     */
    @PostMapping("/pay/add")
     R<String> addPay(@RequestBody PayDTO payDTO);

    /**
     * 查询一个
     */
    @GetMapping("/pay/{id}")
     R<PayDTO>  getPayById(@PathVariable("id") Integer id);

    /**
     * 查询所有
     */
    @GetMapping("/pay/all")
     R<List<PayDTO>> getAllPay();

    /**
     * 删除一个
     */
    @DeleteMapping("/pay/del/{id}")
     R<Integer> deletePayById(@PathVariable("id") Integer id);

    /**
     * 更新一个
     */
    @PutMapping("/pay/update")
     R<String> updatePay(@RequestBody PayDTO payDTO);

    /**
     * feign 自带负载均衡
     */
    @GetMapping("/pay/get/info")
     String getInfo();

    @GetMapping(value = "/pay/circuit/{id}")
     String myCircuit(@PathVariable("id") Integer id);

    /**
     * Resilience4j Bulkhead 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/bulkhead/{id}")
     String myBulkhead(@PathVariable("id") Integer id);

    /**
     * Resilience4j Ratelimit 的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/ratelimit/{id}")
    public String myRatelimit(@PathVariable("id") Integer id);

    /**
     * Micrometer(Sleuth)进行链路监控的例子
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/micrometer/{id}")
    public String myMicrometer(@PathVariable("id") Integer id);


    /**
     * GateWay进行网关测试案例01
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/gateway/get/{id}")
     R getById(@PathVariable("id") Integer id);

    /**
     * GateWay进行网关测试案例02
     * @return
     */
    @GetMapping(value = "/pay/gateway/info")
     R<String> getGatewayInfo();


    /**
     * GateWay进行过滤器测试
     * @param request
     * @return
     */
    @GetMapping(value = "/pay/gateway/filter")
     R<String> getGatewayFilter(HttpServletRequest request);
}
