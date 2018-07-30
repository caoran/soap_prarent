package com.boco.soap.cmnet.db.service;

import com.boco.soap.cmnet.util.SpringContextHolder;

public class DBServiceManager {

    private static DBServiceManager instance = new DBServiceManager();

    public static DBServiceManager getInstance() {
        return instance;
    }

    public <T> T getDBService(String name)
    {
        return SpringContextHolder.getBean(name);
    }
}
