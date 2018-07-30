package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.Process;

public interface ProcessMapper {
    int deleteById(String id);

    int save(Process record);

    Process getById(String id);

    int update(Process record);

}