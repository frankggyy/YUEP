/*
 * $Id: MoManagerTest.java, 2011-5-10 下午02:40:02 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.service.test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;

import java.net.URL;

import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.nms.core.common.mocore.enums.SyncState;
import com.yuep.nms.core.common.mocore.model.ManagedNodeProperty;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManagerImpl;
import com.yuep.nms.core.common.momanager.exception.MoManagerException;
import com.yuep.nms.core.server.momanager.service.MoManagerImpl;

/**
 * <p>
 * Title: MoManagerTest
 * </p>
 * <p>
 * Description:
 * MoManagerImpl单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 下午02:40:02
 * modified [who date description]
 * check [who date description]
 */
public class MoManagerTest extends TestCase {
    
    private  MoManagerImpl moManager;
    private  MoCore moCoreMock;
    private  MoStaticInfoManagerImpl moStaticInfoManagerMock;
    private  ObjectIDGenerateService objectIDGenerateServiceMock;
    private IMocksControl control;
 
    private Mo rootMo;
    private Mo newDoaminMo;
    private Mo newNeMo;
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
        moManager=new MoManagerImpl();
        
        control = createControl();
        moCoreMock=control.createMock(MoCore.class);
        moManager.setMoCore(moCoreMock);
        
        URL url=this.getClass().getResource("mo_static_info.xml");
        moStaticInfoManagerMock=new MoStaticInfoManagerImpl(url.getPath());
        moStaticInfoManagerMock.init();
        moManager.setMoStaticInfoManager(moStaticInfoManagerMock);
        
        objectIDGenerateServiceMock=control.createMock(ObjectIDGenerateService.class);
        moManager.setObjectIDGenerateService(objectIDGenerateServiceMock);
        
        
        MoNaming root=new MoNaming("nm=1/");
        
        rootMo=new Mo();
        rootMo.setActualtype("nm");
        rootMo.setDisplayName("root");
        rootMo.setLinkstate(false);
        rootMo.setMoNaming(root);
        rootMo.setParent(null);
        rootMo.setSyncstate(SyncState.NOTSYNC);
        rootMo.setType("nm");
        
       
        String domainName="bean";
        String domainType="domain";
        
        newDoaminMo=new Mo();
        newDoaminMo.setActualtype(domainType);
        newDoaminMo.setDisplayName(domainName);
        newDoaminMo.setLinkstate(false);
        newDoaminMo.setParent(rootMo.getMoNaming());
        newDoaminMo.setType(domainType);
        newDoaminMo.setMoNaming(MoNamingFactory.createMoNaming(rootMo.getMoNaming(), domainType, 1));
        
        String neType="C8000";
        newNeMo=new Mo();
        newNeMo.setActualtype(neType);
        newNeMo.setLinkstate(false);
        newNeMo.setParent(rootMo.getMoNaming());
        newNeMo.setType(neType);
        newNeMo.setMoNaming(MoNamingFactory.createMoNaming(rootMo.getMoNaming(), "ne", 1));
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
    
    
    public void testCreateManagedDomain(){
        expect(moCoreMock.getMo(rootMo.getMoNaming())).andReturn(rootMo);
        expect(objectIDGenerateServiceMock.generateObjectId(newDoaminMo.getType())).andReturn(Long.valueOf(1));
       
        moCoreMock.createMos(MoListEquals.equals(YuepObjectUtils.newArrayList(newDoaminMo)));
        control.replay();
        
        Mo mo=moManager.createManagedDomain(rootMo.getMoNaming(), newDoaminMo.getDisplayName(), newDoaminMo.getType());
        assertTrue(mo.equals(newDoaminMo));
    }
    
    public void testCreateManagedDomainWithNotStaticConfig(){
        newDoaminMo.setType("aaaaa");
        control.replay();
        try{
            moManager.createManagedDomain(rootMo.getMoNaming(), newDoaminMo.getDisplayName(), newDoaminMo.getType());
            fail();
        }catch(Exception ex){
           MoManagerException moManagerException=(MoManagerException)ex;
           assertTrue(moManagerException.getErrorCode()==MoManagerException.STATIC_CONFIG_NOTFOUND);
        }
        
    }
    
    
    public void testCreateManagedDomainWithParentNotAcceptChild(){
        newDoaminMo.setType("LPU");
        newDoaminMo.setDisplayName("LPU");
        expect(moCoreMock.getMo(rootMo.getMoNaming())).andReturn(rootMo);
        control.replay();
        try{
            moManager.createManagedDomain(rootMo.getMoNaming(), newDoaminMo.getDisplayName(), newDoaminMo.getType());
            fail();
        }catch(Exception ex){
           MoManagerException moManagerException=(MoManagerException)ex;
           assertTrue(moManagerException.getErrorCode()==MoManagerException.PARENT_NOTACCEPT_CHILD);
        }
    }
    
    
    
    public void testCreateManagedNode(){
        ManagedNodeProperty managedNodeProperty=new ManagedNodeProperty();
        managedNodeProperty.setManagedNode(newNeMo.getMoNaming());
        managedNodeProperty.setIp("192.168.11.7");
        managedNodeProperty.setPort(6666);
        
        expect(moCoreMock.getMo(rootMo.getMoNaming())).andReturn(rootMo);
        expect(objectIDGenerateServiceMock.generateObjectId(newNeMo.getMoNaming().getMoType())).andReturn(Long.valueOf(newNeMo.getMoNaming().getInstance()));
        moCoreMock.createMos(MoListEquals.equals(YuepObjectUtils.newArrayList(newNeMo)));
        moCoreMock.updateManagedNodeProperty(managedNodeProperty);
        control.replay();
        
        Mo mo=moManager.createManagedNode(rootMo.getMoNaming(), managedNodeProperty, newNeMo.getType());
        assertEquals(newNeMo, mo);
    }
    
    
    
    
    

}
