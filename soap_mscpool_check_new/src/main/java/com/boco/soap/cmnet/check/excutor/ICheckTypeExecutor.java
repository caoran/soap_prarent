package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;

import java.util.List;

public interface ICheckTypeExecutor {
    List<IDataCheckReturn> checkLogic(List<ICheckData> paramList1, List<ICheckData> paramList2, ICheckEngine paramICheckEngine);
}
