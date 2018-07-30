package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.ProcessDetail;

import java.util.List;

public interface ProcessDetailMapper {
    int deleteById(String id);

    int save(ProcessDetail record);

    ProcessDetail getById(String id);

    int update(ProcessDetail record);

    List<ProcessDetail> getByProcessId(String id);
}