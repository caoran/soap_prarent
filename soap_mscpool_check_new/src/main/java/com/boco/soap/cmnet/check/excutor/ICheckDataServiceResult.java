package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.List;

public interface ICheckDataServiceResult {
    List<IDataCheckReturn> getCheckDataGroupList(BusinessCheckContext paramBusinessCheckContext, ICheckData paramICheckData, ICheckEngine paramICheckEngine, Object paramObject);
}
