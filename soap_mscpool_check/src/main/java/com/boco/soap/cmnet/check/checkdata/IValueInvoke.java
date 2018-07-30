package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.entity.Ne;

import java.util.Map;

public interface IValueInvoke {
    String[] getValues(Ne paramINeElement, IInstructionParameter paramIInstructionParameter, Map<String, ?> paramMap, String paramString);
}
