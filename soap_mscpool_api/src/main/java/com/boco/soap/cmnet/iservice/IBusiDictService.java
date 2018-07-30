package com.boco.soap.cmnet.iservice;

import com.boco.soap.cmnet.beans.entity.BusiDict;

import java.util.List;

public interface IBusiDictService {
    int deleteById(String id);

    int save(BusiDict record);

    BusiDict getById(String id);

    List<BusiDict> getListByBusiId(String id);

    List<BusiDict> getListAndCheckStatue(String orderId);

    int update(BusiDict record);
}
