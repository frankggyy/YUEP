/*
 * $Id: DefaultCache.java, 2010-5-27 下午05:31:08 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.yuep.base.cache.def.Cache;
import com.yuep.base.cache.def.CacheListener;
import com.yuep.base.util.format.YuepObjectUtils;

/**
 * <p>
 * Title: DefaultCache
 * </p>
 * <p>
 * Description:
 * 缓存接口Cache默认实现
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 下午05:31:08
 * modified [who date description]
 * check [who date description]
 * @param <K> 键
 * @param <T> 值
 */
public class DefaultCache<K,T> implements Cache<K,T> {

    /**
     * cache listener
     */
    private List<CacheListener<T>> cacheListeners=new CopyOnWriteArrayList<CacheListener<T>>();
   
    /**
     * cache
     */
    private ConcurrentMap<K, T> cache=new ConcurrentHashMap<K, T>();
   
    /**
     * @param key
     * @see com.yuep.base.cache.Cache#get(java.lang.Object)
     */
    @Override
    public T get(K key) {
        return cache.get(key);
    }
  
    /**
     * 
     * @see com.yuep.base.cache.Cache#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(K key, T cacheObject) {
        if(!cache.containsKey(key)){
            cache.put(key, cacheObject);
            firedCreateCacheObject(cacheObject);
        }
        else{
          T updatedCacheObject= cache.get(key);
          YuepObjectUtils.copyProperities(updatedCacheObject, cacheObject);
          firedUpdateCacheObject(updatedCacheObject);
        }
            
    }
 
    /**
     * 
     * @see com.yuep.base.cache.Cache#remove(java.lang.Object)
     */
    @Override
    public void remove(K key) {
        T removedcacheObject=cache.remove(key);
        firedRemoveCacheObject(removedcacheObject);
    }
   
    /**
     * 
     * @see com.yuep.base.cache.Cache#clear()
     */
    @Override
    public void clear() {
        cache.clear();
    }

    /**
     * 
     * @see com.yuep.base.cache.Cache#size()
     */
    @Override
    public int size() {
        return cache.size();
    }

 
    /**
     * 
     * @see com.yuep.base.cache.Cache#addCacheListener(com.yuep.base.cache.CacheListener)
     */
    @Override
    public void addCacheListener(CacheListener<T> cacheListener) {
        cacheListeners.add(cacheListener);
    }


    /**
     * 
     * @see com.yuep.base.cache.Cache#removeCacheListener(com.yuep.base.cache.CacheListener)
     */
    @Override
    public void removeCacheListener(CacheListener<T> cacheListener) {
        cacheListeners.remove(cacheListener);
    }
 
    
    /**
     * 
     * @see com.yuep.base.cache.Cache#getCacheObjects(java.lang.Class)
     */
    @Override
    public List<T> getCacheObjects(Class<T> valueClass){
        List<T> values=new ArrayList<T>();
        for(T value:cache.values()){
            if(valueClass.isInstance(value))
                values.add(value);
        }
        return new ArrayList<T>(cache.values());
    }
    
    
    private void firedRemoveCacheObject(T cacheObject){
        for(CacheListener<T> cacheListener:cacheListeners){
            cacheListener.remove(cacheObject);
        }
    }
    
    private void firedCreateCacheObject(T cacheObject){
        for(CacheListener<T> cacheListener:cacheListeners){
            cacheListener.create(cacheObject);
        }
    }
    
    private void firedUpdateCacheObject(T cacheObject){
        for(CacheListener<T> cacheListener:cacheListeners){
            cacheListener.update(cacheObject);
        } 
    }

  

}
