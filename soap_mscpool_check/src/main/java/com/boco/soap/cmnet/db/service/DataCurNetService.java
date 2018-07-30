package com.boco.soap.cmnet.db.service;

import com.boco.soap.cmnet.db.mybitas.Session;
import com.boco.soap.cmnet.db.mybitas.SessionFactory;
import com.boco.soap.cmnet.pojo.InstructionDictItem;
import com.boco.soap.cmnet.pojo.InstructionItem;
import com.boco.soap.cmnet.pojo.InstructionParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DataCurNetService {

    private static final Logger logger = LoggerFactory.getLogger(DataCurNetService.class);
    private static final String GET_CURNETTABLE_DATA = "get_curnettable_data";
    private static final String GET_INSTRUCTDICTPARAS_DATA = "get_instructdictparas_data";
    private static final String GET_INSTRUCTDICTPARAM = "get_instructdictparam";
    private static final String GET_INSTRUCTDICT = "get_instruction_dict_item_by_dictid";
    public List<Map<String, String>> getCurNetDatas(String curNetTableSql, String url)
    {
        Map para = new HashMap();

        logger.info("现网数据过滤的CurNetTableSql：" + curNetTableSql);
        para.put("CurNetTableSql", curNetTableSql);
        Session session = SessionFactory.getSession("FILE", url);
        try
        {
            List result = session.query(GET_CURNETTABLE_DATA, para);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            session.close();
        }
    }

    public List<InstructionDictItem> getInstructDictParasList(String querySql, String url)
    {
        Map para = new HashMap();
        para.put("querySql", querySql);
        Session session = SessionFactory.getSession("FILE", url);
        try
        {
            List result = session.query(GET_INSTRUCTDICTPARAS_DATA, para);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public List<InstructionParam> getInstructionParamList(String querySql, String url)
    {
        Map para = new HashMap();
        para.put("querySql", querySql);

        Session session = SessionFactory.getSession("FILE", url);
        try {
            List result = session.query(GET_INSTRUCTDICTPARAM, para);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public List<InstructionItem> getInstructionDictList(String dictid, String url)
    {
        Map para = new HashMap();
        para.put("dictid", dictid);

        Session session = SessionFactory.getSession("FILE", url);
        try {
            List result = session.query(GET_INSTRUCTDICT, para);
            return result;
        }
        catch (Exception e) {
            logger.error("{}", e);
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

}
