/*
 * $Id: CacheListener.java, 2010-5-27 下午05:01:53 yangtao Exp $
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
 * Title: CacheListener
 * </p>
 * <p>
 * Description:
 *      缓存对象生命周期监听器
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 下午05:01:53
 * modified [who date description]
 * check [who date description]
 */
public interface CacheListener<T> {
    /**
     * 缓存对象被创建
     * @param object
     *          缓存对象
     */
    public void create(T object);
    /**
     * 缓存对象被删除
     * @param object
     *          缓存对象
     */
    public void remove(T object);
    /**
     * 缓存对象被修改
     * @param object
     *          缓存对象
     */
    public void update(T object);

}
