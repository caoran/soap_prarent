package com.boco.soap.cmnet.check.result;

import com.boco.soap.cmnet.check.checkdata.IBusinessInstruction;

import java.util.List;

public interface IBusinessConstraint {
    public abstract String getId();

    public abstract String getConstraintMatchName();

    public abstract Object getMatchParameter();

    public abstract List<IBusinessInstruction> getInstructions();

    public abstract void addInstruction(IBusinessInstruction paramIBusinessInstruction);

    public abstract void setInstructions(List<IBusinessInstruction> paramList);
}
