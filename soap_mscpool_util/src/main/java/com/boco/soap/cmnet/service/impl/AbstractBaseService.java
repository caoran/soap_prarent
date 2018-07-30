package com.boco.soap.cmnet.util.service.impl;

import com.boco.soap.cmnet.dao.BaseDao;
import com.boco.soap.cmnet.service.IBaseService;
import com.boco.soap.cmnet.util.ReflectUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: bolei
 * @date：2017年4月16日 下午9:13:16
 * @description：类说明
 */

public abstract class AbstractBaseService<T> implements IBaseService<T> {

    @Autowired
    private BaseDao<T> baseDao;
    protected Class<T> clazz;

    public AbstractBaseService() {
        clazz = (Class<T>) ReflectUtils.getGenericClass(getClass());
    }

    @Override
    public List<T> getList(T t) {
        return baseDao.getList(t);
    }

    @Override
    public void save(T t) {
        if (t == null)
            return;
        try {
            baseDao.save(t);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(T t) {
        if (t == null)
            return;
        try {
            baseDao.update(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(List<Long> ids) {
        if (ids == null) {
            return;
        }
        try {
            baseDao.delete(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public T getById(Long id) {
        return baseDao.getById(id);
    }

}