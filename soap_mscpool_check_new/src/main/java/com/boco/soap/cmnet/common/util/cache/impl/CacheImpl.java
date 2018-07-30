package com.boco.soap.cmnet.common.util.cache.impl;

import com.boco.soap.cmnet.common.util.cache.ICache;

import java.util.HashMap;
import java.util.Map;

public class CacheImpl<T> implements ICache<T> {
    private Map<String, T> map = new HashMap();

    public T getElement(String name)
    {
        return this.map.get(name);
    }

    public Map<String, T> getElements()
    {
        return this.map;
    }

    public synchronized boolean setElement(String name, T element)
    {
        if (!this.map.containsKey(name)) {
            this.map.put(name, element);
            return true;
        }

        return false;
    }

    public void clear()
    {
        this.map.clear();
        this.map = null;
    }

    public boolean contains(String name)
    {
        return this.map.containsKey(name);
    }

    public int size()
    {
        return 0;
    }
}
