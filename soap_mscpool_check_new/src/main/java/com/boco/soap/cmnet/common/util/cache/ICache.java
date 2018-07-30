package com.boco.soap.cmnet.common.util.cache;

import java.util.Map;

public interface ICache<T> {
    public abstract T getElement(String paramString);

    public abstract boolean setElement(String paramString, T paramT);

    public abstract int size();

    public abstract Map<String, T> getElements();

    public abstract void clear();

    public abstract boolean contains(String paramString);
}
