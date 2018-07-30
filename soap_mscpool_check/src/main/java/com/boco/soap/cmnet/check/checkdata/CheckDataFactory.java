package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataType;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataItem;
import com.boco.soap.cmnet.check.result.IDataParamDefine;
import com.boco.soap.cmnet.check.result.impl.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckDataFactory {
    private static CheckDataFactory instance = new CheckDataFactory();

    public static CheckDataFactory getInstance()
    {
        return instance;
    }

    public IDataItem getDataItem(IInstructionParameter param, Map<String, String> map, EnumFullPartCheck checktype)
    {
        IDataParamDefine paramDefine = null;
        if (param != null)
        {
            paramDefine = new DataParamDefineImpl(param);
        }
        else paramDefine = new DataParamDefineImpl();

        IDataItem result = null;
        String englishValue = (String)map.get("englishValue");
        String chineseValue = (String)map.get("chineseValue");
        String checkLogic = map.get("checkLogic") != null ? ((String)map.get("checkLogic")).toString() : "1";
        int type = Integer.parseInt(checkLogic);
        CheckOperateMnum checkdataoperator = CheckOperateMnum.getCheckOperateMnum(type);
        if ((checktype != null) && (checktype.equals(EnumFullPartCheck.PARAMECHECK)))
        {
            result = new DataItemImpl(paramDefine, checkdataoperator, map);
        }
        else result = new DataItemImpl(paramDefine, englishValue, chineseValue);

        return result;
    }

    public ICheckData getCheckData(EnumCheckDataType dataType, List<IDataItem> items, boolean isExitNet, Map<String, String> mapData)
    {
        IData data = new DataImpl(items, mapData);
        ICheckData checkData = new CheckDataImpl(data, dataType);
        checkData.setExitNet(isExitNet);
        return checkData;
    }

    public ICheckData getParameCheckData(EnumCheckDataType dataType, IBusinessInstruction instruction, List<IDataItem> items, Map<String, String> mapData)
    {
        IData data = new CheckListImpl(items, mapData, instruction);
        List datas = new ArrayList();
        datas.add(data);
        ICheckData checkData = new ParameCheckDataImpl(datas, dataType);
        return checkData;
    }

    public ICheckData getCheckRangeData(EnumCheckDataType dataType, List<IDataItem> items, boolean isExitNet, Map<String, String> mapData, String rangeCheckUp, String rangeCheckLow)
    {
        IData data = new DataImpl(items, mapData);

        if ((rangeCheckUp != null) && (!rangeCheckUp.equals(""))) {
            data.setRangeCheckedBeg(rangeCheckUp);
        }

        if ((rangeCheckLow != null) && (!rangeCheckLow.equals(""))) {
            data.setRangeCheckedEnd(rangeCheckLow);
        }

        ICheckData checkData = new CheckDataImpl(data, dataType);
        checkData.setExitNet(isExitNet);

        if ((rangeCheckUp != null) && (!rangeCheckUp.equals(""))) {
            checkData.setRangeCheckedBeg(rangeCheckUp);
        }

        if ((rangeCheckLow != null) && (!rangeCheckLow.equals(""))) {
            checkData.setRangeCheckedEnd(rangeCheckLow);
        }
        return checkData;
    }
}
