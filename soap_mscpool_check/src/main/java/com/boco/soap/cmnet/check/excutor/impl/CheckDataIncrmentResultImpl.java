package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCombineType;
import com.boco.soap.cmnet.beans.enums.EnumFullPartCheck;
import com.boco.soap.cmnet.check.checkdata.IBusinessCurNetData;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checklogic.CheckLogicFactory;
import com.boco.soap.cmnet.check.checklogic.ICheckLogicChange;
import com.boco.soap.cmnet.check.excutor.*;
import com.boco.soap.cmnet.check.pair.*;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.ICheckDataResult;
import com.boco.soap.cmnet.check.result.IData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CheckDataIncrmentResultImpl implements ICheckDataResult {
    private final Logger logger = LoggerFactory.getLogger(CheckDataIncrmentResultImpl.class);
    @Override
    public List<List<IDataCheckReturn>> checkDataExcuteResult(BusinessCheckContext subTaskContext) {
        this.logger.debug("指令字典现网表子线程开始-->>>>" + subTaskContext.getDictIdQuery() + "开始时间" + new Date());
        ICheckDataPair codePairData = FactoryCheckDataPair.getInstance().createCheckPairResult(subTaskContext.getCheckType());

        IRangeCheckDataPair rangeCodePairData = FactoryCheckDataPair.getInstance().createRangeCheckPairResult(subTaskContext.getCheckType());

        DataPairConfig dataPairConfig = new DataPairConfig();

        List<ICheckData> stdCheckDatas = subTaskContext.getStdCheckData();
        List curCheckDatas = subTaskContext.getCurCheckData();

        dataPairConfig.setPairType(EnumPairType.CODE_PAIR);

        boolean isFuzzy = true;
        boolean isdefaultcombine = true;
        if ((stdCheckDatas != null) && (stdCheckDatas.size() > 0)) {
            List tlist = ((ICheckData)stdCheckDatas.get(0)).getData().getInstruction().getParams();
            subTaskContext.setInstructParas(tlist);
            IBusinessInstruction instruction = ((ICheckData)stdCheckDatas.get(0)).getData().getInstruction();
            isFuzzy = instruction.isFuzzyCheck();

            //isdefaultcombine = isdefaultcombine(instruction.getCombineType());
            this.logger.debug("isFuzzy---------------->>>" + isFuzzy);
        }
        this.logger.debug("isFuzzy----------stdCheckDatas------>>>" + isFuzzy);
        dataPairConfig.setAllowAbbreviated(isFuzzy);
        dataPairConfig.setIsdefaultcombine(isdefaultcombine);
        dataPairConfig.setAllowAbbreviated(setAllowAbbreviated(subTaskContext, isFuzzy));
        this.logger.debug("指令字典现网表子线程开始-->>>>" + subTaskContext.getDictIdQuery() + "配对开始时间" + new Date());

        Map rangeCheckQuery = new HashMap();
        List checkDataResultList = new ArrayList();
        ICheckEngine iCheckEngine = CheckEngineFactory.getFactory().getCheckEngine(((ICheckData)stdCheckDatas.get(0)).getData().getInstruction());
        List dataCheckReturnList;
        if ((subTaskContext.getCheckType() == EnumFullPartCheck.RANGECHECK_FULLCHECK) || (subTaskContext.getCheckType() == EnumFullPartCheck.RANGECHECK_PARTCHECK))
        {
            IRangeCheckDataServiceResult checkDataService = CheckDataServiceFactory.getInstance().createRangeCheckDataService(subTaskContext.getCheckType());

            rangeCheckQuery = rangeCodePairData.dataPairRange(stdCheckDatas, curCheckDatas, dataPairConfig, subTaskContext);
            dataCheckReturnList = checkDataService.getCheckDataGroupList(rangeCheckQuery, iCheckEngine);
            checkDataResultList.add(dataCheckReturnList);
            return checkDataResultList;
        }

        ICheckDataServiceResult checkDataService = CheckDataServiceFactory.getInstance().createCheckDataService(subTaskContext.getCheckType());
        //dataPairConfig.isCombine = subTaskContext.getIsCombine();
        stdCheckDatas = codePairData.dataPair(stdCheckDatas, curCheckDatas, dataPairConfig, subTaskContext);
        for (ICheckData stdCheckData : stdCheckDatas)
        {
            List checkDataGroupList = checkDataService
                    .getCheckDataGroupList(subTaskContext, stdCheckData, iCheckEngine, null);

            if ((checkDataGroupList != null) && (checkDataGroupList.size() > 0)) {
                checkDataResultList.add(checkDataGroupList);
            }
        }

        this.logger.info("指令字典现网表子线程-->>>>" + subTaskContext.getDictIdQuery() + "配对结束时间" + new Date());
        this.logger.info("指令字典现网表子线程开始-->>>>" + subTaskContext.getDictIdQuery() + "核查开始时间" + new Date());

        convertData(checkDataResultList);

        this.logger.info("指令字典现网表子线程-->>>>" + subTaskContext.getDictIdQuery() + "核查结束时间" + new Date());

        return getCheckLogicChange(checkDataResultList, subTaskContext);
    }

    private boolean setAllowAbbreviated(BusinessCheckContext subTaskContext, boolean isFuzzy)
    {
        CheckLogicFactory checkLogicFactory = (CheckLogicFactory) SpringContextHolder.getBean("CheckLogicFactory");
        String dictquery = subTaskContext.getDictIdQuery();
        String[] dictname = dictquery.split("-");
        ICheckLogicChange checkLogicChange = checkLogicFactory.getCheckLogicChange(subTaskContext, dictname[0].toString(), "2");
        if ((checkLogicChange != null) && (subTaskContext.getCheckType() == EnumFullPartCheck.FULLCHECK)) {
            this.logger.info("未获取到核查处理类-->>>>" + new Date());
            return false;
        }
        return isFuzzy;
    }

    private List<List<IDataCheckReturn>> convertData(List<List<IDataCheckReturn>> convertDataReturn)
    {
        List datacheckreturn = new ArrayList();

        for (List<IDataCheckReturn> datareturn : convertDataReturn) {
            List checkdatalist = new ArrayList();

            for (IDataCheckReturn idataCheckReturn : datareturn) {
                if (idataCheckReturn != null) {
                    ICheckData checkdata = idataCheckReturn.getStdCheckData();
                    IData data = checkdata.getData();

                    IBusinessInstruction iBusinessInstruction = data
                            .getInstruction();

                    String getSysValue = iBusinessInstruction.getSysValue();

                    if ((getSysValue != null) && (getSysValue.equals("1"))) {
                        Map standdata = data.getStandData();

                        String BUSI_SCENARIO = (String)standdata.get("BUSI_SCENARIO");

                        if ((BUSI_SCENARIO != null) && (BUSI_SCENARIO.equals("是"))) {
                            checkdatalist.add(
                                    changereturntype(idataCheckReturn));
                        }
                        else {
                            checkdatalist.add(idataCheckReturn);
                        }
                    }
                    else
                    {
                        checkdatalist.add(
                                changereturntype(idataCheckReturn));
                    }
                }

            }

            datacheckreturn.add(checkdatalist);
        }

        return datacheckreturn;
    }

    private boolean isdefaultcombine(EnumCombineType combineType)
    {
        boolean isdefault = true;
        if (!combineType.equals(EnumCombineType.CombineDefault))
        {
            isdefault = false;
        }

        return isdefault;
    }

    private IDataCheckReturn changereturntype(IDataCheckReturn idataCheckReturn) {
        if (idataCheckReturn.getCheckDataType().equals(EnumCheckDataReturnType.CORRECT))
        {
            idataCheckReturn.setDataType(EnumCheckDataReturnType.EXECESS);
        } else if (idataCheckReturn.getCheckDataType().equals(EnumCheckDataReturnType.WRONG))
        {
            idataCheckReturn.setDataType(EnumCheckDataReturnType.EXECESS);
        } else if (idataCheckReturn.getCheckDataType().equals(EnumCheckDataReturnType.LOST))
        {
            idataCheckReturn.setDataType(EnumCheckDataReturnType.CORRECT);
        }
        return idataCheckReturn;
    }

    private boolean getDictIdIsCodeField(String key, String url)
    {
        IBusinessCurNetData businessCurNetData = (IBusinessCurNetData)SpringContextHolder.getBean("businessCurNetDataImpl");

        List dictlist = businessCurNetData.getInstructCode(key, url);

        if (dictlist.size() == 1) {
            return true;
        }
        return false;
    }

    private void printLogCheckData(List<List<IDataCheckReturn>> checkDataResultList)
    {
        for (List<IDataCheckReturn>log : checkDataResultList)
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
        CheckLogicFactory checkLogicFactory = (CheckLogicFactory)SpringContextHolder.getBean("CheckLogicFactory");
        String dictquery = subTaskContext.getDictIdQuery();

        String[] dictname = dictquery.split("-");

        ICheckLogicChange checkLogicChange = checkLogicFactory.getCheckLogicChange(subTaskContext, dictname[0].toString(), "1");

        return checkLogicChange.transition(subTaskContext, checkDataResultList);
    }
}
