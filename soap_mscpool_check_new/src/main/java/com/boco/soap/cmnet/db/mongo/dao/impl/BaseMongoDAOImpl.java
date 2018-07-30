package com.boco.soap.cmnet.db.mongo.dao.impl;



import com.boco.soap.cmnet.db.mongo.OrderMongo;
import com.boco.soap.cmnet.db.mongo.dao.IBaseMongoDAO;
import com.boco.soap.cmnet.util.ReflectionUtils;
import com.boco.soap.cmnet.util.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public  class BaseMongoDAOImpl<T> implements IBaseMongoDAO<T> {

    private static final Logger LOGGER= LoggerFactory.getLogger(BaseMongoDAOImpl.class);

    private static final int DEFAULT_SKIP = 0;
    private static final int DEFAULT_LIMIT = 200;

    /**
     * spring mongodb　集成操作类　
     */
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    @Override
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    @Override
    public void update(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    @Override
    public void update(Query query, Update update, String collectionName) {
        mongoTemplate.updateFirst(query, update, collectionName);
    }

    @Override
    public T save(T entity) {
        mongoTemplate.insert(entity);
        return entity;
    }

    @Override
    public T save(T entity, String collectionName) {
      mongoTemplate.insert(entity,collectionName);
      return entity;
    }

    @Override
    public T findById(String id) {
        LOGGER.info("从mongo查询id为：{}",id);
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    @Override
    public T findById(String id, String collectionName) {
        LOGGER.info("从mongo查询id为：{}",id);
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    @Override
    public Page<T> findPage(Page<T> page, Query query){
        long count = this.count(query);
        page.setTotalCount((int)count);
        int pageNumber = page.getCurrentPage();
        int pageSize = page.getPageSize();
        query.skip((pageNumber - 1) * pageSize).limit(pageSize);
        List<T> rows = this.find(query);
        page.setRows(rows);
        return page;
    }

    @Override
    public long count(Query query){
        return mongoTemplate.count(query, this.getEntityClass());
    }

    @Override
    public void dropCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
    }


    /**
     * 获取需要操作的实体类class
     *
     * @return
     */
    private Class<T> getEntityClass(){
        return ReflectionUtils.getSuperClassGenricType(getClass());
    }


    @Override
    public void remove(String id) {
        OrderMongo orderMongo=new OrderMongo();
        orderMongo.setId(id);
        mongoTemplate.remove(orderMongo,orderMongo.getCollectionName());
    }


    @Override
    public void remove(Query query,String collectionName) {
        mongoTemplate.remove(query,this.getEntityClass(),collectionName);
    }


    @Override
    public List<T> find(Query query, String collectionName) {
        return mongoTemplate.find(query,this.getEntityClass(),collectionName);
    }
}
