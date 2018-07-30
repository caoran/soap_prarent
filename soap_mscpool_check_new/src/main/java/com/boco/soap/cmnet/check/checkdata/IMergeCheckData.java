package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.List;
import java.util.Map;

public interface IMergeCheckData {
    Map<String, List<ICheckData>> mergeCheckData(Map<String, List<ICheckData>> paramMap1, int paramInt, EnumFullPartCheck paramEnumFullPartCheck, Map<String, List<ICheckData>> paramMap2);
}
