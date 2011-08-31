/*
 * $Id: DefaultCacheManagerTestCase.java, 2010-10-28 下午02:42:06 Administrator Exp $
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
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.base.cache.def.Cache;
import com.yuep.base.cache.def.CacheManager;
import com.yuep.base.cache.def.CacheObjectLoader;
import com.yuep.base.cache.impl.DefaultCacheManager;

/**
 * <p>
 * Title: DefaultCacheManagerTestCase
 * </p>
 * <p>
 * Description:
 * <br>默认的缓存管理器测试用例
 * </p>
 * 
 * @author yangtao
 * created 2010-10-28 下午02:42:06
 * modified [who date description]
 * check [who date description]
 */
public class DefaultCacheManagerTestCase extends TestCase {
    
    private CacheManager cacheManager;
    private CacheObject cacheObject1;
    @Override
    public void setUp(){
        cacheManager=new DefaultCacheManager();
        setUpCacheObject();
    }
    
    @Override
    public void tearDown(){
        cacheManager=null;
    }
    
    private void setUpCacheObject(){
        cacheObject1=new CacheObject("cacheobject1",1);
    }
    
    
    @SuppressWarnings("unchecked")
    public void testCreateCache(){
        IMocksControl ctrl = createControl();
        
        //Mock CacheObjectLoader对象
        CacheObjectLoader<String,CacheObject> cacheObjectLoaderMock=ctrl.createMock(CacheObjectLoader.class);
        Map<String,CacheObject> cacheObjects=new HashMap<String,CacheObject>();
        cacheObjects.put(cacheObject1.getName(), cacheObject1);
        expect(cacheObjectLoaderMock.loadCacheObjects()).andReturn(cacheObjects);
        replay(cacheObjectLoaderMock);
        
        
        cacheManager.createCache(CacheObject.class, cacheObjectLoaderMock);
        Cache<String,CacheObject> cache=cacheManager.getCache(CacheObject.class);
        assertTrue(cache!=null); 
        verify(cacheObjectLoaderMock);
    }
    
    
    @SuppressWarnings("unchecked")
    public void testRemoveCache(){
        IMocksControl ctrl = createControl();
        //Mock CacheObjectLoader对象
        CacheObjectLoader<String,CacheObject> cacheObjectLoaderMock=ctrl.createMock(CacheObjectLoader.class);
        Map<String,CacheObject> cacheObjects=new HashMap<String,CacheObject>();
        cacheObjects.put(cacheObject1.getName(), cacheObject1);
        expect(cacheObjectLoaderMock.loadCacheObjects()).andReturn(cacheObjects);
        replay(cacheObjectLoaderMock);
        
        
        cacheManager.createCache(CacheObject.class, cacheObjectLoaderMock);
        Cache<String,CacheObject> cache=cacheManager.getCache(CacheObject.class);
        assertTrue(cache!=null); 
  
        
        cacheManager.removeCache(CacheObject.class);
        cache=cacheManager.getCache(CacheObject.class);
        assertTrue(cache==null); 
        
        verify(cacheObjectLoaderMock); 
    }
    

}
