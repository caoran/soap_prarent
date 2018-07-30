package com.boco.soap.cmnet.beans.enums;

public enum  OrderStateEnum {
    CHECK_DATA(1),CHECK_CONFIG(2),CHECK_COMP(3),CHECK_RESULT(4);
    private int state;
    private  OrderStateEnum(int state){
        this.state=state;
    }
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public static OrderStateEnum getEnumByValue(int value){
        for (OrderStateEnum c : OrderStateEnum.values()) {
            if (c.state == value) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "OrderStateEnum{" +
                "state=" + state +
                '}';
    }
}
