package com.boco.soap.cmnet.check.pair;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;

public interface ICheckDataPair {
    List<ICheckData> dataPair(List<ICheckData> paramList1, List<ICheckData> paramList2, DataPairConfig paramDataPairConfig, Object paramObject);
}
