package com.boco.soap.cmnet.check.checklogic.impl;

import com.boco.soap.cmnet.check.checklogic.ICheckLogicChange;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.List;

public class DefaltCheckLogicChangeImpl implements ICheckLogicChange {
    @Override
    public List<List<IDataCheckReturn>> transition(BusinessCheckContext paramBusinessCheckContext, List<List<IDataCheckReturn>> checkDataResultList) {
        return checkDataResultList;
    }
}
