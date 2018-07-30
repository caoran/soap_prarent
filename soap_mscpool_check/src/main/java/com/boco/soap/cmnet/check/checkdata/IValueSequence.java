package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.check.result.ICheckData;

import java.util.Map;

public interface IValueSequence {
    public abstract Map<String, IValueSequence> getSequences();

    public abstract void setSequences(Map<String, IValueSequence> paramMap);

    public abstract int getStep();

    public abstract void setStep(int paramInt);

    public abstract int getMaxStep();

    public abstract void setMaxStep(int paramInt);

    public abstract String getPrefix();

    public abstract void setPrefix(String paramString);

    public abstract String getInit();

    public abstract void setInit(String paramString);

    public abstract String getNextValue(String paramString1, Map<String, Map<String, IValueSequence>> paramMap, String paramString2, Map<String, IValueSequence> paramMap1, ICheckData paramICheckData);

    public abstract String getGroupName();

    public abstract void setGroupName(String paramString);
}
