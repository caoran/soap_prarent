package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.checkdata.paramchange.IParamChange;

public interface ICreateSourceDatasConfig {
    public abstract IBusinessInstruction getInstruction();

    public abstract int getParamSize();

    public abstract IParamChange getParamChange(int paramInt);

    public abstract IValueInvoke getChineseValueInvoke(int paramInt);

    public abstract IValueInvoke getEnglishValueInvoke(int paramInt);

    public abstract Ne getNeElement();

    public abstract int getCodeIndex();
}
