/*
 * $Id: CacheObjectLoader.java, 2010-5-27 下午04:59:43 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.def;

import java.util.Map;

/**
 * <p>
 * Title: CacheObjectLoader
 * </p>
 * <p>
 * Description:
 * Cache初始化接口，用于初始化Cache
 * </p>
 * 
 * @author yangtao
 * @param <K> 键
 * @param <T> 值
 */
public interface CacheObjectLoader<K,T> {
    
    /**
     * 加载缓存对象
     * @param K 键
     * @param T 值
     * @param loadParams
     *        初始化参数
     * @return
     *        Map,key=缓存对象唯一标示 value=缓存对象
     */
    public Map<K,T> loadCacheObjects(Object...loadParams);

}
