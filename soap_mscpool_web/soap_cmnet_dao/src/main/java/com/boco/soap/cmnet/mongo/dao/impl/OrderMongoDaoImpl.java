package com.boco.soap.cmnet.mongo.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;
import com.boco.soap.cmnet.mongo.dao.IOrderMongoDao;
import org.springframework.stereotype.Repository;

@Repository
public class OrderMongoDaoImpl extends BaseMongoDAOImpl<OrderMongo> implements IOrderMongoDao {

    @Override
    public OrderMongo findById(String orderId) {
        return super.findById(orderId,OrderMongo.getCollectionName(orderId));
    }

    @Override
    public OrderMongo save(OrderMongo entity) {
        return super.save(entity,entity.getCollectionName());
    }

}

