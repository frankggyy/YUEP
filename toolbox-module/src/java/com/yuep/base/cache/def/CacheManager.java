/*
 * $Id: CacheManager.java, 2010-5-27 ����04:57:22 yangtao Exp $
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
 * �������ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 ����04:57:22
 * modified [who date description]
 * check [who date description]
 */
public interface CacheManager {

   /**
    * ����Cache
    * @param <K>
    * @param <T>
    * @param cacheObjectClass
    *        �������Java Class����
    * @param cacheObjectLoader
    *        ��������ʼ���ӿ�
    * @param params
    *        ��������ʼ������
    * @return
    *        Cache
    */
    public <K,T> Cache<K,T> createCache(Class<T> cacheObjectClass,CacheObjectLoader<K,T> cacheObjectLoader,Object...params);
    /**
     * ɾ��Cache
     * @param cacheObjectClass
     *        �������java Class����
     */
    public <T> void removeCache(Class<T> cacheObjectClass);
    /**
     * �������ͻ�ȡCache
     * @param <T>
     * @param cacheObjectClass
     *        �������java Class����
     * @return
     *        Cache
     */
    public <K,T> Cache<K,T> getCache(Class<T> cacheObjectClass);
    
}
