package com.boco.soap.cmnet.service.impl;

import com.boco.soap.cmnet.beans.entity.ProcessDetail;
import com.boco.soap.cmnet.dao.ProcessDetailMapper;
import com.boco.soap.cmnet.iservice.IProcessDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessDetailServiceImpl implements IProcessDetailService{

    @Autowired
    private ProcessDetailMapper processDetailMapper;
    @Override
    public int deleteById(String id) {
        return processDetailMapper.deleteById(id);
    }

    @Override
    public int save(ProcessDetail record) {
        return processDetailMapper.save(record);
    }

    @Override
    public ProcessDetail getById(String id) {
        return processDetailMapper.getById(id);
    }

    @Override
    public List<ProcessDetail> getByProcessId(String id) {
        return processDetailMapper.getByProcessId(id);
    }

    @Override
    public int update(ProcessDetail record) {
        return processDetailMapper.update(record);
    }
}
