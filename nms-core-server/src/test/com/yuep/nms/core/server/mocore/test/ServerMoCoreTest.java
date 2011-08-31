/*
 * $Id: ServerMoCoreTestCase.java, 2011-4-1 下午04:09:36 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.test.container.CoreContextMock;
import com.yuep.nms.core.common.base.def.ValueCompareObject;
import com.yuep.nms.core.common.mocore.enums.SyncState;
import com.yuep.nms.core.common.mocore.exception.MoException;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.server.mocore.dao.ManagedNodePropertyDao;
import com.yuep.nms.core.server.mocore.dao.MoDao;
import com.yuep.nms.core.server.mocore.service.ServerMoCore;

/**
 * <p>
 * Title: ServerMoCoreTestCase
 * </p>
 * <p>
 * Description:
 * ServerMoCore单元测试
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 下午04:09:36
 * modified [who date description]
 * check [who date description]
 */
public class ServerMoCoreTest extends TestCase {
    
    private ServerMoCore serverMoCore;
    private IMocksControl control;
    private MoDao moDaoMock;
    private CoreContext coreContextMock;
    private ManagedNodePropertyDao managedNodePropertyDaoMock;
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
        serverMoCore=new ServerMoCore();
        
        control = createControl();
        moDaoMock=control.createMock(MoDao.class);
        serverMoCore.setMoDao(moDaoMock);
        
        coreContextMock=new CoreContextMock();
        serverMoCore.setCoreContext(coreContextMock);
        
