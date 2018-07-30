package com.boco.soap.cmnet.check.checklogic;

import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.List;

public interface ICheckLogicChange {
    List<List<IDataCheckReturn>> transition(BusinessCheckContext paramBusinessCheckContext, List<List<IDataCheckReturn>> paramList);
}
