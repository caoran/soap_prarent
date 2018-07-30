package com.boco.soap.cmnet.check.excutor.impl;

import com.boco.soap.cmnet.beans.entity.BusiDictItem;
import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.beans.enums.EnumFieldUsage;
import com.boco.soap.cmnet.check.result.*;
import com.boco.soap.cmnet.context.impl.BusinessCheckContext;
import com.boco.soap.cmnet.db.mongo.OrderMongo;
import com.boco.soap.cmnet.db.mongo.dao.IOrderMongoDao;
import com.boco.soap.cmnet.util.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MscPollCheckDataAllResultImpl implements ICheckDataResult {
    private final Logger logger = LoggerFactory.getLogger(MscPollCheckDataAllResultImpl.class);

    private  static  Map<String, ICheckData> engineCheckDatas = new ConcurrentHashMap();


    private IOrderMongoDao orderMongoDao= SpringContextHolder.getBean("orderMongoDaoImpl");

    @Override
    public List<List<IDataCheckReturn>> checkDataExcuteResult(BusinessCheckContext subTaskContext) {
        this.logger.debug("指令字典现网表子线程开始-->>>>" + subTaskContext.getDictIdQuery() + "开始时间" + new Date());

        List<List<IDataCheckReturn>> list=new ArrayList<>();
        List<Map<String,String>> checkDataResultList = new ArrayList();

        this.logger.debug("指令字典现网表子线程开始-->>>>" + subTaskContext.getDictIdQuery() + "配对开始时间" + new Date());
        List<ICheckData> stdCheckDatas = subTaskContext.getStdCheckData();
        Map<String,List<ICheckData>> curCheckDatas = subTaskContext.getCurCheckDataMap();

        getEngineCheckDatas(curCheckDatas);

        for (ICheckData stdCheckData : stdCheckDatas)
        {
            Map<String,String> dataCheckReturn = new HashMap<String, String>();
            boolean isEqual=true;
            ICheckData targetCheckData=null;
            ICheckData sourceCheckData =null;
            Map<String,String> stdMap=new ConcurrentHashMap<>();
            for (Ne ne:subTaskContext.getNeList()) {
                String key=stdCheckData.getData().getKey();
                sourceCheckData = engineCheckDatas.get(ne.getName() + "||" + key);
                if (isEqual) {
                    if (sourceCheckData!=null) {
                        if (targetCheckData == null) {
                            targetCheckData = sourceCheckData;
                            //continue;
                        }
                        if (isEqual) {
                            isEqual = checkOperate(sourceCheckData, targetCheckData);
                        }

                    }else {
                        isEqual=false;
                        sourceCheckData=stdCheckData;
                    }
                }
                for (IDataItem dataItem:sourceCheckData.getData().getItems()){
                    if (dataItem.getParam().getFieldUsage()!=EnumFieldUsage.CODE_FILED) {
                        String map = stdMap.get(dataItem.getParamName()) == null ? "" : stdMap.get(dataItem.getParamName());
                        map += ne.getName() + " : " + dataItem.getEnglishValue() + " ;<br/>";
                        stdMap.put(dataItem.getParamName(), map);
                    }else {
                        stdMap.put(dataItem.getParamName(), dataItem.getEnglishValue());
                    }
                }
            }
            //if (!isEqual) {
                checkDataResultList.add(stdMap);
            //}
        }
        OrderMongo orderMong=subTaskContext.getOrderMongo();
        Map<String,Map> map=orderMong.getCheckResult();
        //Map<String,List> map=null;
        if (map==null){
            map=new HashMap();
        }
        Map resultMap=map.get(subTaskContext.getBusiDict().getName());
        if (resultMap==null){
            resultMap= new HashMap<>();
            map.put(subTaskContext.getBusiDict().getName(),resultMap);
        }
        resultMap.put("totalCount",subTaskContext.getTotalCount());
        List resultList=(List)resultMap.get("resultList");
        if (resultList==null){
            resultList= new ArrayList();
            resultMap.put("resultList",resultList);
        }
        resultList.addAll(checkDataResultList);
        orderMong.setCheckResult(map);

        Query query=new Query(Criteria.where("_id").is(orderMong.getId()));
        Update update=new Update().set("checkResult",map).set("endTime",new Date());
        orderMongoDao.update(query,update,orderMong.getCollectionName());

        this.logger.info("指令字典现网表子线程-->>>>" + subTaskContext.getDictIdQuery() + "配对结束时间" + new Date());
        this.logger.info("指令字典现网表子线程开始-->>>>" + subTaskContext.getDictIdQuery() + "核查开始时间" + new Date());
        return list;
    }

    private boolean checkOperate(ICheckData checkSourceData, ICheckData checkTargetData) {
        if (checkSourceData == null) {
            return false;
        }

        if (checkTargetData == null) {
            return false;
        }

        for (IDataItem dataItem:checkSourceData.getData().getItems()){
            String stdValue=dataItem.getEnglishValue();
            String curValue=checkTargetData.getData().getValueByName(dataItem.getParamName(),1);
           if ((dataItem.getParam().getFieldUsage()== EnumFieldUsage.CHECK_FIELD)&&!stdValue.equals(curValue)){
               return false;
           }
        }

        return true;
    }

    private void getEngineCheckDatas( Map<String,List<ICheckData>> curCheckDatas)
    {
        String tmpkey = "";
        if (engineCheckDatas.size()==0) {
            for (String keyStr : curCheckDatas.keySet()) {
                for (ICheckData checkData : curCheckDatas.get(keyStr)) {
                    tmpkey = checkData.getData().getKey() != null ? checkData.getData().getKey().toUpperCase() : "";
                    this.engineCheckDatas.put(keyStr.split("\\|\\|")[0] + "||" + tmpkey, checkData);
                }
            }
        }
    }
}
