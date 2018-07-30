package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.beans.enums.EnumCheckType;
import com.boco.soap.cmnet.check.excutor.*;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;

import java.util.ArrayList;
import java.util.List;

public class CheckDataServiceResultImpl implements ICheckDataServiceResult
{
    public List<IDataCheckReturn> getCheckDataGroupList(BusinessCheckContext subTaskContext, ICheckData stdCheckData, ICheckEngine checkEngine, Object extobj)
    {
        List checkDataGroupList = new ArrayList();
        if (!stdCheckData.isStdChecked())
        {
            boolean isLostFlag = false;

            isLostFlag = (stdCheckData.getKeyEquData() == null) &&
                    (stdCheckData
                            .getKeyAbbrData() == null) &&
                    (stdCheckData
                            .getKeyExtDatas() == null);
            if (isLostFlag)
            {
                ICheckCommonComponent checkCommonComponent = new CheckCommonComponentImpl();

                IDataCheckReturn dataCheckReturn = checkCommonComponent
                        .setCheckDataResult(stdCheckData, null, EnumCheckDataReturnType.LOST, EnumCheckQuertPairType.NOTPAIR);

                stdCheckData.setStdChecked(true);
                checkDataGroupList.add(dataCheckReturn);
                return checkDataGroupList;
            }if (stdCheckData.getKeyEquData() != null)
        {
            ICheckTypeExecutor checkFactory = CheckerLogicFactory.getInstance()
                    .getCheckObjEngine(EnumCheckType.EQUCHECK);

            List stdCheckDatas = new ArrayList();
            List curCheckDatas = new ArrayList();
            stdCheckDatas.add(stdCheckData);
            curCheckDatas.add(stdCheckData.getKeyEquData());
            return checkFactory.checkLogic(stdCheckDatas, curCheckDatas, checkEngine);
        }if (stdCheckData.getKeyAbbrData() != null)
        {
            ICheckTypeExecutor checkFactoryAbbr = CheckerLogicFactory.getInstance().getCheckObjEngine(EnumCheckType.ABBRCHECK);

            List curCheckDatas = new ArrayList();
            curCheckDatas.add(stdCheckData.getKeyAbbrData());

            return checkFactoryAbbr
                    .checkLogic(stdCheckData
                            .getKeyAbbrData()
                            .getKeyExtDatas(), curCheckDatas, checkEngine);
        }if ((stdCheckData.getKeyExtDatas() != null) &&
                (stdCheckData
                        .getKeyExtDatas().size() > 0))
        {
            ICheckTypeExecutor checkFactoryExt = CheckerLogicFactory.getInstance()
                    .getCheckObjEngine(EnumCheckType.EXTCHECK);

            List stdCheckDatas = new ArrayList();
            stdCheckDatas.add(stdCheckData);
            return checkFactoryExt.checkLogic(stdCheckDatas, stdCheckData
                    .getKeyExtDatas(), checkEngine);
        }
        } else {
            return null;
        }
        return checkDataGroupList;
    }
}
