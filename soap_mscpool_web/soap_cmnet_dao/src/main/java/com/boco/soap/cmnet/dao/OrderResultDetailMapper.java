package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.OrderResultDetail;

public interface OrderResultDetailMapper {
    int deleteById(Short id);

    int save(OrderResultDetail record);

    OrderResultDetail getById(Short id);

    int update(OrderResultDetail record);
}