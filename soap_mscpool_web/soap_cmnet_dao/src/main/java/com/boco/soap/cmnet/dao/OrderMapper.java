package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface OrderMapper {

    int deleteById(String id);

    int save(Order record);

    Order getById(String id);

    int update(Order record);

    List<Order> getList(Order order);
}