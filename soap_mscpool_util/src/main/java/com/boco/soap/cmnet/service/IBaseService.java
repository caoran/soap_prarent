package com.boco.soap.cmnet.service;

import java.util.List;

/**
 * @author: bolei
 * @date：2017年4月16日 下午9:13:16
 * @description：类说明
 */

public interface IBaseService<T> {

    List<T> getList(T t);

    T getById(Long id);

    void save(T t);

    void update(T t);

    void delete(List<Long> ids);

}