package com.boco.soap.cmnet.check.checkdata;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.pojo.InstructionDictItem;
import com.boco.soap.cmnet.pojo.InstructionItem;
import com.boco.soap.cmnet.pojo.InstructionParam;

import java.util.List;
import java.util.Map;

public interface IBusinessCurNetData {
    public abstract List<Map<String, String>> getCurNetDataInfo(String paramString1, Ne paramINeElement, String paramString2, String paramString3);

    public abstract List<InstructionDictItem> getInstructDictParas(IBusinessInstruction paramIBusinessInstruction, String paramString);

    public abstract List<InstructionParam> getInstructDictParamList(IBusinessInstruction paramIBusinessInstruction, String paramString);

    public abstract List<InstructionDictItem> queryInstructCodeAndQuery(int paramInt, String paramString1, String paramString2);

    public abstract List<InstructionParam> getInstructCodeAndQuery(String paramString1, String paramString2, String paramString3);

    public abstract List<InstructionDictItem> queryInstructCode(int paramInt, String paramString);

    public abstract List<InstructionParam> getInstructCode(String paramString1, String paramString2);

    public abstract List<InstructionItem> getInstructDictList(IBusinessInstruction paramIBusinessInstruction, String paramString);
}
