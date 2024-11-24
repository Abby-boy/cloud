package com.atguigu.cloud.controller;


import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.R;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@Tag(name="支付模块",description="支付相关的API接口")
public class PayController {

    @Resource
    private PayService payService;

    /**
     * 新增一个
     */
    @PostMapping("/pay/add")
    @Operation(summary = "新增一个支付")
    public R<String> addPay(@RequestBody Pay pay){
        System.out.println(pay.toString());
        int i = payService.add(pay);
        if (i < 0) throw new RuntimeException("添加失败");
        //return i > 0 ? "新增成功" : "添加失败";
        return  R.success("新增成功");
    }

    /**
     * 查询一个
     */
    @GetMapping("/pay/{id}")
    @Operation(summary = "根据id查询支付")
    public R<Pay>  getPayById(@PathVariable("id") Integer id) {
        if (id < 0) throw new RuntimeException("id不能小于0");
        // 暂停几秒，模拟网络延迟
        try {
            TimeUnit.SECONDS.sleep(62);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        Pay pay = payService.getById(id);
        return R.success(pay);
    }

    /**
     * 查询所有
     */
    @GetMapping("/pay/all")
    @Operation(summary = "查询所有支付")
    public R<List<Pay>> getAllPay(){
        return R.success(payService.getAll());
    }

    /**
     * 删除一个
     */
    @DeleteMapping("/pay/del/{id}")
    @Operation(summary = "根据id删除支付")
    public R<Integer> deletePayById(@PathVariable("id") Integer id) {
        int i = payService.del(id);
        if (i < 0) throw new RuntimeException("删除失败");
        return  R.success(i);
    }

    /**
     * 更新一个
     */
    @PutMapping("/pay/update")
    @Operation(summary = "更新一个支付")
    public R<String> updatePay(@RequestBody PayDTO payDTO) {
        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO, pay);
        int i = payService.update(pay);
        if (i < 0) throw new RuntimeException("修改失败");
        return  R.success("修改成功");
    }


    @Value("${server.port}")
    private String port;

    @GetMapping("/pay/get/info")
    public String getInfo(@Value("${atguigu.info}") String atguiguInfo) {
        return "当前端口号：" + port + ",配置信息为：" + atguiguInfo;
    }
}
