/*
 * $Id: Cache.java, 2010-5-27 ����05:01:18 yangtao Exp $
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
 * ����ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2010-5-27 ����05:01:18
 * modified [who date description]
 * check [who date description]
 */
public interface Cache<K,T> {

    /**
     * ����key��Cache�л�ȡ����
     * 
     * @param key
     *        ������Ķ���Ψһ��ʾ
     * @return
     *        ������������key��Ӧ�Ķ��󲻴��ڣ��򷵻�Null
     */
    public T get(K key);

    /**
     * ��Cache�м������
     * 
     * @param key
     *          ������Ķ���Ψһ��ʾ
     * @param cacheObject
     *          ���������
     */
    public void put(K key, T cacheObject);

    /**
     * ����key��Cache��ɾ������
     * 
     * @param key
     *          ������Ķ���Ψһ��ʾ
     */
    public void remove(K key);

    /**
     * ������л����е�����
     */
    public void clear();
   
    /**
     * ��ȡ������Ԫ������
     * 
     * @return
     *      ������Ԫ������
     */
    public int size();
    /**
     * ���CacheListener������
     * 
     * @param cacheListener
     *         ��������������ڼ�����
     * @see com.yuep.base.cache.CacheListener
     */
    public void addCacheListener(CacheListener<T> cacheListener);

    /**
     * ɾ��CacheListener������
     * 
     * @param cacheListener
     *        ��������������ڼ�����
     * @see com.yuep.base.cache.CacheListener
     */
    public void removeCacheListener(CacheListener<T> cacheListener);
    
    /**
     * ���ݱ���������java Class���ͻ�ȡ���������и����͵Ļ������
     * @param cacheObjectClass
     *        ����������java Class����
     * @return
     *        ���������󼯺�
     */
    public List<T> getCacheObjects(Class<T> cacheObjectClass);

}
