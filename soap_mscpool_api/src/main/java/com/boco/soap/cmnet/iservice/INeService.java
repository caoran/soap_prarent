package com.boco.soap.cmnet.iservice;

import com.boco.soap.cmnet.beans.entity.Ne;

import java.util.List;

public interface INeService {
    int deleteById(String id);

    int save(Ne record);

    Ne getById(String id);

    int update(Ne record);

    List<String> getNeCitys();

    List<String> getNeSelected(String orderId);
}
