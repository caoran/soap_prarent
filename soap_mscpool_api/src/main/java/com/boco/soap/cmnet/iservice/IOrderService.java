package com.boco.soap.cmnet.iservice;


import com.boco.soap.cmnet.beans.entity.Busi;
import com.boco.soap.cmnet.beans.entity.Order;
import com.boco.soap.cmnet.beans.entity.OrderMongoVO;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;

import java.util.List;
import java.util.Map;

public interface IOrderService {

    int deleteById(String id);

    int save(Order record);

    Order getById(String id);

    int update(Order record);

    List<Order> getList(Order order);

    String getOrderName(String busiId);

    void saveOrderStd(Order order, String city, String[] busiDictList);

    OrderMongo getOrderCheckResult(String orderId);

    List getOrderDetail(String orderId,String busiDictName,String paramName,String paramValue);

}
