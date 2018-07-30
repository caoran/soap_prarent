package com.boco.soap.cmnet.db.service;

import com.boco.soap.cmnet.db.mybitas.Session;
import com.boco.soap.cmnet.db.mybitas.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DBLocalService {
    private static final Logger logger = LoggerFactory.getLogger(DBLocalService.class);
    public static final String GET_LOCAL_DATA = "get_local_data";

    public List<Map<String, ?>> getLocalData(String sql, String filePath)
    {
        if (logger.isDebugEnabled()) {
            logger.debug("根据sql [" + sql + "]查询现网数据");
        }

        Session session = SessionFactory.getSession("FILE", filePath);
        List list = null;
        try {
            Map para = new HashMap();
            para.put("sql", sql);
            list = session.query(GET_LOCAL_DATA, para);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return list;
    }

    public List<Map<String, String>> getSourceData(String sql, String url)
    {
        List list = null;
        if (logger.isDebugEnabled()) {
            logger.debug("根据sql [" + sql + "]查询标准数据");
        }
        Session session = SessionFactory.getSession("FILE", url);
        try {
            Map para = new HashMap();
            para.put("sql", sql);
            list = session.query(GET_LOCAL_DATA, para);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
        return list;
    }
}
