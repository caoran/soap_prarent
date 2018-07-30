package com.boco.soap.cmnet.db.mybitas.impl;

import com.boco.soap.cmnet.db.mybitas.IDBAccessManager;
import com.boco.soap.cmnet.db.mybitas.Session;
import com.boco.soap.cmnet.db.mybitas.exception.SqlException;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;

import java.sql.SQLException;
import java.util.List;

public class MyBatisSession implements Session {
    private SqlSession session = null;

    public MyBatisSession(String type, String dbAccessName)
    {
        if ((dbAccessName != null) && (!dbAccessName.equals("")))
        {
            if (type.equals("DB")) {
                IDBAccessManager manager = (IDBAccessManager) SpringContextHolder.getBean("db_access_manager");
                this.session = manager.getFactory(dbAccessName).openSession();
            }
            else if (type.equals("FILE")) {
                IDBAccessManager manager = (IDBAccessManager)SpringContextHolder.getBean("file_access_manager");
                this.session = manager.getFactory(dbAccessName).openSession(true);
            }
        }
    }

    public MyBatisSession(String type, String dbAccessName, boolean isBatch) {
        if ((isBatch) &&
                (dbAccessName != null) && (!dbAccessName.equals("")))
        {
            if (type.equals("DB")) {
                IDBAccessManager manager = (IDBAccessManager)SpringContextHolder.getBean("db_access_manager");
                this.session = manager.getFactory(dbAccessName).openSession(ExecutorType.BATCH, false);
            }
            else if (type.equals("FILE")) {
                IDBAccessManager manager = (IDBAccessManager)SpringContextHolder.getBean("file_access_manager");
                this.session = manager.getFactory(dbAccessName).openSession(ExecutorType.BATCH, false);
            }
        }
    }

    public MyBatisSession()
    {
    }

    public int insert(String id, Object data)
    {
        int i = this.session.insert(id, data);

        return i;
    }

    public int update(String id, Object data)
    {
        int i = this.session.update(id, data);
        return i;
    }

    public int delete(String id, Object data)
    {
        int i = this.session.delete(id, data);
        return i;
    }

    public List<Object> query(String id, Object data)
    {
        List result = this.session.selectList(id, data);

        return result;
    }

    public void close()
    {
        this.session.close();
        this.session = null;
    }

    public SqlSession getSession()
    {
        return this.session;
    }

    public void setSession(SqlSession session)
    {
        this.session = session;
    }

    public void commit()
    {
        this.session.commit();
    }

    public void clearCache()
    {
        this.session.clearCache();
    }

    public void rollback()
    {
        this.session.rollback();
    }

    public void closeConn()
    {
        try
        {
            this.session.getConnection().close();
        }
        catch (SQLException e) {
            try {
                throw new SqlException(e);
            }
            catch (SqlException e1) {
                e1.printStackTrace();
            }
        }
    }
}
