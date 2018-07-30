package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.OrderResult;

public interface OrderResultMapper {
    int deleteById(Short id);

    int save(OrderResult record);

    OrderResult getById(Short id);

    int update(OrderResult record);
}