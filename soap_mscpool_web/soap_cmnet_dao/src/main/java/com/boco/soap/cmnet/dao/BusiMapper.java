package com.boco.soap.cmnet.dao;

import com.boco.soap.cmnet.beans.entity.Busi;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
@Mapper
@CacheConfig(cacheNames = "busi")
public interface BusiMapper {

    //如果指定为 true，则方法调用后将立即清空所有缓存
    @CacheEvict(key ="#p0+''",allEntries=true)
    int deleteById(String id);

    int save(Busi record);

    @Cacheable(key ="#p0+''")
    Busi getById(String id);

    //#root.caches[0].name:当前被调用方法所使用的Cache, 即"user"
    @CachePut(key = "#record.id+''")
    int update(Busi record);

    @Cacheable()
    List<Busi> getAll(Busi busi);
}