package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;
import com.boco.soap.cmnet.check.checkdata.IInstructionParameter;
import com.boco.soap.cmnet.check.excutor.ICheckEngine;
import com.boco.soap.cmnet.check.pair.operate.CheckOperateFactory;
import com.boco.soap.cmnet.check.pair.operate.ICheckOperate;
import com.boco.soap.cmnet.check.result.CheckItemResult;
import com.boco.soap.cmnet.check.result.ICheckData;
import com.boco.soap.cmnet.check.result.IData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CheckEngineImpl implements ICheckEngine {
    private static final Logger log = LoggerFactory.getLogger(CheckEngineImpl.class);
    private int[] checkIndexs;
    private String[] checkNames;
    private ICheckOperate[] checkOperates;
    private int checkSize = 0;

    public CheckEngineImpl(IBusinessInstruction instruction)
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

        IBusinessInstruction instruction = checkSourceData.getData()
                .getInstruction();

        init(instruction);

        boolean result = true;
        if (this.checkSize != 0)
        {
            for (int i = 0; i < this.checkSize; i++) {
                String checkName = this.checkNames[i];

                String checkSourceValue = checkSourceData.getData()
                        .getValueByName(checkName, 1);

                String checkTargetValue = checkTargetData.getData()
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
        return null;
    }

    public List<CheckItemResult> checkparamesForOtm(IData checkSourceData, IData checkTargetData)
    {
        return null;
    }
}
