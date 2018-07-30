package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.Ne;

import java.util.List;

public interface NeMapper {
    int deleteById(String id);

    int save(Ne record);

    Ne getById(String id);

    int update(Ne record);

    List<Ne> getAll();

    List<Ne> getByCityName(String coverCity);
}