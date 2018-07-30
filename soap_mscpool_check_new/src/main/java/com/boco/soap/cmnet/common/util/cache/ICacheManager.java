package com.boco.soap.cmnet.common.util.cache;

import java.util.Map;

public interface ICacheManager {
    public abstract void clearCache(String paramString);

    public abstract void setCache(String paramString, ICache<?> paramICache);

    public abstract int getCacheSize();

    public abstract Map<String, ICache<?>> getCaches();

    public abstract ICache<?> getCache(String paramString);
}
