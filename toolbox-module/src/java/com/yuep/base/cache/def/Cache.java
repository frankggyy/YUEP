/*
 * $Id: Cache.java, 2010-5-27 下午05:01:18 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.def;

import java.util.List;

/**
 * <p>
 * Title: Cache
 * </p>
 * <p>
 * Description:
 * 缓存接口
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 下午05:01:18
 * modified [who date description]
 * check [who date description]
 */
public interface Cache<K,T> {

    /**
     * 根据key从Cache中获取对象
     * 
     * @param key
     *        被缓存的对象唯一标示
     * @return
     *        被缓存对象，如果key对应的对象不存在，则返回Null
     */
    public T get(K key);

    /**
     * 向Cache中加入对象
     * 
     * @param key
     *          被缓存的对象唯一标示
     * @param cacheObject
     *          被缓存对象
     */
    public void put(K key, T cacheObject);

    /**
     * 根据key从Cache中删除对象
     * 
     * @param key
     *          被缓存的对象唯一标示
     */
    public void remove(K key);

    /**
     * 清空所有缓存中的数据
     */
    public void clear();
   
    /**
     * 获取缓存中元素总数
     * 
     * @return
     *      缓存中元素总数
     */
    public int size();
    /**
     * 添加CacheListener监听器
     * 
     * @param cacheListener
     *         缓存对象生命周期监听器
     * @see com.yuep.base.cache.CacheListener
     */
    public void addCacheListener(CacheListener<T> cacheListener);

    /**
     * 删除CacheListener监听器
     * 
     * @param cacheListener
     *        缓存对象生命周期监听器
     * @see com.yuep.base.cache.CacheListener
     */
    public void removeCacheListener(CacheListener<T> cacheListener);
    
    /**
     * 根据被缓存对象的java Class类型获取缓存中所有该类型的缓存对象
     * @param cacheObjectClass
     *        被缓存对象的java Class类型
     * @return
     *        缓存对象对象集合
     */
    public List<T> getCacheObjects(Class<T> cacheObjectClass);

}
