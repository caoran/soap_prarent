package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumCheckQuertPairType;
import com.boco.soap.cmnet.beans.enums.EnumCheckType;
import com.boco.soap.cmnet.check.excutor.*;
import com.boco.soap.cmnet.check.pair.query.DataQueryResult;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IDataCheckReturn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RangeCheckDataServiceResultImpl implements IRangeCheckDataServiceResult {
    @Override
        public List<IDataCheckReturn> getCheckDataGroupList(Map<String, List<DataQueryResult>> checkCollections, ICheckEngine checkEngine)
        {
            List checkDataGroupList = new ArrayList();
            List originalList = new ArrayList();
            for (String groupKey : checkCollections.keySet()) {
                if (groupKey.equals("lost")) {
                    List<DataQueryResult> dataQueryResultList = (List)checkCollections.get("lost");
                    for (DataQueryResult dataQueryResult : dataQueryResultList) {
                        ICheckData stdCheckData = (ICheckData)dataQueryResult.getResultStdData().get(0);
                        ICheckCommonComponent checkCommonComponent = new CheckCommonComponentImpl();

                        IDataCheckReturn dataCheckReturn = checkCommonComponent
                                .setCheckDataResult(stdCheckData, null, EnumCheckDataReturnType.LOST, EnumCheckQuertPairType.NOTPAIR);

                        stdCheckData.setStdChecked(true);
                        checkDataGroupList.add(dataCheckReturn);
                    }
                } else if (groupKey.equals("execess")) {
                    List<DataQueryResult> dataQueryResultList = (List)checkCollections.get("execess");
                    for (DataQueryResult dataQueryResult : dataQueryResultList) {
                        ICheckData curCheckData = (ICheckData)dataQueryResult.getQueryResult().get(0);
                        ICheckCommonComponent checkCommonComponent = new CheckCommonComponentImpl();

                        IDataCheckReturn dataCheckReturn = checkCommonComponent
                                .setCheckDataResult(null, curCheckData, EnumCheckDataReturnType.EXECESS, EnumCheckQuertPairType.NOTPAIR);

                        curCheckData.setStdChecked(true);
                        checkDataGroupList.add(dataCheckReturn);
                    }
                } else if (groupKey.equals("equal")) {
                    List<DataQueryResult> dataQueryResultList = (List)checkCollections.get("equal");
                    for (DataQueryResult dataQueryResult : dataQueryResultList) {
                        ICheckData curCheckData = (ICheckData)dataQueryResult.getQueryResult().get(0);
                        ICheckData stdCheckData = (ICheckData)dataQueryResult.getResultStdData().get(0);

                        ICheckTypeExecutor checkFactory = CheckerLogicFactory.getInstance()
                                .getCheckObjEngine(EnumCheckType.EQUCHECK);

                        List stdCheckDatas = new ArrayList();
                        List curCheckDatas = new ArrayList();
                        stdCheckDatas.add(stdCheckData);
                        curCheckDatas.add(curCheckData);
                        List dataCheckReturnList = checkFactory.checkLogic(stdCheckDatas, curCheckDatas, checkEngine);
                        checkDataGroupList.add(dataCheckReturnList.get(0));
                    }
                } else if (groupKey.equals("mixedLost")) {
                    List<DataQueryResult> dataQueryResultList = (List)checkCollections.get("mixedLost");
                    for (DataQueryResult dataQueryResult : dataQueryResultList) {
                        ICheckData stdCheckData = (ICheckData)dataQueryResult.getResultStdData().get(0);
                        ICheckCommonComponent checkCommonComponent = new CheckCommonComponentImpl();

                        IDataCheckReturn dataCheckReturn = checkCommonComponent
                                .setCheckDataResult(stdCheckData, null, EnumCheckDataReturnType.MIXED_LOST, EnumCheckQuertPairType.NOTPAIR);

                        stdCheckData.setStdChecked(true);
                        checkDataGroupList.add(dataCheckReturn);
                    }
                } else if (groupKey.equals("mixedExcess")) {
                    List<DataQueryResult> dataQueryResultList = (List)checkCollections.get("mixedExcess");
                    for (DataQueryResult dataQueryResult : dataQueryResultList) {
                        ICheckData curCheckData = (ICheckData)dataQueryResult.getQueryResult().get(0);
                        ICheckCommonComponent checkCommonComponent = new CheckCommonComponentImpl();
                        IDataCheckReturn dataCheckReturn = checkCommonComponent.setCheckDataResult(null, curCheckData, EnumCheckDataReturnType.MIXED_EXECESS, EnumCheckQuertPairType.NOTPAIR);

                        curCheckData.setStdChecked(true);
                        checkDataGroupList.add(dataCheckReturn);

                        ICheckData curCheckDataOriginal = dataQueryResult.getOriginalCurData();
                        if ((curCheckDataOriginal != null) &&
                                (!originalList.contains(curCheckDataOriginal.getRangeCheckedBeg()))) {
                            IDataCheckReturn dataCheckReturnOriginal = checkCommonComponent.setCheckDataResult(null, curCheckDataOriginal, EnumCheckDataReturnType.ORIGINAL_EXECESS, EnumCheckQuertPairType.NOTPAIR);

                            curCheckData.setStdChecked(true);
                            checkDataGroupList.add(dataCheckReturnOriginal);
                            originalList.add(curCheckDataOriginal.getRangeCheckedBeg());
                        }
                    }

                }
                else if (groupKey.equals("mixedEqual")) {
                    List<DataQueryResult> dataQueryResultList = (List)checkCollections.get("mixedEqual");
                    for (DataQueryResult dataQueryResult : dataQueryResultList) {
                        ICheckData curCheckData = (ICheckData)dataQueryResult.getQueryResult().get(0);
                        ICheckData stdCheckData = (ICheckData)dataQueryResult.getResultStdData().get(0);
                        ICheckData OriginalCurCheckData = dataQueryResult.getOriginalCurData();

                        ICheckTypeExecutor checkFactory = CheckerLogicFactory.getInstance()
                                .getCheckObjEngine(EnumCheckType.EQUCHECK);

                        List stdCheckDatas = new ArrayList();
                        List curCheckDatas = new ArrayList();
                        stdCheckDatas.add(stdCheckData);
                        curCheckDatas.add(curCheckData);
                        List<IDataCheckReturn> dataCheckReturnList = checkFactory.checkLogic(stdCheckDatas, curCheckDatas, checkEngine);
                        if (dataCheckReturnList.get(0) != null) {
                            if (((IDataCheckReturn)dataCheckReturnList.get(0)).getCheckDataType() == EnumCheckDataReturnType.WRONG) {
                                ((IDataCheckReturn)dataCheckReturnList.get(0)).setDataType(EnumCheckDataReturnType.MIXED_WRONG);
                                ((IDataCheckReturn)dataCheckReturnList.get(0)).setCurOriginalCheckData(OriginalCurCheckData);
                            } else if (((IDataCheckReturn)dataCheckReturnList.get(0)).getCheckDataType() == EnumCheckDataReturnType.CORRECT) {
                                ((IDataCheckReturn)dataCheckReturnList.get(0)).setDataType(EnumCheckDataReturnType.MIXED_COLLECT);
                                ((IDataCheckReturn)dataCheckReturnList.get(0)).setCurOriginalCheckData(OriginalCurCheckData);
                            }
                        }
                        checkDataGroupList.add(dataCheckReturnList.get(0));
                    }
                }

            }

            return checkDataGroupList;
    }
}
