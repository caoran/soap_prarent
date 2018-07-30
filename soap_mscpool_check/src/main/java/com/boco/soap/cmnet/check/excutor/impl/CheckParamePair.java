package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.pojo.InstructionItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckParamePair {
    private static Logger logger = LoggerFactory.getLogger(CheckParamePair.class);

    public IData getCurData(ICheckData stdCheckData, ICheckData curCheckData)
    {
        IData curdata = null;
        List curdatas = curCheckData.getDatas();

        if (curdatas.size() > 0)
            curdata = keydata("", stdCheckData, curdatas);
        else {
            curdata = (IData)curCheckData.getDatas().get(0);
        }

        return curdata;
    }

    public boolean ispairdata(String dbfile, ICheckData stdCheckData, ICheckData curCheckData) {
        boolean ispair = false;
        IData curdata = null;
        List curdatas = curCheckData.getDatas();
        if (curdatas.size() > 0) {
            curdata = keydata(dbfile, stdCheckData, curdatas);
        }
        if (curdata != null) {
            ispair = true;
        }
        return ispair;
    }

    private IData keydata(String dbfile, ICheckData stdCheckData, List<IData> curdatas) {
        IData curdata = null;
        IData stddata = (IData)stdCheckData.getDatas().get(0);
        List<IDataItem> dataitems = stddata.getItems();
        for (IData cdata : curdatas) {
            Map curmaps = cdata.getCheck();
            boolean iskey = true;
            for (IDataItem item : dataitems)
            {
               /* DataCurNetService service = (DataCurNetService)DBServiceManager.getInstance()
                        .getDBService("db_datacurnet_service");*/

                String dictId = item.getKey().split("[|]")[0];
                String paramName = item.getKey().split("[|]")[1];
                if (logger.isDebugEnabled()) {
                    logger.debug("通过指令字典ID获取指令参数信息->" + dictId);
                }
                List<InstructionItem> checkParamList =null;// service.getInstructionDictList(dictId, dbfile);
                InstructionItem instItem = null;
                for (InstructionItem instItemt : checkParamList) {
                    if (instItemt.getEnglishname().equals(paramName)) {
                        instItem = instItemt;
                        break;
                    }
                }
                String remark = instItem.getRemark();
                if ((StringUtils.isNotBlank(remark)) && (remark.startsWith("OTM"))) {
                    Iterator tit = curmaps.entrySet().iterator();
                    boolean f = false;
                    while (tit.hasNext()) {
                        Map.Entry ent = (Map.Entry)tit.next();
                        if ((((String)ent.getKey()).contains("*")) && (((String)ent.getKey()).split("[*]")[0].equals(item.getEnglishName()))) {
                            f = true;
                            break;
                        }
                    }
                    iskey = f;
                } else {
                    IDataItem curitem = (IDataItem)curmaps.get(item.getEnglishName());
                    String englishvale = curitem.getEnglishValue();

                    if ((englishvale != null) && (!englishvale.equals("@key"))) {
                        iskey = iskey;
                    } else {
                        if (iskey);
                        iskey = false;
                    }
                }
            }

            if (iskey) {
                curdata = cdata;
                break;
            }
        }

        if (curdata == null) {
            logger.error("参数 核查项{} 无法匹配对应的现网数据项", stddata.getCheckitem());
        }

        return curdata;
    }
}
