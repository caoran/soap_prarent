
package com.boco.soap.cmnet.db.mybitas.impl;

import com.boco.soap.cmnet.db.mybitas.IDBAccessManager;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("dBAccessManagerImpl")
public class DBAccessManagerImpl implements IDBAccessManager {

    private static final Logger logger = LoggerFactory.getLogger(DBAccessManagerImpl.class);
    private Map<String, SqlSessionFactory> factorys = new HashMap();

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public Map<String, SqlSessionFactory> getFactorys()
    {
        return this.factorys;
    }

    public void setFactorys(Map<String, SqlSessionFactory> factorys)
    {
        this.factorys = factorys;
    }

    public void addFactory(String name, SqlSessionFactory factory)
    {
        this.factorys.put(name, factory);
    }

    public void removeFactory(String name)
    {
        this.factorys.remove(name);
    }

    public SqlSessionFactory getFactory(String name) {
        if (null == name) {
            if (logger.isErrorEnabled()) {
                logger.error("从DBAccessManager中获取SqlSessionFactory失败，SqlSessionFactory名称为：" + name);
            }
            return null;
        }/*if (!this.factorys.containsKey(name))
        {
            if (logger.isErrorEnabled()) {
                logger.error("从DBAccessManager中获取SqlSessionFactory失败，DBAccessManager中没有名称为：" + name + " 的SqlSessionFactory");
            }
            return null;
        }*/

        //return (SqlSessionFactory)this.factorys.get(name);
        return sqlSessionFactory;
    }

    public int getFactorysSize()
    {
        return this.factorys.size();
    }
}
