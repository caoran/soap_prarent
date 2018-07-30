package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.beans.entity.BusiDictItem;

import java.util.List;

public interface BusiDictMapper {
    int deleteById(String id);

    int save(BusiDict record);

    BusiDict getById(String id);

    int update(BusiDict record);

    List<BusiDict> getList(BusiDict busiDict);
}