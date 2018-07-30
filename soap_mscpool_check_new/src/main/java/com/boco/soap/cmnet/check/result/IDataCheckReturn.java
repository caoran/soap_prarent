package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;

import java.util.List;

public interface IDataCheckReturn {
    public abstract EnumCheckDataReturnType getCheckDataType();

    public abstract void setDataType(EnumCheckDataReturnType paramEnumCheckDataReturnType);

    public abstract ICheckData getStdCheckData();

    public abstract void setStdCheckData(ICheckData paramICheckData);

    public abstract ICheckData getCurCheckData();

    public abstract void setCurCheckData(ICheckData paramICheckData);

    public abstract EnumCheckQuertPairType getPairDataType();

    public abstract void setPairDataType(EnumCheckQuertPairType paramEnumCheckQuertPairType);

    public abstract String formatString(List<IInstructionParameter> paramList, ICheckData paramICheckData1, ICheckData paramICheckData2, EnumCheckDataReturnType paramEnumCheckDataReturnType);

    public abstract String parameformatString(List<IInstructionParameter> paramList, IData paramIData1, IData paramIData2);


    void setCurOriginalCheckData(ICheckData originalCurCheckData);
}
