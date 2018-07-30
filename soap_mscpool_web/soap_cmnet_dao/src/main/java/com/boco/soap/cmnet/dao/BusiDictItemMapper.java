package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.BusiDictItem;

import java.util.List;

public interface BusiDictItemMapper {
    int deleteById(Short id);

    int save(BusiDictItem record);

    BusiDictItem getById(Short id);

    int update(BusiDictItem record);

    List getByDictId(String s);
}