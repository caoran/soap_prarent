package com.boco.soap.cmnet.check.pair;

public class DataPairConfig {
    private EnumPairType pairType;
    private boolean allowAbbreviated = false;

    private boolean isdefaultcombine = false;

    public int isCombine = 0;

    public EnumPairType getPairType()
    {
        return this.pairType;
    }

    public void setPairType(EnumPairType pairType) {
        this.pairType = pairType;
    }

    public boolean isAllowAbbreviated() {
        return this.allowAbbreviated;
    }

    public void setAllowAbbreviated(boolean allowAbbreviated) {
        this.allowAbbreviated = allowAbbreviated;
    }

    public boolean isdefaultcombine() {
        return this.isdefaultcombine;
    }

    public void setIsdefaultcombine(boolean isdefaultcombine) {
        this.isdefaultcombine = isdefaultcombine;
    }
}
