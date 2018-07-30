package com.boco.soap.cmnet.check.pair.operate;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.boco.soap.cmnet.check.instruction.CheckOperateMnum;
import com.boco.soap.cmnet.util.SpringContextHolder;

public class CheckOperateFactory {
    public static final Log logger = LogFactory.getLog(CheckOperateFactory.class);
    private static final String EQUALS_OPERATE = "equals_operate";
    private static final String MORE_THAN_OPERATE = "more_than_operate";
    private static final String LESS_THAN_OPERATE = "less_than_operate";
    private static final String MORE_OR_EUQALSE_OPERATE = "more_or_euqalse_operate";
    private static final String LESS_OR_EQUALSE_OPERATE = "less_or_equalse_operate";
    private static final String CONTAIN_OPERATE = "contain_operate";
    private static final String ENUM_OPERATE = "enum_operate";
    private static final String RANG_OPERATE = "rang_operate";
    private static final String BE_CONTAINED_OPERATE = "be_contained_operate";
    private static final String IGONRE_EQUALSE_OPERATE = "igonre_equalse_operate";
    private static final String COLECTION_OPERATE = "colection_operate";
    private static final String ADDCOLECTION_OPERATE = "addcolection_operate";
    private static final String TIME_LESS_THAN_OPERATE = "time_less_than_operate ";
    private static final String REGEX_OPERATE = "regex_operate";
    private static final String BIT_OPERATE = "bit_operate";
    private static final String RANGE_CONTAINS_LESS_THAN_OPERATE = "range_contains_less_than_operate";
    private static final String RANGE_BY_CONTAINS_LESS_THAN_OPERATE = "range_by_contains_less_than_operate";
    private static final CheckOperateFactory instance = new CheckOperateFactory();

    public static CheckOperateFactory getInstance()
    {
        return instance;
    }

    public ICheckOperate getCheckOperate(CheckOperateMnum operate)
    {
        if (operate == CheckOperateMnum.EQUALS) {
            return (ICheckOperate)SpringContextHolder.getBean("equals_operate");
        }
        if (operate == CheckOperateMnum.MORE_THAN) {
            return (ICheckOperate)SpringContextHolder.getBean("more_than_operate");
        }
        if (operate == CheckOperateMnum.LESS_THAN) {
            return (ICheckOperate)SpringContextHolder.getBean("less_than_operate");
        }
        if (operate == CheckOperateMnum.MORE_OR_EUQALSE) {
            return (ICheckOperate)SpringContextHolder.getBean("more_or_euqalse_operate");
        }
        if (operate == CheckOperateMnum.LESS_OR_EQUALSE) {
            return (ICheckOperate)SpringContextHolder.getBean("less_or_equalse_operate");
        }
        if (operate == CheckOperateMnum.CONTAIN) {
            return (ICheckOperate)SpringContextHolder.getBean("contain_operate");
        }
        if (operate == CheckOperateMnum.ENUM) {
            return (ICheckOperate)SpringContextHolder.getBean("enum_operate");
        }
        if (operate == CheckOperateMnum.RANG) {
            return (ICheckOperate)SpringContextHolder.getBean("rang_operate");
        }
        if (operate == CheckOperateMnum.BE_CONTAINED) {
            return (ICheckOperate)SpringContextHolder.getBean("be_contained_operate");
        }
        if (operate == CheckOperateMnum.IGONRE_EQUALSE) {
            return (ICheckOperate)SpringContextHolder.getBean("igonre_equalse_operate");
        }
        if (operate == CheckOperateMnum.COLECTION) {
            return (ICheckOperate)SpringContextHolder.getBean("colection_operate");
        }
        if (operate == CheckOperateMnum.ADDCOLECTION) {
            return (ICheckOperate)SpringContextHolder.getBean("addcolection_operate");
        }
        if (operate == CheckOperateMnum.TIME_LESS_THAN) {
            return (ICheckOperate)SpringContextHolder.getBean("time_less_than_operate ");
        }
        if (operate == CheckOperateMnum.REGEX_OPERATE) {
            return (ICheckOperate) SpringContextHolder.getBean("regex_operate");
        }
        if (operate == CheckOperateMnum.BIT_OPERATE) {
            return (ICheckOperate)SpringContextHolder.getBean("bit_operate");
        }

        if (operate == CheckOperateMnum.RANGE_CONTAINS_LESS_THAN) {
            return (ICheckOperate)SpringContextHolder.getBean("range_contains_less_than_operate");
        }
        if (operate == CheckOperateMnum.RANGE_BY_CONTAINS_LESS_THAN) {
            return (ICheckOperate)SpringContextHolder.getBean("range_by_contains_less_than_operate");
        }

        if (operate == CheckOperateMnum.EQUALS) {
            return (ICheckOperate)SpringContextHolder.getBean("equals_operate");
        }
        return null;
    }

    public ICheckOperate getCheckOperate(int type)
    {
        CheckOperateMnum operate = CheckOperateMnum.getCheckOperateMnum(type);
        return getCheckOperate(operate);
    }
}
