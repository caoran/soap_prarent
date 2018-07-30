package com.boco.soap.cmnet.db.mybitas;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

public interface Session {
    public abstract int insert(String paramString, Object paramObject);

    public abstract int update(String paramString, Object paramObject);

    public abstract int delete(String paramString, Object paramObject);

    public abstract List<?> query(String paramString, Object paramObject);

    public abstract void close();

    public abstract SqlSession getSession();

    public abstract void setSession(SqlSession paramSqlSession);

    public abstract void commit();

    public abstract void closeConn();

    public abstract void clearCache();

    public abstract void rollback();
}
