package com.boco.soap.cmnet.db.mybitas.impl;

import com.boco.soap.cmnet.db.mybitas.ExtendSession;

public class  SqliteMyBatisSession extends MyBatisSession implements ExtendSession
{
    public void createTable(String id, String sql)
    {
        super.getSession().update(sql);
    }

    public void deleteTable(String id, String sql)
    {
        super.getSession().update(sql);
    }
}
