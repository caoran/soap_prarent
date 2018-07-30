package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetServiceData;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
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
import java.util.List;
import java.util.Map;

@Service("businessCurNetServiceDataImpl")
public class BusinessCurNetServiceDataImpl
        implements IBusinessCurNetServiceData
{
    private final Logger logger = LoggerFactory.getLogger(BusinessLoginCurNetDataImpl.class)
            ;

    public List<IData> getCurDataList(CollectResult collectResult, List<IInstructionParameter> instructionParameterList)
    {
        List<Map<String, String>> curNetDataList = collectResult.getResults();

        List dataList = new ArrayList();
        if (curNetDataList != null) {
            this.logger.debug("开始现网数据curNetDataList--->>>>>>>>>>>>>>>>>>>>>>>>" + curNetDataList
                    .size());
        }
        for (Map curDataMap : curNetDataList)
        {
            List items = new ArrayList();
            String rangeCheckUp = "";
            String rangeCheckLow = "";
            for (IInstructionParameter instructionParameter : instructionParameterList) {
                if ((curDataMap != null) && (!instructionParameter.getEnglishValue().equals("GUUID"))) {
                    IDataItem dataItem = getIDataItem(instructionParameter,
                            (String)curDataMap
                                    .get(instructionParameter
                                            .getChineseName()),
                            EnumFieldUsage.valueOf(instructionParameter
                                    .getParamproperty()));
                    if (dataItem != null) {
                        items.add(dataItem);
                    }
                    if (EnumFieldUsage.valueOf(instructionParameter.getParamproperty()) == EnumFieldUsage.RANGE_UP)
                    {
                        rangeCheckUp = (String)curDataMap.get(instructionParameter.getChineseName());
                    }
                    if (EnumFieldUsage.valueOf(instructionParameter.getParamproperty()) == EnumFieldUsage.RANGE_DOWN)
                    {
                        rangeCheckLow = (String)curDataMap.get(instructionParameter.getChineseName());
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

            if ((rangeCheckUp != null) && (!rangeCheckUp.equals(""))) {
                iData.setRangeCheckedBeg(rangeCheckUp);
            }

            if ((rangeCheckLow != null) && (!rangeCheckLow.equals("")))
            {
                iData.setRangeCheckedEnd(rangeCheckLow);
            }

            dataList.add(iData);
        }

        if (curNetDataList != null) {
            this.logger.debug("完成现网数据curNetDataList--->>>>>>>>>>>>>>>>>>>>>>>>" + curNetDataList
                    .size());
        }
        return dataList;
    }

    private IDataItem getIDataItem(IInstructionParameter instructionItem, String curDataItemEnglishValue, EnumFieldUsage fieldUsage)
    {
        IDataParamDefine curDataParaDefine = new DataParamDefineImpl(instructionItem
                .getDictItemId(), instructionItem.getEnglishName(), fieldUsage);

        if ((fieldUsage == EnumFieldUsage.QUERY_FIELD) && (curDataItemEnglishValue == null))
        {
            curDataItemEnglishValue = "";
        }
        IDataItem dataItemRe = new DataItemImpl(curDataParaDefine, curDataItemEnglishValue, curDataItemEnglishValue);

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
