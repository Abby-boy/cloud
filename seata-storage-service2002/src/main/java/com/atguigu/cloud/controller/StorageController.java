package com.atguigu.cloud.controller;


import com.atguigu.cloud.resp.R;
import com.atguigu.cloud.service.StorageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StorageController {


    @Resource
    private StorageService storageService;

    /**
     * 扣减库存
     */
    @RequestMapping("/storage/decrease")
    public R decrease(Long productId, Integer count) {

        storageService.decrease(productId, count);
        return R.success("扣减库存成功!");
    }
}
