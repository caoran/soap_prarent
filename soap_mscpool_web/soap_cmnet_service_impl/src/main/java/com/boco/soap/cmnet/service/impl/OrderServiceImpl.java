package com.boco.soap.cmnet.service.impl;

import com.boco.soap.cmnet.beans.entity.*;
import com.boco.soap.cmnet.dao.*;
import com.boco.soap.cmnet.iservice.IOrderService;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;
import com.boco.soap.cmnet.mongo.dao.IBaseMongoDAO;
import com.boco.soap.cmnet.mongo.dao.IOrderMongoDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BusiMapper busiMapper;
    @Autowired
    private BusiDictMapper busiDictMapper;
    @Autowired
    private BusiDictItemMapper busiDictItemMapper;
    @Autowired
    private IBaseMongoDAO baseMongoDAO;
    @Autowired
    private CommonMapper commonMapper;
    @Autowired
    private NeMapper neMapper;
    @Autowired
    private IOrderMongoDao orderMongoDao;


    @Override
    public int deleteById(String id) {
        return orderMapper.deleteById(id);
    }

    @Override
    public int save(Order record) {
        return orderMapper.save(record);
    }

    @Override
    public Order getById(String id) {
        return orderMapper.getById(id);
    }
    @Override
    public int update(Order record) {
        return orderMapper.update(record);
    }

    @Override
    public List<Order> getList(Order order) {
        return orderMapper.getList(order);
    }

    @Override
    public String getOrderName(String busiId) {
        //SimpleDateFormat sf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        DateTimeFormatter sf= DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        Order order=new Order();
        order.setBusiId(busiId);
        order.setCreateTime(new Date());
        List<Order> orderList=orderMapper.getList(order);
        int count=orderList.size();
        Busi busi=busiMapper.getById(busiId);
        return String.format("%s-%s-%d",busi.getBusiName(),sf.format(LocalDateTime.now()),count).replace("-","_");
    }

    @Override
    public OrderMongo getOrderCheckResult(String orderId) {
        return orderMongoDao.findById(orderId);
    }




    @Override
    public void saveOrderStd(Order order, String city, String[] busiDictList) {
        OrderMongo orderMongo=new OrderMongo();
        List cityList=new ArrayList<String>();
        cityList.add(city);
        orderMongo.setBusiCity(cityList);
        Busi busi=busiMapper.getById(order.getBusiId());
        Map<String,BusiDict> busiDictListTmp=new HashMap<>();
        Map<String,List> curTableMap=new HashMap<>();
        //Map<String,List> orderCheckResult=new HashMap<>();
        Map<String,Ne> orderNeMap=new HashMap<>();
        for (int i = 0; i <busiDictList.length; i++) {
            BusiDict busiDict=busiDictMapper.getById(busiDictList[i]);
            List busiDictItems=busiDictItemMapper.getByDictId(busiDictList[i]);
            busiDict.setBusiDictItems(busiDictItems);
            busiDictListTmp.put(busiDict.getName(),busiDict);
            String curTableStr=busiDict.getCurTable();
            curTableMap.put(curTableStr,/*commonMapper.getListByTableName(curTableStr)*/Collections.emptyList());
            //orderCheckResult.put(busiDict.getBusiId(),new ArrayList());
        }
        List<Ne> neList=neMapper.getByCityName(city);
        for (Ne neTmp:neList){
            orderNeMap.put(neTmp.getName(),neTmp);
        }
        orderMongo.setNeList(orderNeMap);
        BeanUtils.copyProperties(order, orderMongo);
        orderMongo.setBusiDictList(busiDictListTmp);
        orderMongo.setBusiName(busi.getBusiName());
        orderMongo.setStdTable(Collections.emptyMap());
        orderMongo.setCurTable(curTableMap);
        //orderMongo.setCheckResult(orderCheckResult);
        baseMongoDAO.save(orderMongo,orderMongo.getCollectionName());
    }


    @Override
    public List<Map<String,Object>> getOrderDetail(String orderId, String busiDictName, String paramName, String paramValue) {
        List resultList=new ArrayList();
        List<Map<String,Object>> list=(List<Map<String,Object>>)orderMongoDao.findById(orderId).getCheckResult().get(busiDictName).get("resultList");
        for (Map tempMap:list){
            if (tempMap.containsKey(paramName)&&tempMap.get(paramName).toString().contains(paramValue)){
                resultList.add(tempMap);
            }
        }

        return resultList;
    }

}
