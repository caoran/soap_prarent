package com.boco.soap.cmnet.service.impl;

import com.boco.soap.cmnet.beans.entity.Busi;
import com.boco.soap.cmnet.dao.BusiMapper;
import com.boco.soap.cmnet.iservice.IBusiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BusiServiceImpl implements IBusiService {

    @Autowired
    private BusiMapper busiMapper;
    @Override
    public int deleteById(String id) {
        return busiMapper.deleteById(id);
    }

    @Override
    public int save(Busi record) {
        return busiMapper.save(record);
    }

    @Override
    public Busi getById(String id) {
        return busiMapper.getById(id);
    }

    @Override
    public int update(Busi record) {
        return busiMapper.update(record);
    }

    @Override
    @Cacheable(value = "busi")
    public List<Busi> getAll(Busi busi) {
        return busiMapper.getAll(busi);
    }

    @Override
    public List<Busi> getAll() {
        return getAll(new Busi());
    }
}
