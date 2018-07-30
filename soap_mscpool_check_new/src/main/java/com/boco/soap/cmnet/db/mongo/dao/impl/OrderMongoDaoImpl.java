package com.boco.soap.cmnet.db.mongo.dao.impl;

import com.boco.soap.cmnet.db.mongo.OrderMongo;
import com.boco.soap.cmnet.db.mongo.dao.IOrderMongoDao;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    @Override
    public void update(Query query, Update update, String collectionName) {
        super.update(query, update, collectionName);
    }

    @Override
    public List<OrderMongo> find(Query query) {
        return super.find(query);
    }

    @Override
    public List<OrderMongo> find(Query query, String collectionName) {
        return super.find(query, collectionName);
    }
}
