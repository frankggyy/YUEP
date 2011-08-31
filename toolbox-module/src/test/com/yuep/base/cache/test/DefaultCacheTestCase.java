/*
 * $Id: DefaultCacheTestCase.java, 2010-10-28 ����02:42:52 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.cache.test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.base.cache.def.Cache;
import com.yuep.base.cache.def.CacheListener;
import com.yuep.base.cache.impl.DefaultCache;

/**
 * <p>
 * Title: DefaultCacheTestCase
 * </p>
 * <p>
 * Description:
 * DefaultCache��Ԫ����
 * </p>
 * 
 * @author yangtao
 * created 2010-10-28 ����02:42:52
 * modified [who date description]
 * check [who date description]
 */
public class DefaultCacheTestCase extends TestCase {
    
    private Cache<String,CacheObject> cache=null;
    private CacheObject cacheObject1;
    private CacheObject cacheObject2;
    /**
     * setUp�������ڳ�ʼ�����ݣ�ÿ�����Է�����ʼǰ�������
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
        cache=new DefaultCache<String,CacheObject>(); 
        setUpCacheObject();
    }
    
    private void setUpCacheObject(){
        cacheObject1=new CacheObject("cacheobject1",1);
        cacheObject2=new CacheObject("cacheobject1",2);
    }
    
    
    
    /**
     * tearDown���������������ݣ�ÿ�����Է�������֮�󶼻����
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
        cache.clear();
    }
    
    /**
     * ���Դӻ����л�ȡ����
     */
    public void testGet(){
        cache.put(cacheObject1.getName(), cacheObject1);
        CacheObject cacheObject=cache.get(cacheObject1.getName());
        assertTrue(cacheObject==cacheObject1);
    }
    
    
    /**
     * �����򻺴��д������߸�������
     */
    @SuppressWarnings("unchecked")
    public void testPut(){
        IMocksControl ctrl = createControl();
        CacheListener<CacheObject> cacheListenerMock=ctrl.createMock(CacheListener.class);
        cache.addCacheListener(cacheListenerMock);
        
        //������������,��һ�ε���create
        cacheListenerMock.create(cacheObject1);
        //�ڶ��ε��ã�update
        cacheListenerMock.update(cacheObject1);
        //׼����mock����
        replay(cacheListenerMock);
        
    
        
        cache.put(cacheObject1.getName(), cacheObject1);
        cache.put(cacheObject2.getName(), cacheObject2);
        
        CacheObject cacheObject=cache.get(cacheObject1.getName());
        assertTrue(cacheObject==cacheObject1);
        
        verify(cacheListenerMock);

    }
    
    @SuppressWarnings("unchecked")
    public void testRemove(){
        //ת��mock����
        IMocksControl ctrl = createControl();
        CacheListener<CacheObject> cacheListenerMock=ctrl.createMock(CacheListener.class);
        cache.addCacheListener(cacheListenerMock);
        //������������,��һ�ε���create
        cacheListenerMock.create(cacheObject1);
        //�ڶ��ε��ã�ɾ��
        cacheListenerMock.remove(cacheObject1);
        //׼����mock����
        replay(cacheListenerMock);
        
        cache.put(cacheObject1.getName(), cacheObject1);
        CacheObject cacheObject=cache.get(cacheObject1.getName());
        assertTrue(cacheObject==cacheObject1);
        
        cache.remove(cacheObject1.getName());
        cacheObject=cache.get(cacheObject1.getName());
        assertTrue(cacheObject==null); 
        
        verify(cacheListenerMock);
    }
    
    
    public void testClear(){
        cache.put(cacheObject1.getName(), cacheObject1);
        cache.clear();
        assertTrue(cache.size()==0); 
    }
    
    
    public void testRemoveCacheListener(){
        //ת��mock����
        IMocksControl ctrl = createControl();
        CacheListener<CacheObject> cacheListenerMock=ctrl.createMock(CacheListener.class);
        cache.addCacheListener(cacheListenerMock);
        //������������,��һ�ε���create
        cacheListenerMock.create(cacheObject1);
        //׼����mock����
        replay(cacheListenerMock);

        cache.put(cacheObject1.getName(), cacheObject1);
        CacheObject cacheObject=cache.get(cacheObject1.getName());
        assertTrue(cacheObject==cacheObject1);
        
        cache.removeCacheListener(cacheListenerMock);
        
        cache.remove(cacheObject1.getName());
        cacheObject=cache.get(cacheObject1.getName());
        assertTrue(cacheObject==null); 
        
        verify(cacheListenerMock);
    }

}
