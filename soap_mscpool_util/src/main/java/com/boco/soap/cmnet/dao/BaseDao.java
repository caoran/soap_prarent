package com.boco.soap.cmnet.dao;


import java.util.List;

/**
 * @author: bolei
 * @date：2017年4月16日 下午9:34:37
 * @description：dao公共类
 */

public interface BaseDao<T> {

     List<T> getList(T t);

     T getById(Long id);

     void save(T t);

     void update(T t);

     void delete(List<Long> ids);
}