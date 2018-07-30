package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.BusiDictItem;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetServiceData;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.IDataParamDefine;
import com.boco.soap.cmnet.check.result.impl.DataImpl;
import com.boco.soap.cmnet.check.result.impl.DataItemImpl;
import com.boco.soap.cmnet.check.result.impl.DataParamDefineImpl;
import com.boco.soap.cmnet.pojo.CollectResult;
import com.boco.soap.cmnet.pojo.InstructionItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("businessCurNetServiceDataImpl")
public class BusinessCurNetServiceDataImpl
        implements IBusinessCurNetServiceData
{
    private final Logger logger = LoggerFactory.getLogger(BusinessLoginCurNetDataImpl.class)
            ;

    public List<IData> getCurDataList(CollectResult collectResult, List<BusiDictItem> busiDictItems)
    {
        List<Map<String, String>> curNetDataList = collectResult.getResults();

        List dataList = new ArrayList();
        if (curNetDataList != null) {
            this.logger.debug("开始现网数据curNetDataList--->>>>>>>>>>>>>>>>>>>>>>>>" + curNetDataList.size());
        }
        for (Map curDataMap : curNetDataList)
        {
            List items = new ArrayList();
            for (BusiDictItem instructionParameter : busiDictItems) {
                if ((curDataMap != null) && (!instructionParameter.getCurName().equals("GUUID"))) {
                    IDataItem dataItem = getIDataItem(instructionParameter,
                            (String)curDataMap
                                    .get(instructionParameter.getCurName()),
                            EnumFieldUsage.valueOf(instructionParameter.getParamProperty()));
                    if (dataItem != null) {
                        items.add(dataItem);
                    }
                }

            }

            IDataItem dataItem = getGuuidIDataItem((String)curDataMap
                    .get("GUUID"));

            if (dataItem != null) {
                items.add(dataItem);
            }

            IData iData = new DataImpl(items);
            iData.setItems(items);
            dataList.add(iData);
        }

        if (curNetDataList != null) {
            this.logger.debug("完成现网数据curNetDataList--->>>>>>>>>>>>>>>>>>>>>>>>" + curNetDataList
                    .size());
        }
        return dataList;
    }

    private IDataItem getIDataItem(BusiDictItem instructionItem, String curDataItemEnglishValue, EnumFieldUsage fieldUsage)
    {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(0, instructionItem.getParamName(), fieldUsage);

        if ((fieldUsage == EnumFieldUsage.QUERY_FIELD) && (curDataItemEnglishValue == null))
        {
            curDataItemEnglishValue = "";
        }
        Map tempMap=new HashMap();
        tempMap.put("englishValue",curDataItemEnglishValue);
        tempMap.put("chineseValue",curDataItemEnglishValue);
        tempMap.put("englishName",instructionItem.getCurName());
        tempMap.put("chineseName",instructionItem.getStdName());
        IDataItem dataItemRe = new DataItemImpl( curDataParaDefine, CheckOperateMnum.EQUALS, tempMap);

        return dataItemRe;
    }

    private IDataItem getGuuidIDataItem(String guuidValue) {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(10000, "GUUID", EnumFieldUsage.MAKE_FIELD);

        IDataItem dataItemRe = new DataItemImpl(curDataParaDefine, guuidValue, guuidValue);

        return dataItemRe;
    }

    public List<IData> getParCurDataList(List<CollectResult> collectResult, List<InstructionItem> instructDictParas)
    {
        return null;
    }
}
