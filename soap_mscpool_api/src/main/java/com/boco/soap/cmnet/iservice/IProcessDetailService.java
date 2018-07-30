package com.boco.soap.cmnet.iservice;

import com.boco.soap.cmnet.beans.entity.ProcessDetail;

import java.util.List;

public interface IProcessDetailService {
    int deleteById(String id);

    int save(ProcessDetail record);

    ProcessDetail getById(String id);

    List<ProcessDetail> getByProcessId(String id);

    int update(ProcessDetail record);
}
