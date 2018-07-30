package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.enums.EnumCheckDataReturnType;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.excutor.ICheckEngine;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;
import com.boco.soap.cmnet.check.pair.operate.CheckOperateFactory;
import com.boco.soap.cmnet.check.pair.operate.ICheckOperate;
import com.boco.soap.cmnet.check.result.*;
import com.boco.soap.cmnet.check.result.impl.CheckListImpl;
import com.boco.soap.cmnet.check.result.impl.DataItemImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CheckParameEngineImpl implements ICheckEngine {
    private static final Logger log = LoggerFactory.getLogger(CheckParameEngineImpl.class);
    private int[] checkIndexs;
    private String[] checkNames;
    private ICheckOperate[] checkOperates;
    private int checkSize = 0;

    public CheckParameEngineImpl(IBusinessInstruction instruction)
    {
        init(instruction);
    }

    private void init(IBusinessInstruction instruction)
    {
        List params = instruction.getParams();

        List indexList = instruction.getCheckFiledIndexs();

        this.checkSize = indexList.size();

        this.checkIndexs = new int[this.checkSize];
        this.checkNames = new String[this.checkSize];
        this.checkOperates = new ICheckOperate[this.checkSize];

        for (int i = 0; i < this.checkSize; i++) {
            int index = ((Integer)indexList.get(i)).intValue();

            this.checkIndexs[i] = index;
            this.checkNames[i] = ((IInstructionParameter)params.get(index)).getEnglishName();

            this.checkOperates[i] =
                    CheckOperateFactory.getInstance()
                            .getCheckOperate(
                                    Integer.parseInt(((IInstructionParameter)params
                                            .get(index))
                                            .getCheckLogic()));
        }
    }

    public boolean check(ICheckData checkSourceData, ICheckData checkTargetData)
    {
        if (checkSourceData == null) {
            return false;
        }

        if (checkTargetData == null) {
            return false;
        }

        IData idata = (IData)checkSourceData.getDatas().get(0);
        IBusinessInstruction instruction = idata.getInstruction();
        init(instruction);

        boolean result = true;
        if (this.checkSize != 0)
        {
            for (int i = 0; i < this.checkSize; i++) {
                String checkName = this.checkNames[i];

                String checkSourceValue = ((IData)checkSourceData.getDatas().get(0))
                        .getValueByName(checkName, 1);

                String checkTargetValue = ((IData)checkTargetData.getDatas().get(0))
                        .getValueByName(checkName, 1);

                if (checkSourceValue != null) {
                    checkSourceValue = checkSourceValue.trim();
                }
                if (checkTargetValue != null) {
                    checkTargetValue = checkTargetValue.trim();
                }
                ICheckOperate checkOperate = this.checkOperates[i];
                boolean tempResult = checkOperate.checkOperate(checkSourceValue, checkTargetValue);

                if (log.isDebugEnabled()) {
                    log.debug("核查参数的核查结果为：[" + tempResult + "]，标准值为 ：[" + checkSourceValue + "]，现网值为 ：[" + checkTargetValue + "] 核查方法为[" + checkOperate + "]");
                }

                result = (result) && (tempResult);
            }
        }

        return result;
    }

    private IDataItem setresult(boolean checkresult, IDataItem idataiem)
    {
        if (idataiem == null)
        {
            idataiem.setItemchecktype(EnumCheckDataReturnType.CONFLICT);
        }
        else if (checkresult)
            idataiem.setItemchecktype(EnumCheckDataReturnType.CORRECT);
        else {
            idataiem.setItemchecktype(EnumCheckDataReturnType.WRONG);
        }

        return idataiem;
    }

    public int[] getCheckIndexs()
    {
        return this.checkIndexs;
    }

    public String[] getCheckNames()
    {
        return getCheckNames();
    }

    public ICheckOperate[] getCheckOperates()
    {
        return this.checkOperates;
    }

    public CheckItemResult checkparames(IData checkSourceData, IData checkTargetData)
    {
        CheckItemResult checkitem = new CheckItemResult();

        if (checkSourceData == null) {
            checkitem.setCheckresult(false);
            checkitem.setStdcheckresult(checkSourceData);
            log.error("核查对象中标准数据对象为空");
            return checkitem;
        }

        if (checkTargetData == null) {
            checkitem.setCheckresult(false);
            checkitem.setStdcheckresult(checkTargetData);
            log.error("核查对象中现网数据对象为空");
            return checkitem;
        }

        IBusinessInstruction instruction = checkSourceData.getInstruction();
        init(instruction);
        Map itemmaps = checkSourceData.getCheck();

        List items = new ArrayList();

        boolean result = true;

        if (this.checkSize != 0)
        {
            for (int i = 0; i < this.checkSize; i++) {
                String checkName = this.checkNames[i];
                if ((checkName != null) && (!checkName.equals("")))
                {
                    String checkSourceValue = checkSourceData
                            .getValueByName(checkName, 1);

                    String checkTargetValue = checkTargetData
                            .getValueByName(checkName, 1);

                    if (checkSourceValue != null) {
                        checkSourceValue = checkSourceValue.trim();

                        if (checkTargetValue != null) {
                            checkTargetValue = checkTargetValue.trim();
                        }
                        ICheckOperate checkOperate = this.checkOperates[i];
                        boolean tempResult = checkOperate.checkOperate(checkSourceValue, checkTargetValue);
                        if (log.isDebugEnabled()) {
                            log.debug("核查参数的核查结果为：[" + tempResult + "]，标准值为 ：[" + checkSourceValue + "]，现网值为 ：[" + checkTargetValue + "] 核查方法为[" + checkOperate + "]");
                        }

                        IDataItem idataitems = (IDataItem)itemmaps.get(checkName);

                        if (idataitems != null) {
                            IDataParamDefine param = idataitems.getParam();
                            Map map = contrMap(idataitems);
                            CheckOperateMnum checkOperateMnum = idataitems.getCheckdataoperator();
                            IDataItem newdataitems = new DataItemImpl(param, checkOperateMnum, map);
                            newdataitems = setresult(tempResult, newdataitems);
                            items.add(newdataitems);
                        }
                        result = (result) && (tempResult);
                    }
                }
            }
        }
        checkSourceData.setItems(items);

        checkitem.setCheckresult(result);
        checkitem.setStdcheckresult(checkSourceData);

        return checkitem;
    }

    private Map<String, String> contrMap(IDataItem idataitems)
    {
        Map map = new HashMap();
        String englishValue = idataitems.getEnglishValue();
        String chineseValue = idataitems.getChineseValue();
        String chineseName = idataitems.getChineseName();
        String englishName = idataitems.getEnglishName();
        map.put("englishValue", englishValue);
        map.put("chineseValue", chineseValue);
        map.put("chineseName", chineseName);
        map.put("englishName", englishName);
        return map;
    }

    public List<CheckItemResult> checkparamesForOtm(IData checkSourceData, IData checkTargetData) {
        List checkitemList = new ArrayList();

        CheckItemResult checkitem = new CheckItemResult();

        if (checkSourceData == null) {
            checkitem.setCheckresult(false);
            checkitem.setStdcheckresult(checkSourceData);
            log.error("核查对象中标准数据对象为空");
            checkitemList.add(checkitem);
            return checkitemList;
        }

        if (checkTargetData == null) {
            checkitem.setCheckresult(false);
            checkitem.setStdcheckresult(checkTargetData);
            log.error("核查对象中现网数据对象为空");
            checkitemList.add(checkitem);
            return checkitemList;
        }

        IBusinessInstruction instruction = checkSourceData.getInstruction();
        init(instruction);
        Map itemmaps = checkSourceData.getCheck();

        if (this.checkSize != 0) {
            for (int i = 0; i < this.checkSize; i++) {
                String checkName = this.checkNames[i];
                if ((checkName != null) && (!checkName.equals(""))) {
                    String checkSourceValue = checkSourceData.getValueByName(checkName, 1);
                    List<IDataItem> idtlist = checkTargetData.getItems();
                    List targetIdtlist = new ArrayList();
                    for (IDataItem idt : idtlist) {
                        String paramName = idt.getParam().getParamName();
                        if ((paramName.contains("*")) && (paramName.split("[*]")[0].equals(checkName))) {
                            targetIdtlist.add(idt);
                        }
                    }
                    if (checkSourceValue != null) {
                        checkSourceValue = checkSourceValue.trim();

                        Iterator idtit = targetIdtlist.iterator();
                        while (idtit.hasNext()) {
                            checkitem = new CheckItemResult();
                            List items = new ArrayList();
                            IDataItem idttmp = (IDataItem) idtit.next();
                            String checkTargetValue = idttmp.getEnglishValue();
                            if (checkTargetValue != null) {
                                checkTargetValue = checkTargetValue.trim();
                            }
                            log.debug("检查的参数名[{}] 数据参数名[{}] 参数项参数名[{}] 标准值[{}] 现网值[{}]", new Object[]{checkName, idttmp.getParamName(), idttmp.getParam().getParamName(), checkSourceValue, checkTargetValue});
                            ICheckOperate checkOperate = this.checkOperates[i];
                            boolean tempResult = checkOperate.checkOperate(checkSourceValue, checkTargetValue);
                            if (log.isDebugEnabled()) {
                                log.debug("核查参数的核查结果为：[" + tempResult + "]，标准值为 ：[" + checkSourceValue + "]，现网值为 ：[" + checkTargetValue + "] 核查方法为[" + checkOperate + "]");
                            }

                            IDataItem idataitems = (IDataItem) itemmaps.get(checkName);

                            if (idataitems != null) {
                                IDataParamDefine param = idttmp.getParam();
                                Map map = contrMap(idataitems);
                                if ((param.getFieldUsage().equals(EnumFieldUsage.CHECK_FIELD)) && (param.getParamName().contains("*"))) {
                                    map.put("chineseName", (String) map.get("chineseName") + "【" + param.getParamName().split("[*]")[1] + "】");
                                }
                                CheckOperateMnum checkOperateMnum = idataitems.getCheckdataoperator();
                                IDataItem newdataitems = new DataItemImpl(param, checkOperateMnum, map);
                                items.add(newdataitems);
                                setresult(tempResult, newdataitems);
                            }
                            IData idata = new CheckListImpl(checkSourceData.getItems(), checkSourceData.getStandData(), checkSourceData.getInstruction());
                            idata.setItems(items);
                            checkitem.setCheckresult(tempResult);
                            ((IDataItem) idata.getItems().get(0)).setEnglishName(((IDataItem) idata.getItems().get(0)).getParamName());
                            checkitem.setStdcheckresult(idata);
                            checkitemList.add(checkitem);
                        }
                    }
                }
            }
        }
        return checkitemList;
    }

}
