package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;

public interface ICheckCommonComponent {
    public abstract IDataCheckReturn checkPairEqualCheckData(ICheckData paramICheckData1, ICheckData paramICheckData2, EnumCheckQuertPairType paramEnumCheckQuertPairType, boolean paramBoolean);

    public abstract IDataCheckReturn setCheckDataResult(ICheckData paramICheckData1, ICheckData paramICheckData2, EnumCheckDataReturnType paramEnumCheckDataReturnType, EnumCheckQuertPairType paramEnumCheckQuertPairType);
}
