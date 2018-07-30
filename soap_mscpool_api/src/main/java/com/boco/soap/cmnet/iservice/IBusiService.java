package com.boco.soap.cmnet.iservice;


import com.boco.soap.cmnet.beans.entity.Busi;

import java.util.List;

public interface IBusiService {

    int deleteById(String id);

    int save(Busi record);

    Busi getById(String id);

    List<Busi> getAll(Busi busi);

    List<Busi> getAll();

    int update(Busi record);
}
