package com.boco.soap.cmnet.db.mybitas;

import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Map;

public interface IDBAccessManager {
    public abstract Map<String, SqlSessionFactory> getFactorys();

    public abstract void setFactorys(Map<String, SqlSessionFactory> paramMap);

    public abstract void addFactory(String paramString, SqlSessionFactory paramSqlSessionFactory);

    public abstract void removeFactory(String paramString);

    public abstract SqlSessionFactory getFactory(String paramString);

    public abstract int getFactorysSize();
}
