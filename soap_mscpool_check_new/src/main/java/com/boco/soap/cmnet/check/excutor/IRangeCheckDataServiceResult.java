package com.boco.soap.cmnet.check.excutor;

import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;

import java.util.List;
import java.util.Map;

public interface IRangeCheckDataServiceResult {
    List<IDataCheckReturn> getCheckDataGroupList(Map<String, List<DataQueryResult>> paramMap, ICheckEngine paramICheckEngine);
}
