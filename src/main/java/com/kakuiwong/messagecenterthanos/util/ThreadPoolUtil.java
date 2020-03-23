package com.door.core.util;

import java.util.concurrent.*;

/**
 * @author: gaoyang
 * @Description:
 */
public enum ThreadPoolUtil {
    POOL;

    private ThreadPoolExecutor pool;

    private ConcurrentHashMap<Object, Object> cacheMap = new ConcurrentHashMap();

    public Object getCache(Object key) {
        return cacheMap.get(key);
    }

    public void putCache(Object key, Object val) {
        cacheMap.put(key, val);
    }

    ThreadPoolUtil() {
        BlockingQueue<Runnable> bqueue = new ArrayBlockingQueue<Runnable>(100);
        final int SIZE_CORE_POOL = Runtime.getRuntime().availableProcessors() + 1;
        final int SIZE_MAX_POOL = 100;
        final long ALIVE_TIME = 2000;
        pool = new ThreadPoolExecutor(SIZE_CORE_POOL, SIZE_MAX_POOL, ALIVE_TIME
                , TimeUnit.MILLISECONDS, bqueue, new ThreadPoolExecutor.CallerRunsPolicy());
        pool.prestartAllCoreThreads();
    }

    public ThreadPoolExecutor getPool() {
        return pool;
    }
}
