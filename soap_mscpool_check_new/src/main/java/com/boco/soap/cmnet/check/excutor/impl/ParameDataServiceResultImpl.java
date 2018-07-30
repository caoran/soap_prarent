package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckType;
import com.boco.soap.cmnet.check.excutor.CheckerLogicFactory;
import com.boco.soap.cmnet.check.excutor.ICheckDataServiceResult;
import com.boco.soap.cmnet.check.excutor.ICheckEngine;
import com.boco.soap.cmnet.check.excutor.ICheckTypeExecutor;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ParameDataServiceResultImpl implements ICheckDataServiceResult {

    private static final Logger logger = LoggerFactory.getLogger(ParameDataServiceResultImpl.class);

    public List<IDataCheckReturn> getCheckDataGroupList(BusinessCheckContext subTaskContext, ICheckData stdCheckData, ICheckEngine checkEngine, Object extobj)
    {
        List<ICheckData> curCheckDatas = (List)extobj;

        List checkDataGroupList = new ArrayList();
        try {
            List stdlist = stdCheckData.getDatas();
            if ((stdlist == null) || (stdlist.size() == 0))
            {
                throw new Exception("标准数据设置错误！-数据为空");
            }
            for (ICheckData curCheckData : curCheckDatas) {
                List curlist = curCheckData.getDatas();

                if ((curlist == null) || (curlist.size() == 0)) {
                    throw new Exception("现网数据参数设置错误！");
                }

                if (logger.isDebugEnabled()) {
                    printcurdatas(curlist);
                }

                CheckParamePair checkparame = new CheckParamePair();
                logger.info("开始参数配对！");
                boolean ispair = checkparame.ispairdata(subTaskContext.getDbFile(), stdCheckData, curCheckData);

                if (ispair)
                {
                    logger.info("参数值配对成功，进行核查！");

                    boolean issedcollect = issedcollect(curlist);

                    //stdCheckData.setDbFile(subTaskContext.getDbFile());

                    if (issedcollect) {
                        logger.info("参数核查项{}进行二次采集核查", ((IData)stdCheckData.getDatas().get(0)).getCheckitem());
                        ICheckTypeExecutor checkFactoryExt = CheckerLogicFactory.getInstance().getCheckObjEngine(EnumCheckType.PARAMEEXTCHECK);
                        List stdCheckDatas = new ArrayList();
                        List targetCheckDatas = new ArrayList();
                        stdCheckDatas.add(stdCheckData);
                        targetCheckDatas.add(curCheckData);
                        checkDataGroupList.addAll(checkFactoryExt.checkLogic(stdCheckDatas, targetCheckDatas, checkEngine));
                    } else {
                        logger.info("参数核查项{}进普通核查", ((IData)stdCheckData.getDatas().get(0)).getCheckitem());
                        ICheckTypeExecutor checkFactory = CheckerLogicFactory.getInstance().getCheckObjEngine(EnumCheckType.PARAMEEQUALCHECK);
                        List stdCheckDatas = new ArrayList();
                        List targetCheckDatas = new ArrayList();
                        stdCheckDatas.add(stdCheckData);
                        targetCheckDatas.add(curCheckData);
                        checkDataGroupList.addAll(checkFactory.checkLogic(stdCheckDatas, targetCheckDatas, checkEngine));
                    }
                }
            }
        } catch (Exception e) { logger.error("参数核查，核查逻辑出错:", e); }

        return checkDataGroupList;
    }

    private boolean issedcollect(List<IData> curlist)
    {
        boolean result = false;
        String collectkey = ((IData)curlist.get(0)).getCollectkey();
        result = (collectkey != null) && (!collectkey.equals(""));

        return result;
    }

    private void printcurdatas(List<IData> curlist)
    {
        for (IData idata : curlist)
        {
            List<IDataItem> items = idata.getItems();

            for (IDataItem idataitems : items)
            {
                logger.info("现网参数名为:{}，Englishname：{}，EnglishValue:{}", new Object[] { idataitems.getEnglishName(), idataitems.getParam().getParamName(), idataitems.getEnglishValue() });
            }
        }
    }
}
