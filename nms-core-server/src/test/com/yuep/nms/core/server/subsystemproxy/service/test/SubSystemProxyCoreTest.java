/*
 * $Id: SubSystemProxyCoreTest.java, 2011-7-26 下午05:46:11 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxy.service.test;

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import com.yuep.core.test.container.CoreContextMock;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter;
import com.yuep.nms.core.server.subsystemproxycore.SubSystemProxyCoreImpl;
import com.yuep.nms.core.server.subsystemproxycore.SubSystemProxyImpl;

/**
 * <p>
 * Title: SubSystemProxyCoreTest
 * </p>
 * <p>
 * Description:多级网管proxy的测试用例
 * </p>
 * 
 * @author yangtao
 * @created 2011-7-26 下午05:46:11
 * @modified [who date description]
 * @check [who date description]
 */
public class SubSystemProxyCoreTest extends TestCase {

    private SubSystemProxyCoreImpl subSystemProxyCore;
    private SubSystemProxyImpl subSystemProxy;
    private final MoNaming nm=new MoNaming("nm=1/");
    @Override
    public void setUp(){
        setUpSubSystemProxyCore();
        setUpSubSystemProxy();
    }
    
    private void setUpSubSystemProxyCore(){
        subSystemProxyCore=new SubSystemProxyCoreImpl();
        subSystemProxyCore.setCoreContext(new CoreContextMock());
        subSystemProxyCore.registerSubSystemRequestIntecepter("nm", new TestSubSystemRequestIntercepter());
    }
    
    private void setUpSubSystemProxy(){
        subSystemProxy=new SubSystemProxyImpl();
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(nm);
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.2");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        subSystemProxy.init(subSystemProxyProperty);
    }
    
    
    @Override
    public void tearDown(){
        
    }
    
    
    public void testCreateSubSystemProxy(){
        subSystemProxyCore.createSubSystemProxy(subSystemProxy);
        SubSystemProxy subSystemProxy=subSystemProxyCore.getSubSystemProxyBySubSystemId(nm);
        assertEquals(subSystemProxy, this.subSystemProxy);
    }
    
    
    public void testDeleteSubSystemProxy(){
        subSystemProxyCore.createSubSystemProxy(subSystemProxy);
        subSystemProxyCore.deleteSubSystemProxy(subSystemProxy.getSubSystemProxyProperty().getSubSystemId());
        SubSystemProxy subSystemProxy=subSystemProxyCore.getSubSystemProxyBySubSystemId(nm);
        assertNull(subSystemProxy);
    }
    
    
    public void testGetSubSystemProxies(){
        subSystemProxyCore.createSubSystemProxy(subSystemProxy);
        List<SubSystemProxy> subSystemProxies=subSystemProxyCore.getSubSystemProxies("nm");
        assertTrue(subSystemProxies.contains(subSystemProxy));
    }
    
    
    public void testBindSubSystemProxy(){
        subSystemProxyCore.createSubSystemProxy(subSystemProxy);
        MoNaming nm=new MoNaming("nm=1/nm=2/");
        subSystemProxyCore.bindSubSystemProxy(nm, subSystemProxy.getSubSystemProxyProperty().getSubSystemId());
        SubSystemProxy subSystemProxy=subSystemProxyCore.getBindSubSystemProxy(nm);
        assertEquals(subSystemProxy, this.subSystemProxy);
    }
    
    
    public void testUpdateSubSystemProxy(){
        subSystemProxyCore.createSubSystemProxy(subSystemProxy);
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(nm);
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.3");
        subSystemProxyProperty.setUserName("admin1");
        subSystemProxyProperty.setPassword("admin1");
        subSystemProxyProperty.setPort(888);
        subSystemProxyProperty.setSubSystemType("nm");

        subSystemProxyCore.updateSubSystemProxy(subSystemProxyProperty);
    }
    
    
    public void testRegisterSubSystemRequestIntecepter(){
        TestSubSystemRequestIntercepter testSubSystemRequestIntercepter=new TestSubSystemRequestIntercepter();
        subSystemProxyCore.registerSubSystemRequestIntecepter("nm", testSubSystemRequestIntercepter);
        SubSystemRequestIntercepter result=subSystemProxyCore.getSubSystemRequestIntecepter("nm");
        assertEquals(testSubSystemRequestIntercepter,result);
    }

    /**
     * 内部类：test子系统请求处理
     */
    private static class TestSubSystemRequestIntercepter implements SubSystemRequestIntercepter{
        @Override
        public <T> Object invoke(SubSystemProxy subSystemProxy,MoNaming managedNode, String serviceName,
                Class<T> serviceIntefaceClass, Object proxy, Method method,
                Object[] args) {
            return null;
        }
        
    }
}
