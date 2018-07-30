package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetData;
import com.boco.soap.cmnet.check.checkdata.IBusinessCustomerReflect;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.common.util.StdColumn;
import com.boco.soap.cmnet.db.service.DBServiceManager;
import com.boco.soap.cmnet.db.service.DataCurNetService;
import com.boco.soap.cmnet.pojo.InstructionDictItem;
import com.boco.soap.cmnet.pojo.InstructionItem;
import com.boco.soap.cmnet.pojo.InstructionParam;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

public class BusinessCurNetDataImpl implements IBusinessCurNetData {

    private final Logger logger = LoggerFactory.getLogger(BusinessCurNetDataImpl.class)
            ;

    private String businessTypeXmlPath = "";

    public List<Map<String, String>> getCurNetDataInfo(String filePath, Ne neElement, String solutionId, String logTableName)
    {
        List curNetDataList = new ArrayList();
        try {
            curNetDataList = getCurNetDatas(filePath, logTableName, neElement
                    .getName(), solutionId);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curNetDataList;
    }

    public List<InstructionDictItem> getInstructDictParas(IBusinessInstruction instruction, String url)
    {
        String instructDictItemIds = new String();
        for (IInstructionParameter instructionParameter : instruction
                .getParams()) {
            if ((instructionParameter != null) &&
                    (instructionParameter
                            .getFieldUsage() != null))
            {
                if (instructionParameter
                        .getFieldUsage().getValue() == StdColumn.CHECK_FIELD
                        .intValue()) {
                    instructDictItemIds = instructDictItemIds + instructionParameter.getDictItemId() + ",";
                }
            }
        }
        return getInstructDictParasList(instruction.getDictId(), instructDictItemIds, url);
    }

    public List<InstructionItem> getInstructDictList(IBusinessInstruction instruction, String url)
    {
        List checkParamList = null;
        try {
            String dictid = String.valueOf(instruction.getDictId());

            DataCurNetService service = (DataCurNetService) DBServiceManager.getInstance()
                    .getDBService("db_datacurnet_service");

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("通过指令字典ID获取指令参数信息->" + dictid);
            }
            checkParamList = service.getInstructionDictList(dictid, url);
        }
        catch (Exception e) {
            this.logger.error("", e);
        }

        return checkParamList;
    }

    public List<InstructionDictItem> queryInstructCodeAndQuery(int instructDictId, String queryType, String url)
    {
        DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                .getDBService("db_datacurnet_service");

        String querySql = " select  *  from  ur_instruction_dict_item   where  status =0   and    dictid = " + instructDictId;

        if ((queryType != null) && (queryType.equals("1"))) {
            querySql = querySql + "  and paramtype in (1,2) ";
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("通过指令字典ID获取指令参数信息->" + querySql);
        }

        return service.getInstructDictParasList(querySql, url);
    }

    public List<InstructionParam> getInstructCodeAndQuery(String instructDictId, String queryType, String url)
    {
        DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                .getDBService("db_datacurnet_service");

        String querySql = "SELECT a.*,b.chinesename as chineseName,b.englishname as englishName,b.paramformat as format,priority,is_sort as isSort FROM ur_rule_instruction_param  a left join ur_instruction_dict_item  b on a.paramdictid = b.dictid and  a.paramitemid = b.itemid WHERE   a.status ='0'  and b.status ='0' and instructioncode='" + instructDictId + "'";

        if ((queryType != null) && (queryType.equals("1"))) {
            querySql = querySql + "  and paramproperty in (1,2) ";
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("通过指令字典ID获取指令参数信息->" + querySql);
        }

        return service.getInstructionParamList(querySql, url);
    }

    public List<InstructionDictItem> queryInstructCode(int instructDictId, String url)
    {
        DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                .getDBService("db_datacurnet_service");

        String querySql = " select  *  from  ur_instruction_dict_item   where  status =0  and   dictid = " + instructDictId + " and paramtype =3  ";

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("通过指令字典ID获取指令参数信息->" + querySql);
        }

