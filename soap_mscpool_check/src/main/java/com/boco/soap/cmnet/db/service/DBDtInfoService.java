package com.boco.soap.cmnet.db.service;

import com.boco.soap.cmnet.db.mybitas.Session;
import com.boco.soap.cmnet.db.mybitas.SessionFactory;
import com.boco.soap.cmnet.pojo.DtInfo;
import com.boco.soap.cmnet.pojo.OperationMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDtInfoService {
    private static final Logger logger = LoggerFactory.getLogger(DBDtInfoService.class);
    private static final String GET_OPERATIONMAPINFO_BY_DICTID = "get_operationmapinfo_by_dictid";
    private static final String INSERT_CHECK_RESULT_DT = "insert_check_result_dt";
    private static final String GET_INSTRUCTION_DICT_INFO = "get_instruction_dict_info";

    public List<OperationMap> getOperationMap(Map<String, Object> map)
    {
        String standardoperation = map.get("standardoperation") != null ? (String)map.get("standardoperation") : null;
        String dictId = map.get("dictId") != null ? (String)map.get("dictId") : null;
        int productionmode = map.get("productionmode") != null ? ((Integer)map.get("productionmode")).intValue() : 0;
        int inscategory = map.get("inscategory") != null ? ((Integer)map.get("inscategory")).intValue() : 0;
        String operationtype = map.get("operationtype") != null ? (String)map.get("operationtype") : null;
        int instype = map.get("instype") != null ? ((Integer)map.get("instype")).intValue() : 0;
        String dbFile = map.get("url") != null ? (String)map.get("url") : null;

        String getOperationMapSql = " select  *  from  ur_operation_map where 1=1 ";

        if (dictId != null) {
            getOperationMapSql = getOperationMapSql + " and dictId =" + dictId;
        }
        if (standardoperation != null) {
            getOperationMapSql = getOperationMapSql + " and standardoperation ='" + standardoperation + "'";
        }
        if (productionmode > 0) {
            getOperationMapSql = getOperationMapSql + " and productionmode =" + productionmode;
        }
        if (inscategory > 0) {
            getOperationMapSql = getOperationMapSql + " and inscategory =" + inscategory;
        }
        if (operationtype != null) {
            getOperationMapSql = getOperationMapSql + " and operationtype in (" + operationtype + ")";
        }
        if (instype > 0) {
            getOperationMapSql = getOperationMapSql + " and instype =" + instype;
        }
        getOperationMapSql = getOperationMapSql + "   order by INSSEQUENCE   ";

        if (logger.isDebugEnabled()) {
            logger.debug("根据sql--->[" + getOperationMapSql + "]查询该操作影射集合");
        }

        List results = null;

        Session session = SessionFactory.getSession("FILE", dbFile);
        Map para = new HashMap();
        para.put("operationMapSql", getOperationMapSql);
        try
        {
            results = session.query("get_operationmapinfo_by_dictid", para);

            return results;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            session.close();
        }
    }

    public List<Map<String, ?>> getInstructionDictInfo(String dictId, String dbFile)
    {
        Map para = new HashMap();
        para.put("dictid", dictId);
        Session session = SessionFactory.getSession("DB", "soap");
        try
        {
            List result = session.query("get_instruction_dict_info", para);
            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            session.close();
        }
    }

    public void insertDts(List<DtInfo> dtinfos)
    {
        Session session = SessionFactory.getBatchSession("DB", "result");
        try
        {
            for (DtInfo temp : dtinfos) {
                session.insert("insert_check_result_dt", temp);
            }
            session.commit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            session.close();
        }
    }
}
