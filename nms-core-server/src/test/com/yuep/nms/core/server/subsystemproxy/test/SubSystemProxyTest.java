/*
 * $Id: SubSystemProxyTest.java, 2011-7-26 下午03:12:25 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.subsystemproxy.test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;

import java.io.Serializable;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.RemoteCommunicateObject;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.test.container.CoreContextMock;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.momanager.service.MoManager;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxy;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyCore;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemRequestIntercepter;
import com.yuep.nms.core.server.subsystemproxycore.SubSystemProxyImpl;

/**
 * <p>
 * Title: SubSystemProxyTest
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * @created 2011-7-26 下午03:12:25
 * @modified [who date description]
 * @check [who date description]
 */
public class SubSystemProxyTest extends TestCase {
    private SubSystemProxyImpl subSystemProxy;
    private CoreContextMock coreContextMock=new CoreContextMock();
    private IMocksControl control;
    @Override
    public void setUp(){
        subSystemProxy=new SubSystemProxyImpl();
        subSystemProxy.setCoreContext(coreContextMock);
        
       
    }
    
    @Override
    public void tearDown(){
        
    }
    
    
    public void testInitWithLocalCommunication(){
        control = createControl();
        
        SubSystemProxyCore subSystemProxyCore=control.createMock(SubSystemProxyCore.class);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(new MoNaming("nm=1/"));
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("localhost");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        
        control.replay();
        
        subSystemProxy.init(subSystemProxyProperty);
        
        control.verify();
        
        CommunicateObject local=subSystemProxy.getCommunicateObject();
        assertEquals(local, coreContextMock.local());
        
    }
    
    
    public void testInitWithRemoteCommunication(){
        control = createControl();
        
        SubSystemProxyCore subSystemProxyCore=control.createMock(SubSystemProxyCore.class);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(new MoNaming("nm=1/"));
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.2");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        
        control.replay();
        
        subSystemProxy.init(subSystemProxyProperty);
        
        control.verify();
        
        RemoteCommunicateObject remote=(RemoteCommunicateObject)subSystemProxy.getCommunicateObject();
        assertEquals(remote.getRemoteIp(), subSystemProxyProperty.getIp());
        assertEquals(remote.getRemoteServerPort(), subSystemProxyProperty.getPort()); 
    }
    
    public void testDestory(){

        control = createControl();
        
        SubSystemProxyCore subSystemProxyCore=control.createMock(SubSystemProxyCore.class);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(new MoNaming("nm=1/"));
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.2");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        
        control.replay();
        
        subSystemProxy.init(subSystemProxyProperty);
        
        control.verify();
        
        subSystemProxy.destory();
        
        CommunicateObject communicateObject=subSystemProxy.getCommunicateObject();
        assertEquals(null, communicateObject);
    }
    
    
    public void testGetService(){

        control = createControl();
        
        SubSystemProxyCore subSystemProxyCore=control.createMock(SubSystemProxyCore.class);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        
        expect(subSystemProxyCore.getSubSystemRequestIntecepter("nm")).andReturn(new TestSubSystemRequestIntercepter());
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(new MoNaming("nm=1/"));
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.2");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        
        control.replay();
        
        subSystemProxy.init(subSystemProxyProperty);
        MoManager moManager=subSystemProxy.getService(new MoNaming("nm=1/"), "moManager", MoManager.class);
        assertNotNull(moManager);
        control.verify();
    }
    
    
    public void testSubscribe(){

        control = createControl();
        
        SubSystemProxyCore subSystemProxyCore=control.createMock(SubSystemProxyCore.class);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(new MoNaming("nm=1/"));
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.2");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        
        control.replay();
        
        subSystemProxy.init(subSystemProxyProperty);
        subSystemProxy.subscribe("MO_CREATE", new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,Serializable msg) {
            }
        });
        control.verify();
    }
    
    
    public void testUnSubscribe(){


        control = createControl();
        
        SubSystemProxyCore subSystemProxyCore=control.createMock(SubSystemProxyCore.class);
        subSystemProxy.setSubSystemProxyCore(subSystemProxyCore);
        
        SubSystemProxyProperty subSystemProxyProperty=new SubSystemProxyProperty();
        subSystemProxyProperty.setSubSystemId(new MoNaming("nm=1/"));
        subSystemProxyProperty.setSubSystemName("nm");
        subSystemProxyProperty.setIp("192.168.11.2");
        subSystemProxyProperty.setUserName("admin");
        subSystemProxyProperty.setPassword("admin");
        subSystemProxyProperty.setPort(8888);
        subSystemProxyProperty.setSubSystemType("nm");
        
        control.replay();
        
        subSystemProxy.init(subSystemProxyProperty);
        MessageReceiver messageReceiver=new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,
                    Serializable msg) {
            }
        };
        subSystemProxy.subscribe("MO_CREATE", messageReceiver);
        subSystemProxy.unsubscribe("MO_CREATE", messageReceiver);
        control.verify();
    }

    /**
     * 子系统请求处理test class
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
