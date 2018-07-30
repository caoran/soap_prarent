package com.boco.soap.cmnet;

import com.boco.soap.cmnet.check.excutor.ICheckDataAllMain;
import com.boco.soap.cmnet.db.mongo.OrderMongo;
import com.boco.soap.cmnet.db.mongo.dao.IOrderMongoDao;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.boco.soap")
public class AppTest
{
    @Autowired
    private IOrderMongoDao orderMongoDao;
    @Test
    public void testCheckDataAllMain() throws Throwable{
        ICheckDataAllMain checkDataAllMain= SpringContextHolder.getBean("checkDataAllMainImpl");
        checkDataAllMain.soapCheckDataInvokeResultByOrderId("364028b88164832c270164839d11c90004");
        Thread.sleep(100000000);

    }

    @Test
    public void testDelMongo() {
        String id="364028b881646dabb401646dabb41d0000";
        OrderMongo orderMongo=orderMongoDao.findById(id);
        orderMongoDao.remove(id);
    }
    @Test
    public void testUpdateMongo() {
        String id="364028b881646e56a801646e56a8960000";
        OrderMongo orderMongo=orderMongoDao.findById(id);
        Query query=new Query(where("_id").is(orderMongo.getId()));
        Update update=new Update().set("checkResult2","789652223");
        orderMongoDao.update(query,update,orderMongo.getCollectionName());
    }


    @Test
    public void testSearchMongo() {
        String orderId="364028b88164832c270164839d11c90004";
        Criteria criteria=new Criteria();
        criteria.andOperator(where("_id").is(orderId),where("checkResult.3G临区.resultList.3G服务区号").regex("46000B7D00429"));
        List<OrderMongo> list=orderMongoDao.find(query(criteria),OrderMongo.getCollectionName(orderId));
        System.out.printf("list:====="+ list.toString());
    }
}