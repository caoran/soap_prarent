package com.boco.soap.cmnet.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommonMapper {
    List getListBySql(String sql);

    List getListByTableName(@Param("tableName") String tableName);
}
