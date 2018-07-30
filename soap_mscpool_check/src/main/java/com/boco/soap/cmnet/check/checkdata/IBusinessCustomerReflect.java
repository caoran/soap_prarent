package com.boco.soap.cmnet.check.checkdata;

import java.util.List;
import java.util.Map;

public interface IBusinessCustomerReflect {
    List<Map<String, String>> businessCurNetDataReturn(List<Map<String, String>> paramList, String[] paramArrayOfString, String paramString1, String paramString2);
}
