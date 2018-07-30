package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.Busi;
import com.boco.soap.cmnet.beans.entity.Menu;

public interface MenuMapper {
    int deleteById(Short id);

    int save(Menu record);

    Menu getById(Short id);

    int update(Menu record);
}