        managedNodePropertyDaoMock=control.createMock(ManagedNodePropertyDao.class);
        serverMoCore.setManagedNodePropertyDao(managedNodePropertyDaoMock);
        
        
    }
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
        control.verify();
    }
    
    
    public void testCreateMos(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        control.replay();
        
        final AtomicInteger messageCount=new AtomicInteger(0);
        coreContextMock.local().subscribe(MoMessage.MO_CREATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,
                    Serializable msg) {
                MoMessage moMessage=(MoMessage)msg;
                assertTrue(mos.contains(moMessage.getMessageBody()));
                messageCount.incrementAndGet();
            }
        });
        
        serverMoCore.createMos(mos);
        List<Mo> allMos=serverMoCore.getAllMos();
        assertTrue(allMos.size()==mos.size());
        assertTrue(allMos.containsAll(mos));
        assertTrue(messageCount.get()==mos.size());
        
    }
    
    
    public void testDeleteMos(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        moDaoMock.deleteMos(mos);
        for(Mo mo:mos){
            managedNodePropertyDaoMock.deleteManagerNodeProperty(mo.getMoNaming());
        }
        control.replay();
        
        serverMoCore.createMos(mos);
        
        final AtomicInteger messageCount=new AtomicInteger(0);
        coreContextMock.local().subscribe(MoMessage.MO_DELETE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,
                    Serializable msg) {
                MoMessage moMessage=(MoMessage)msg;
                assertTrue(mos.contains(moMessage.getMessageBody()));
                messageCount.incrementAndGet();
            }
        });
        serverMoCore.deleteMos(mos);
        List<Mo> allMos=serverMoCore.getAllMos();
        assertTrue(allMos.size()==0);
        assertTrue(messageCount.get()==mos.size());
    }
    
    public void testGetAllMos(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        control.replay();
        
        serverMoCore.createMos(mos);
        List<Mo> allMos=serverMoCore.getAllMos();
        assertTrue(allMos.size()==mos.size());
        assertTrue(allMos.containsAll(mos));
    }
    
    
    public void testGetChildrenMos(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        control.replay();
        
        serverMoCore.createMos(mos);
        
        List<Mo> children=serverMoCore.getChildrenMos(new MoFilter(){
            @Override
            public boolean accept(Mo mo) {
                return true;
            }
            }, new MoNaming("nm=1/"));
         assertTrue(children.size()==1);
    }
    
    
    public void testGetManagedNodeProperty(){
        MoNaming nm=new MoNaming("nm=1/");
        ManagedNodeProperty managedNodeProperty=new ManagedNodeProperty();
        managedNodeProperty.setIp("192.168.11.3");
        managedNodeProperty.setPort(1111);
        managedNodeProperty.setManagedNode(nm);
        
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(nm)).andReturn(managedNodeProperty);
        
        control.replay();
        
        ManagedNodeProperty returnManagedNodeProperty=serverMoCore.getManagedNodeProperty(nm);
        assertTrue(returnManagedNodeProperty==managedNodeProperty);
        
    }
    
    
    public void testGetMo(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        control.replay();
        
        serverMoCore.createMos(mos);
        
        for(Mo mo:mos){
           Mo mo1=serverMoCore.getMo(mo.getMoNaming());
           assertEquals(mo1, mo);
        }
        
    }
    
    public void testGetMosByMoFilter(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        control.replay();
        
        serverMoCore.createMos(mos);
        
        List<Mo> results=serverMoCore.getMos(new MoFilter(){
            @Override
            public boolean accept(Mo mo) {
                return mo.getDisplayName().equals("192.168.11.1");
            }
        });
        
        assertTrue(results.size()==1);
        
        
    }
    
    public void testCreateManagedNodeProperty(){
        MoNaming nm=new MoNaming("nm=1/");
        final ManagedNodeProperty managedNodeProperty=new ManagedNodeProperty();
        managedNodeProperty.setIp("192.168.11.3");
        managedNodeProperty.setPort(1111);
        managedNodeProperty.setManagedNode(nm);
        
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(managedNodeProperty.getIp())).andReturn(null);
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(nm)).andReturn(null);
        managedNodePropertyDaoMock.createManagerNodeProperty(managedNodeProperty);
        control.replay();
        
        final AtomicInteger messageCount=new AtomicInteger(0);
        coreContextMock.local().subscribe(MoMessage.MANAGEDNODEPROPERTY_UPDATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,Serializable msg) {
                MoMessage moMessage=(MoMessage)msg;
                assertTrue(managedNodeProperty==moMessage.getMessageBody());
                messageCount.incrementAndGet();
            }
        });
        
        serverMoCore.updateManagedNodeProperty(managedNodeProperty);
        assertTrue(messageCount.get()==1);
        
    }
    
    
    public void testUpdateManagedNodeProperty(){
        MoNaming nm=new MoNaming("nm=1/");
        final ManagedNodeProperty newManagedNodeProperty=new ManagedNodeProperty();
        newManagedNodeProperty.setIp("192.168.11.3");
        newManagedNodeProperty.setPort(1111);
        newManagedNodeProperty.setManagedNode(nm);
        
        final ManagedNodeProperty oldManagedNodeProperty=new ManagedNodeProperty();
        oldManagedNodeProperty.setIp("192.168.11.3");
        oldManagedNodeProperty.setPort(1234);
        oldManagedNodeProperty.setManagedNode(nm);
        
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(newManagedNodeProperty.getIp())).andReturn(oldManagedNodeProperty);
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(nm)).andReturn(oldManagedNodeProperty);
        managedNodePropertyDaoMock.updateManagedNodeProperty(newManagedNodeProperty);
        control.replay();
        
        final AtomicInteger messageCount=new AtomicInteger(0);
        coreContextMock.local().subscribe(MoMessage.MANAGEDNODEPROPERTY_UPDATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,Serializable msg) {
                MoMessage moMessage=(MoMessage)msg;
                assertTrue(newManagedNodeProperty==moMessage.getMessageBody());
                messageCount.incrementAndGet();
            }
        });
        
        serverMoCore.updateManagedNodeProperty(newManagedNodeProperty);
        assertTrue(messageCount.get()==1);
    }
    
    
    public void testUpdateManagedNodePropertyWithSameIp(){
        MoNaming nm1=new MoNaming("nm=1/");
        final ManagedNodeProperty newManagedNodeProperty1=new ManagedNodeProperty();
        newManagedNodeProperty1.setIp("192.168.11.4");
        newManagedNodeProperty1.setPort(1111);
        newManagedNodeProperty1.setManagedNode(nm1);
        
        MoNaming nm2=new MoNaming("nm=2/");
        final ManagedNodeProperty newManagedNodeProperty2=new ManagedNodeProperty();
        newManagedNodeProperty2.setIp("192.168.11.4");
        newManagedNodeProperty2.setPort(1111);
        newManagedNodeProperty2.setManagedNode(nm2);
        
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(newManagedNodeProperty1.getIp())).andReturn(null);
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(nm1)).andReturn(null);
        managedNodePropertyDaoMock.createManagerNodeProperty(newManagedNodeProperty1);
        
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(newManagedNodeProperty2.getIp())).andReturn(newManagedNodeProperty1);
        expect(managedNodePropertyDaoMock.getManagedNodeProperty(nm2)).andReturn(null);
        control.replay();
        
        serverMoCore.updateManagedNodeProperty(newManagedNodeProperty1);
        try{
            serverMoCore.updateManagedNodeProperty(newManagedNodeProperty2);
            fail();
        }catch(Exception ex){
            MoException moException=(MoException)ex;
            assertTrue(moException.getErrorCode()==MoException.IP_EXIST);
        }
    }
    
    public void testUpdateMos(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);

        final List<Mo> updatedMos= newMos();
        for(Mo updatedMo:updatedMos){
            updatedMo.setLinkstate(false);
            updatedMo.setSyncstate(SyncState.SYNC);
            updatedMo.setType("nms");
        }
        
        moDaoMock.updateMos(updatedMos);
        

        control.replay();
        
        final AtomicInteger attrMessageCount=new AtomicInteger(0);
        coreContextMock.local().subscribe(MoMessage.MO_ATTR_CHANGED, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,Serializable msg) {
                MoMessage moMessage=(MoMessage)msg;
                Map<String,Serializable> additions=moMessage.getAdditions();
                ValueCompareObject valueCompareObject=(ValueCompareObject)additions.get("type");
                assertEquals("nms", valueCompareObject.getNewValue());
                attrMessageCount.incrementAndGet();
            }
        });
        
        final AtomicInteger stateMessageCount=new AtomicInteger(0);
        coreContextMock.local().subscribe(MoMessage.MO_STATE_CHANGED, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name,Serializable msg) {
                MoMessage moMessage=(MoMessage)msg;
                Map<String,Serializable> additions=moMessage.getAdditions();
                ValueCompareObject valueCompareObject=(ValueCompareObject)additions.get("syncstate");
                assertEquals(SyncState.SYNC, valueCompareObject.getNewValue());
                valueCompareObject=(ValueCompareObject)additions.get("linkstate");
                assertEquals(false, valueCompareObject.getNewValue());
                stateMessageCount.incrementAndGet();
            }
            
        });
        
        
        serverMoCore.createMos(mos);
        serverMoCore.updateMos(updatedMos);
        assertTrue(attrMessageCount.get()==mos.size());
        assertTrue(stateMessageCount.get()==mos.size());
        
    }
   
    
    public void testGetRootMo(){
        final List<Mo> mos= newMos();
        moDaoMock.createMos(mos);
        control.replay();
        
        serverMoCore.createMos(mos);
        
        Mo rootMo=serverMoCore.getRootMo();
        assertEquals(new MoNaming("nm=1/"), rootMo.getMoNaming());
    }
    
    private List<Mo> newMos(){
        List<Mo> mos=new ArrayList<Mo>();
        Mo mo1=new Mo();
        mos.add(mo1);
        mo1.setActualtype("nm");
        mo1.setType("nm");
        mo1.setDisplayName("192.168.11.1");
        mo1.setLinkstate(true);
        mo1.setSyncstate(SyncState.NOTSYNC);
        mo1.setMoNaming(new MoNaming("nm=1/"));
        
        Mo mo2=new Mo();
        mos.add(mo2);
        mo2.setActualtype("nm");
        mo2.setType("nm");
        mo2.setDisplayName("192.168.11.2");
        mo2.setLinkstate(true);
        mo2.setSyncstate(SyncState.NOTSYNC);
        mo2.setParent(mo1.getMoNaming());
        mo2.setMoNaming(new MoNaming("nm=1/nm=3/"));
        
        return mos;
    }

}
