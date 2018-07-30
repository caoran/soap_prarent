package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetData;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetServiceData;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.IDataParamDefine;
import com.boco.soap.cmnet.check.result.impl.CheckListImpl;
import com.boco.soap.cmnet.check.result.impl.DataItemImpl;
import com.boco.soap.cmnet.check.result.impl.DataParamDefineImpl;
import com.boco.soap.cmnet.pojo.CollectResult;
import com.boco.soap.cmnet.pojo.InstructionItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ParameCurNetServiceDataImpl implements IBusinessCurNetServiceData
{

    private static final Logger logger = LoggerFactory.getLogger(ParameCurNetServiceDataImpl.class);

    public List<IData> getCurDataList(CollectResult collectResult, List<IInstructionParameter> instructionParameterList)
    {
        return null;
    }

    public List<IData> getParCurDataList(List<CollectResult> collectResult, List<InstructionItem> instructDictParas)
    {
        List dataList = new ArrayList();
        try {
            for (Iterator localIterator1 = collectResult.iterator(); localIterator1.hasNext(); ) {
                CollectResult  collectresult = (CollectResult)localIterator1.next();

                List<Map<String, String>> curNetDataList = collectresult.getResults();

                for (Map curDataMap : curNetDataList)
                {
                    String collectkey = "";
                    if (collectresult.isSecond()) {
                        collectkey = collectresult.getCollectkey();
                    }
                    String logfilepath = collectresult.getLogfilepath();

                    if (logger.isDebugEnabled())
                    {
                        logger.debug("当前现网采集文件地址为:{},二次采集参数名称为:{},采集数量为:{}", new Object[] { logfilepath, collectkey, Integer.valueOf(curNetDataList.size()) });
                    }

                    IData idata = createdataList(instructDictParas, curDataMap, collectkey, logfilepath);
                    dataList.add(idata);
                    if (logger.isDebugEnabled())
                    {
                        logger.debug(curDataMap.toString());
                    }
                }
            }
        }
        catch (Exception e)
        {
            Iterator localIterator1;
            CollectResult collectresult;
            List curNetDataList;
            logger.error("现网数据制作失败：", e);
        }

        return dataList;
    }

    private IDataItem getParaIDataItem(InstructionItem instructionItem, String curDataItemEnglishValue, EnumFieldUsage fieldUsage)
    {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(instructionItem
                .getItemid(), instructionItem.getEnglishname(), fieldUsage);

        IDataItem dataItemRe = new DataItemImpl(curDataParaDefine, curDataItemEnglishValue, curDataItemEnglishValue);

        return dataItemRe;
    }

    private IDataItem getParaIDataItemOtm(InstructionItem instructionItem, String key, String curDataItemEnglishValue, EnumFieldUsage fieldUsage)
    {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(instructionItem
                .getItemid(), instructionItem.getEnglishname() + "*" + key, fieldUsage);

        IDataItem dataItemRe = new DataItemImpl(curDataParaDefine, curDataItemEnglishValue, curDataItemEnglishValue);

        return dataItemRe;
    }

    private IData createdataList(List<InstructionItem> instructDictParas, Map<String, String> curDataMap, String collectkey, String logfilepath)
    {
        List items = new ArrayList();
        for (InstructionItem instItem : instructDictParas) {
            if ((StringUtils.isNotBlank(instItem.getRemark())) && (instItem.getRemark().startsWith("OTM"))) {
                Iterator ite = curDataMap.entrySet().iterator();
                while (ite.hasNext()) {
                    Map.Entry ent = (Map.Entry)ite.next();
                    String orignalkey = (String)ent.getKey();
                    String targetValue = (String)ent.getValue();
                    IDataItem dataItem = null;
                    if (orignalkey.contains("*")) {
                        if (instItem.getEnglishname().equals(orignalkey.split("[*]")[0]))
                        {
                            dataItem = getParaIDataItemOtm(instItem, orignalkey.split("[*]")[1], targetValue, EnumFieldUsage.curvalueOf(instItem.getParamtype()));
                        }
                    }
                    else {
                        dataItem = getParaIDataItem(instItem, (String)curDataMap.get(instItem.getEnglishname()), EnumFieldUsage.curvalueOf(instItem.getParamtype()));
                    }
                    if (dataItem != null)
                        items.add(dataItem);
                }
            }
            else
            {
                IDataItem dataItem = getParaIDataItem(instItem, (String)curDataMap.get(instItem.getEnglishname()), EnumFieldUsage.curvalueOf(instItem.getParamtype()));
                if (dataItem != null) {
                    items.add(dataItem);
                }
            }
        }
        IData iData = new CheckListImpl(items, curDataMap, null);
        iData.setItems(items);
        iData.setCollectkey(collectkey);
        iData.setLogfile(logfilepath);
        return iData;
    }
}
