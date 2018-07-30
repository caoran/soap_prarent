package com.boco.soap.cmnet.service.impl;

import com.boco.soap.cmnet.beans.entity.Ne;
import com.boco.soap.cmnet.dao.NeMapper;
import com.boco.soap.cmnet.iservice.INeService;
import com.boco.soap.cmnet.mongo.bean.OrderMongo;
import com.boco.soap.cmnet.mongo.dao.IBaseMongoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class NeServiceImpl implements INeService{

    @Autowired
    private NeMapper neMapper;
    @Autowired
    private IBaseMongoDAO<OrderMongo> baseMongoDAO;

    @Override
    public int deleteById(String id) {
        return neMapper.deleteById(id);
    }

    @Override
    public int save(Ne record) {
        return neMapper.save(record);
    }

    @Override
    public Ne getById(String id) {
        return neMapper.getById(id);
    }

    @Override
    public int update(Ne record) {
        return neMapper.update(record);
    }

    @Override
    public List<String> getNeCitys() {
        List<Ne> neList=neMapper.getAll();
        Set<String> citySet=new HashSet<>();
        for (Ne ne:neList){
            citySet.add(ne.getCoverCity());
        }
        return new ArrayList<String>(citySet);
    }


    @Override
    public List<String> getNeSelected(String orderId) {
        OrderMongo orderMongo =baseMongoDAO.findById(orderId,OrderMongo.getCollectionName(orderId));
        return   orderMongo.getBusiCity();
    }
}
