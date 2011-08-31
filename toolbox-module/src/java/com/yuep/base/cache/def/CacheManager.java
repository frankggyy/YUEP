/*
 * $Id: CacheManager.java, 2010-5-27 下午04:57:22 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.def;

/**
 * <p>
 * Title: CacheManager
 * </p>
 * <p>
 * Description:
 * 缓存管理接口
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 下午04:57:22
 * modified [who date description]
 * check [who date description]
 */
public interface CacheManager {

   /**
    * 创建Cache
    * @param <K>
    * @param <T>
    * @param cacheObjectClass
    *        缓存对象Java Class类型
    * @param cacheObjectLoader
    *        缓存对象初始化接口
    * @param params
    *        缓存对象初始化参数
    * @return
    *        Cache
    */
    public <K,T> Cache<K,T> createCache(Class<T> cacheObjectClass,CacheObjectLoader<K,T> cacheObjectLoader,Object...params);
    /**
     * 删除Cache
     * @param cacheObjectClass
     *        缓存对象java Class类型
     */
    public <T> void removeCache(Class<T> cacheObjectClass);
    /**
     * 根据类型获取Cache
     * @param <T>
     * @param cacheObjectClass
     *        缓存对象java Class类型
     * @return
     *        Cache
     */
    public <K,T> Cache<K,T> getCache(Class<T> cacheObjectClass);
    
}
