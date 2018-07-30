package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckType;
import com.boco.soap.cmnet.check.checklogic.CheckLogicFactory;
import com.boco.soap.cmnet.check.checklogic.ICheckLogicChange;
import com.boco.soap.cmnet.check.excutor.CheckerLogicFactory;
import com.boco.soap.cmnet.check.excutor.ICheckTypeExecutor;
import com.boco.soap.cmnet.check.result.ICheckDataResult;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CheckDataAllResultImpl implements ICheckDataResult
{
    private final Logger logger = LoggerFactory.getLogger(CheckDataAllResultImpl.class);

    @Override
    public List<List<IDataCheckReturn>> checkDataExcuteResult(BusinessCheckContext subTaskContext) {
        ICheckDataResult checkDataIncrmentResult = new CheckDataIncrmentResultImpl();

        List checkDataResultList = checkDataIncrmentResult.checkDataExcuteResult(subTaskContext);

        ICheckTypeExecutor checkFactory = CheckerLogicFactory.getInstance()
                .getCheckObjEngine(EnumCheckType.CHECKEXECESS);

        List execessCheckDataGroup = checkFactory.checkLogic(subTaskContext
                .getStdCheckData(), subTaskContext.getCurCheckData(), null);
        if ((execessCheckDataGroup != null) && (execessCheckDataGroup.size() > 0)) {
            String dictquery = subTaskContext.getDictIdQuery();
            String[] dictname = dictquery.split("-");

            if (dictname[1].equals("NSN_MSS_RQI")) {
                List convertDataReturn = convertData(execessCheckDataGroup);
                checkDataResultList.add(convertDataReturn);
            } else {
                checkDataResultList.add(execessCheckDataGroup);
            }
        }

        if (this.logger.isDebugEnabled()) {
            printLogCheckData(checkDataResultList);
        }
        return getCheckLogicChange(checkDataResultList, subTaskContext);
    }

    private List<IDataCheckReturn> convertData(List<IDataCheckReturn> convertDataReturn)
    {
        List datacheckreturn = new ArrayList();

        for (IDataCheckReturn idataCheckReturn : convertDataReturn) {
            if (idataCheckReturn != null)
            {
                datacheckreturn.add(changereturntype(idataCheckReturn));
            }

        }

        return datacheckreturn;
    }

    private IDataCheckReturn changereturntype(IDataCheckReturn idataCheckReturn)
    {
        if (idataCheckReturn.getCheckDataType().equals(EnumCheckDataReturnType.EXECESS))
        {
            idataCheckReturn.setDataType(EnumCheckDataReturnType.CORRECT);
        } else if (idataCheckReturn.getCheckDataType().equals(EnumCheckDataReturnType.CORRECT))
        {
            idataCheckReturn.setDataType(EnumCheckDataReturnType.EXECESS);
        }
        return idataCheckReturn;
    }

    private void printLogCheckData(List<List<IDataCheckReturn>> checkDataResultList)
    {
        for (List<IDataCheckReturn> log : checkDataResultList)
            for (IDataCheckReturn dataCheckReturn : log)
                if (this.logger.isDebugEnabled())
                    this.logger
                            .debug("核查数据类型 [{}],核查配对类型 [{}],核查标准数据key [{}],核查现网数据key [{}]", new Object[] { dataCheckReturn
                                    .getCheckDataType(), dataCheckReturn
                                    .getPairDataType(), dataCheckReturn
                                    .getStdCheckData() == null ? "标准数据为空" : dataCheckReturn
                                    .getStdCheckData()
                                    .getData().getKey(), dataCheckReturn
                                    .getCurCheckData() == null ? "现网数据为空" : dataCheckReturn
                                    .getCurCheckData()
                                    .getData().getKey() });
    }

    private List<List<IDataCheckReturn>> getCheckLogicChange(List<List<IDataCheckReturn>> checkDataResultList, BusinessCheckContext subTaskContext)
    {
        CheckLogicFactory checkLogicFactory = (CheckLogicFactory) SpringContextHolder.getBean("CheckLogicFactory");
        String dictquery = subTaskContext.getDictIdQuery();

        String[] dictname = dictquery.split("-");

        ICheckLogicChange checkLogicChange = checkLogicFactory.getCheckLogicChange(subTaskContext, dictname[0].toString(), "1");

        return checkLogicChange.transition(subTaskContext, checkDataResultList);
    }
}
