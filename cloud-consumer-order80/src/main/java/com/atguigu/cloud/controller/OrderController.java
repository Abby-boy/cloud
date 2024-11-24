package com.atguigu.cloud.controller;


import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.R;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Tag(name="订单模块",description = "订单相关的操作")
public class OrderController {

    //public static final String PaymentSrv_URL = "http://localhost:8001";
    public static final String PaymentSrv_URL = "http://cloud-payment-service";  // 注册中心获取服务地址

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/pay/add")
    @Schema(description = "新增订单")
    public R<String> addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/add",payDTO,R.class);
    }

    @GetMapping("/consumer/pay/{id}")
    @Schema(description = "查询订单")
    public R getOrder(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/"+id,R.class,id);
    }

    @GetMapping("/consumer/pay/update")
    @Schema(description = "修改订单")
    public R<String> updateOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL+"/pay/update",payDTO,R.class);
    }

    @GetMapping("/consumer/pay/del/{id}")
    @Schema(description = "删除订单")
    public R<String> deleteOrder(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/del"+id,R.class,id);
    }

    @GetMapping("/consumer/pay/all")
    @Schema(description = "查询所有订单")
    public R<String> listOrder(){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/all",R.class);
    }


    @GetMapping("/consumer/pay/getInfo")
    private String getInfo(){
        return restTemplate.getForObject(PaymentSrv_URL+"/pay/get/info",String.class);
    }

    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();
    }
}