        return service.getInstructDictParasList(querySql, url);
    }

    public List<InstructionParam> getInstructCode(String instructDictId, String url)
    {
        DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                .getDBService("db_datacurnet_service");

        String querySql = "SELECT a.*,b.chinesename as chineseName,b.englishname as englishName,b.paramformat as format,priority,is_sort as isSort FROM ur_rule_instruction_param  a left join ur_instruction_dict_item  b on a.paramdictid = b.dictid and  a.paramitemid = b.itemid WHERE   a.status ='0' and b.status ='0' and a.paramproperty=1 and instructioncode='" + instructDictId + "'";

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("通过指令字典ID获取指令参数信息->" + querySql);
        }

        return service.getInstructionParamList(querySql, url);
    }

    private List<Map<String, String>> getCurNetDatas(String filePath, String logTableName, String neElementName, String solutionId)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        String[] businessTypePath = this.businessTypeXmlPath.split(";");

        String enterBusinessTypePath = "";
        if ((businessTypePath.length > 0) &&
                (solutionId.length() > 4)) {
            String businessType = solutionId.substring(0, 4);
            for (String xmlPath : businessTypePath) {
                if (xmlPath.indexOf(businessType) != -1) {
                    enterBusinessTypePath = xmlPath;
                }
            }

        }

        List curNetDataList = null;

        DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                .getDBService("db_datacurnet_service");

        String curNetTableSql = " select  *  from  " + logTableName + " where DEVICENAME = '" + neElementName + "' ";

        Map map = extracondition(enterBusinessTypePath, logTableName, solutionId);

        String sql = "";
        String configClass = "";

        if ((map != null) && (map.size() > 0)) {
            sql = (String)map.get("curNetTableSql");
            configClass = map.get("configClass") != null ? ((String)map.get("configClass")).trim() : null;
            curNetTableSql = curNetTableSql + sql;
        }

        this.logger.info("现网数据过滤的CurNetTableSql：" + curNetTableSql);
        this.logger.info("开始进行查询现网数据 " + new Date());
        List curdatas = service.getCurNetDatas(curNetTableSql, filePath);

        this.logger.info("结束查询 " + new Date());
        if ((configClass != null) && (!configClass.equals("")))
            curNetDataList = curTableDataFiter(curdatas, map, filePath);
        else {
            curNetDataList = curdatas;
        }
        return curNetDataList;
    }

    private List<Map<String, String>> curTableDataFiter(List<Map<String, String>> listdatas, Map<String, String> map, String url)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        List curNetDataList = null;

        IBusinessCustomerReflect businessCustomerReflect = null;
        String configClass = (String)map.get("configClass");
        String filterlen = (String)map.get("filterlen");
        String[] colname = ((String)map.get("colname")).split(",");
        this.logger.info("现网数据过滤类为:" + configClass);
        this.logger.info("现网数据过滤长度为:" + filterlen);
        this.logger.info("现网数据过滤colname为:" + colname);
        Class clazz = Class.forName(configClass);

        businessCustomerReflect = (IBusinessCustomerReflect)clazz
                .newInstance();
        if (businessCustomerReflect != null)
        {
            curNetDataList = businessCustomerReflect
                    .businessCurNetDataReturn(listdatas, colname, filterlen, url);

            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Check应用现网数据过滤用户自定义class---->" + configClass);
            }
        }

        return curNetDataList;
    }

    private List<InstructionDictItem> getInstructDictParasList(int instructDictId, String instructDictItemIds, String url)
    {
        String querySql = " select  *  from  ur_instruction_dict_item   where status =0  and  dictid = " + instructDictId;

        if ((instructDictItemIds != null) &&
                (!instructDictItemIds
                        .trim().equals(""))) {
            instructDictItemIds = instructDictItemIds.substring(0, instructDictItemIds
                    .length() - 1);
            querySql = querySql + " and itemid in  (" + instructDictItemIds + ")";
        }

        DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                .getDBService("db_datacurnet_service");

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("通过指令字典ID获取指令参数信息->" + querySql);
        }
        return service.getInstructDictParasList(querySql, url);
    }

    private Map<String, String> extracondition(String enterBusinessTypePath, String logTableName, String solutionId)
    {
        File inputFile = new File(enterBusinessTypePath);

        Map map = new HashMap();

        if (inputFile.isFile()) {
            String whereSql = "";
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(inputFile);
                Element root = document.getRootElement();
                List<Element> curTablesList = root.elements();
                String curNetTableSql = "";
                String configClass = "";
                String colname = "";
                String filterlen = "";
                for (Element curElement : curTablesList) {
                    String curTableName = curElement.attribute("curTable") != null ? curElement.attribute("curTable").getText() : null;
                    String solutionIdObj = curElement.attribute("solutionId") != null ? curElement.attribute("solutionId").getText() : null;
                    if ((curTableName != null) &&
                            (curTableName
                                    .equals(logTableName)) &&
                            (solutionIdObj != null) &&
                            (solutionIdObj
                                    .equals(solutionId)))
                    {
                        whereSql = curElement.getText();
                        if ((whereSql != null) && (!whereSql.trim().equals(""))) {
                            curNetTableSql = curNetTableSql + " and  " + whereSql;
                        }
                        configClass = curElement.attribute("class") != null ? curElement.attribute("class").getText() : null;
                        colname = curElement.attribute("colname") != null ? curElement.attribute("colname").getText() : null;
                        filterlen = curElement.attribute("filterlen") != null ? curElement.attribute("filterlen").getText() : "5";
                        map.put("curNetTableSql", curNetTableSql);
                        map.put("configClass", configClass);
                        map.put("colname", colname);
                        map.put("filterlen", filterlen);
                    }
                }
                if (this.logger.isDebugEnabled())
                    this.logger.debug("现网表查询完整sql语句->" + curNetTableSql);
            }
            catch (DocumentException e1) {
                e1.printStackTrace();
            }
        }

        return map;
    }

    public String getBusinessTypeXmlPath()
    {
        return this.businessTypeXmlPath;
    }

    public void setBusinessTypeXmlPath(String businessTypeXmlPath) {
        this.businessTypeXmlPath = businessTypeXmlPath;
    }

    public List<InstructionParam> getInstructDictParamList(IBusinessInstruction instruction, String url)
    {
        return null;
    }
}
