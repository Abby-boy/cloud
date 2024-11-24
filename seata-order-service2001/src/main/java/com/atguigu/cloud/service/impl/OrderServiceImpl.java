package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;


@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource    // 订单微服务通过OpenFeign调用库存微服务的接口
    private StorageFeignApi storageFeignApi;

    @Resource
    private AccountFeignApi accountFeignApi;

    @Override
    @GlobalTransactional(name = "fsp-create-order",rollbackFor = Exception.class)
    public void create(Order order) {



        // xid 全局事务id的检查，xid 是全局唯一的，xid 是全局唯一的，xid 是全局唯一的
        String xid = RootContext.getXID();

        // 1.新建订单
        log.info("----->新建订单:"+"\t"+"xid:"+xid);
        order.setStatus(0);
        int result = orderMapper.insertSelective(order);
        // 插入订单成功后获取mysql的实体对象
        Order orderFromDB = null;
        if (result > 0){
            //  从mysql里面查出刚插入的对象
            orderFromDB = orderMapper.selectOne(order);
            log.info("----->订单创建成功,orderFromDB info:"+ orderFromDB);
            // 2.扣减库存
            log.info("-----> 订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            // 3.扣减账户余额
            log.info("-----> 订单微服务开始调用account余额，做扣减money");
            accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            // 4.更新订单状态为已完成
            log.info("-----> 修改订单状态start");
            orderFromDB.setStatus(1);
            //orderMapper.updateByPrimaryKeySelective(orderFromDB);
            Example whereCondition= new Example(Order.class);
            Example.Criteria criteria = whereCondition.createCriteria();
            criteria.andEqualTo("userId", orderFromDB.getId());
            criteria.andEqualTo("status", 0);
            int updateResult = orderMapper.updateByExampleSelective(orderFromDB, whereCondition);
            log.info("-----> 修改订单状态完成"+"\t"+updateResult);
            log.info("----->orderFromDB:"+orderFromDB);
        }
        System.out.println();
        log.info("----->订单创建完毕:"+"\t"+"xid:"+xid);


        //orderMapper.create(order);
    }
}
