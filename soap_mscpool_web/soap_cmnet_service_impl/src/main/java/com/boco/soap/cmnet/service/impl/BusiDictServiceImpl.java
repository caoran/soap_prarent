package com.boco.soap.cmnet.service.impl;

import com.boco.soap.cmnet.beans.entity.BusiDict;
import com.boco.soap.cmnet.dao.BusiDictMapper;
import com.boco.soap.cmnet.iservice.IBusiDictService;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;
import com.boco.soap.cmnet.mongo.dao.IBaseMongoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusiDictServiceImpl implements IBusiDictService {

    @Autowired
    private IBaseMongoDAO<OrderMongo> baseMongoDAO;

    @Autowired
    private BusiDictMapper busiDictMapper;
    @Override
    public int deleteById(String id) {
        return busiDictMapper.deleteById(id);
    }

    @Override
    public int save(BusiDict record) {
        return busiDictMapper.save(record);
    }

    @Override
    public BusiDict getById(String id) {
        return busiDictMapper.getById(id);
    }

    @Override
    public int update(BusiDict record) {
        return busiDictMapper.update(record);
    }

    @Override
    public List<BusiDict> getListByBusiId(String busiId) {
        BusiDict busiDict=new BusiDict();
        busiDict.setBusiId(busiId);
        return busiDictMapper.getList(busiDict);
    }

    @Override
    public List<BusiDict> getListAndCheckStatue(String orderId) {
        OrderMongo orderMongo =baseMongoDAO.findById(orderId,OrderMongo.getCollectionName(orderId));
        List<BusiDict> busiList=this.getListByBusiId(orderMongo.getBusiId());
        for (BusiDict busiDict:busiList){
            busiDict.setChecked(false);
            if (orderMongo.getBusiDictList().containsKey(busiDict.getId())){
                busiDict.setChecked(true);
            }
        }
        return busiList;

    }
}
