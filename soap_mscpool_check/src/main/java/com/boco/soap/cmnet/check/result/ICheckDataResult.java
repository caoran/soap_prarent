package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.List;

public interface ICheckDataResult {
    List<List<IDataCheckReturn>> checkDataExcuteResult(BusinessCheckContext paramBusinessCheckContext);
}
