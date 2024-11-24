package com.atguigu.cloud.controller;


import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Tag(name="fegin设计订单模块",description = "订单相关的操作")
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/fegin/pay/add")
    @Operation(summary = "新增订单")
    public R addOrder(@RequestBody PayDTO payDTO){
        R<String> addPay = payFeignApi.addPay(payDTO);
        return R.success(addPay.getData());
    }


    @GetMapping("/fegin/pay/get/{id}")
    @Operation(summary = "根据id查询订单")
    public R getOrderById(@PathVariable("id") Integer id){
        R<PayDTO> payDTO = payFeignApi.getPayById(id);
        return R.success(payDTO.getData());
    }

    @GetMapping("/fegin/pay/getAll")
    @Operation(summary = "查询所有订单")
    public R getAllOrder(){
        R<List<PayDTO>> allPay = payFeignApi.getAllPay();
        return R.success(allPay.getData());
    }

    @DeleteMapping("/fegin/pay/delete/{id}")
    @Operation(summary = "根据id删除订单")
    public R deleteOrderById(@PathVariable("id") Integer id){
        R<Integer> integerR = payFeignApi.deletePayById(id);
        return R.success(integerR.getData());
    }

    @PutMapping("/fegin/pay/update")
    @Operation(summary = "更新订单")
    public R updateOrder(@RequestBody PayDTO payDTO){
        R<String> stringR = payFeignApi.updatePay(payDTO);
        return R.success(stringR.getData());
    }

    @GetMapping("/fegin/pay/mylb")
    @Operation(summary = "openfeign负载均衡")
    public String mylb(){
        return payFeignApi.getInfo();
    }

}
