package com.boco.soap.cmnet.check.checkdata.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.ICreateSourceDatasConfig;
import com.boco.soap.cmnet.check.checkdata.IValueInvoke;
import com.boco.soap.cmnet.check.checkdata.paramchange.IParamChange;

public class CreateSourceDatasConfigImpl implements ICreateSourceDatasConfig{

    @Override
    public IBusinessInstruction getInstruction() {
        return null;
    }

    @Override
    public int getParamSize() {
        return 0;
    }

    @Override
    public IParamChange getParamChange(int paramInt) {
        return null;
    }

    @Override
    public IValueInvoke getChineseValueInvoke(int paramInt) {
        return null;
    }

    @Override
    public IValueInvoke getEnglishValueInvoke(int paramInt) {
        return null;
    }

    @Override
    public Ne getNeElement() {
        return null;
    }

    @Override
    public int getCodeIndex() {
        return 0;
    }
}
