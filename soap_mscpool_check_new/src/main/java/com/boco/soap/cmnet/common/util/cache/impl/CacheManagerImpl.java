package com.boco.soap.cmnet.common.util.cache.impl;

import com.boco.soap.cmnet.common.util.cache.ICache;
import com.boco.soap.cmnet.common.util.cache.ICacheManager;

import java.util.HashMap;
import java.util.Map;

public class CacheManagerImpl implements ICacheManager{
    private final Map<String, ICache<?>> caches = new HashMap();

    @Override
    public void clearCache(String id) {
        ICache cache = this.caches.get(id);
        if (cache != null) {
            cache.clear();
            this.caches.remove(id);
        }
    }

    @Override
    public void setCache(String id, ICache<?> cache) {
        this.caches.put(id, cache);
    }

    @Override
    public ICache<?> getCache(String id) {
        return this.caches.get(id);
    }

    @Override
    public int getCacheSize() {
        return this.caches.size();
    }

    @Override
    public Map<String, ICache<?>> getCaches() {
        return this.caches;
    }
}
