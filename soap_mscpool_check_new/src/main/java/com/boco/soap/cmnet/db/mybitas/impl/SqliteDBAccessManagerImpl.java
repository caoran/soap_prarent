package com.boco.soap.cmnet.db.mybitas.impl;

import com.boco.soap.cmnet.db.mybitas.IDBAccessManager;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SqliteDBAccessManagerImpl implements IDBAccessManager
{
    private static final Logger logger = LoggerFactory.getLogger(SqliteDBAccessManagerImpl.class);

    private String filePath = "";

    private Map<String, SqlSessionFactory> factorys = new HashMap();

    public SqliteDBAccessManagerImpl(String file)
    {
        this.filePath = file;
    }

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
                logger.error("从SqliteDBAccessManager中获取SqlSessionFactory失败，SqlSessionFactory名称为：" + name);
            }
            return null;
        }if (!this.factorys.containsKey(name)) {
            try
            {
                return createFactory(name);
            }
            catch (Exception e) {
                e.printStackTrace();
                if (logger.isErrorEnabled()) {
                    logger.error("从SqliteDBAccessManager中获取SqlSessionFactory失败，DBAccessManager中没有名称为：" + name + " 的SqlSessionFactory");
                }
                return null;
            }

        }

        return (SqlSessionFactory)this.factorys.get(name);
    }

    public int getFactorysSize()
    {
        return this.factorys.size();
    }

    private SqlSessionFactory createFactory(String url) throws IOException
    {
        Reader reader = Resources.getResourceAsReader(this.filePath);
        Properties proper = createSQLiteProperties(url);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, proper);

        this.factorys.put(url, sqlSessionFactory);
        return sqlSessionFactory;
    }

    private Properties createSQLiteProperties(String url)
    {
        Properties proper = new Properties();
        proper.put("driver", "org.sqlite.JDBC");
        proper.put("username", "");
        proper.put("password", "");
        proper.put("url", "jdbc:sqlite:/" + url);
        return proper;
    }
}
