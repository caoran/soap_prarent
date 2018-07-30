package com.boco.soap.cmnet.db.mybitas;

import com.boco.soap.cmnet.db.mybitas.impl.MyBatisSession;

public class SessionFactory {
    public static final String DB = "DB";
    public static final String FILE = "FILE";

    public static Session getSession(String type, String dbAccessName)
    {
        if ((dbAccessName == null) || (dbAccessName.equals("")))
        {
            return null;
        }
        Session session = new MyBatisSession(type, dbAccessName);
        return session;
    }

    public static Session getBatchSession(String type, String dbAccessName)
    {
        if ((dbAccessName == null) || (dbAccessName.equals("")))
        {
            return null;
        }
        Session session = new MyBatisSession(type, dbAccessName, true);
        return session;
    }
}
