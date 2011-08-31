/*
 * $Id: SbiManagerTest.java, 2011-5-10 下午06:05:53 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.sbimanager.service.test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;
import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.base.mock.test.CommonArgumentEquals;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.db.id.def.ObjectIDGenerateService;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.sbimanager.exception.SbiManagerException;
import com.yuep.nms.core.common.sbimanager.model.SbiBindProperty;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.subsystemproxycore.SubSystemProxyProperty;
import com.yuep.nms.core.server.sbimanager.dao.SbiBindPropertyDao;
import com.yuep.nms.core.server.sbimanager.dao.SbiPropertyDao;
import com.yuep.nms.core.server.sbimanager.service.SbiManagerImpl;
import com.yuep.nms.core.server.subsystemproxymanager.service.SubSystemProxyManager;

/**
 * <p>
 * Title: SbiManagerTest
 * </p>
 * <p>
 * Description:
 * SbiManagerImpl单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 下午06:05:53
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerTest extends TestCase {

    private IMocksControl control;
    private SbiManagerImpl sbiManager;
    private SbiPropertyDao sbiPropertyDaoMock;
    private SbiBindPropertyDao sbiBindPropertyDaoMock;
    private SubSystemProxyManager subSystemProxyServiceMock;
    private ObjectIDGenerateService objectIDGenerateServiceMock;
    
    
    @Override
    public void setUp(){
        sbiManager=new SbiManagerImpl();
        
        control = createControl();
        sbiPropertyDaoMock=control.createMock(SbiPropertyDao.class);
        sbiManager.setSbiPropertyDao(sbiPropertyDaoMock);
        
        sbiBindPropertyDaoMock=control.createMock(SbiBindPropertyDao.class);
        sbiManager.setSbiBindPropertyDao(sbiBindPropertyDaoMock);
        
        subSystemProxyServiceMock=control.createMock(SubSystemProxyManager.class);
        sbiManager.setSubSystemProxyService(subSystemProxyServiceMock);
        
        objectIDGenerateServiceMock=control.createMock(ObjectIDGenerateService.class);
        sbiManager.setObjectIDGenerateService(objectIDGenerateServiceMock);
        
    }
    
    @Override
    public void tearDown(){
        control.verify();
    }
    
    
    public void testCreateSbi(){
        MoNaming parent=new MoNaming("nm=1/nm=3/");
        
        SbiProperty sbiProperty=new SbiProperty();
        sbiProperty.setSbiName("192.168.11.11");
        sbiProperty.setIp("192.168.11.11");
        sbiProperty.setPort(8000);
        sbiProperty.setSbiNaming(MoNamingFactory.createMoNaming(parent, "sbi", 1));
        
        expect(sbiPropertyDaoMock.getSbiProperty(sbiProperty.getSbiName())).andReturn(null);
        expect(objectIDGenerateServiceMock.generateObjectId("sbi")).andReturn(Long.valueOf(1));
        sbiPropertyDaoMock.createSbiProperty(sbiProperty);
        subSystemProxyServiceMock.createSubSystemProxy(CommonArgumentEquals.matcher(toSubSystemProperty(sbiProperty)));
        
        control.replay();
        
        sbiManager.createSbi(parent, sbiProperty);
        
        
    }
    
    
    public void testSetSbiProperty(){
        MoNaming parent=new MoNaming("nm=1/nm=3/");
        SbiProperty sbiProperty=new SbiProperty();
        sbiProperty.setSbiNaming(MoNamingFactory.createMoNaming(parent, "sbi", 1));
        sbiProperty.setSbiName("192.168.11.11");
        sbiProperty.setIp("192.168.11.11");
        sbiProperty.setPort(8000);
        
        sbiPropertyDaoMock.updateSbiProperty(sbiProperty);
        subSystemProxyServiceMock.updateSubSystemProxy(CommonArgumentEquals.matcher(toSubSystemProperty(sbiProperty)));
       
        control.replay();
        
        sbiManager.setSbiProperty(sbiProperty);
        
        control.verify();
        
    }
    
    
    public void testDeleteSbiWithBindNe(){
        MoNaming parent=new MoNaming("nm=1/");
        
        SbiProperty sbiProperty=new SbiProperty();
        sbiProperty.setSbiNaming(MoNamingFactory.createMoNaming(parent, "sbi", 1));
        sbiProperty.setSbiName("192.168.11.11");
        sbiProperty.setIp("192.168.11.11");
        sbiProperty.setPort(8000);
        
        SbiBindProperty sbiBindProperty=new SbiBindProperty();
        sbiBindProperty.setNe(MoNamingFactory.createMoNaming(parent, "ne", 1));
        sbiBindProperty.setSbiNaming(sbiProperty.getSbiNaming());
        
        expect(sbiPropertyDaoMock.getSbiProperty(sbiProperty.getSbiNaming())).andReturn(sbiProperty);
        expect(sbiBindPropertyDaoMock.getSbiBindProperties(sbiProperty.getSbiNaming())).andReturn(YuepObjectUtils.newArrayList(sbiBindProperty));
       
        control.replay();
        
        try{
            sbiManager.deleteSbi(sbiProperty.getSbiNaming());
            fail();
        }catch(Exception ex){
            SbiManagerException sbiManagerExcepion=(SbiManagerException)ex;
            assertEquals(SbiManagerException.SBI_BIND_NE, sbiManagerExcepion.getErrorCode());
        }
    }
    
    public void testDeleteSbi(){
        MoNaming parent=new MoNaming("nm=1/");
        SbiProperty sbiProperty=new SbiProperty();
        sbiProperty.setSbiNaming(MoNamingFactory.createMoNaming(parent, "sbi", 1));
        sbiProperty.setSbiName("192.168.11.11");
        sbiProperty.setIp("192.168.11.11");
        sbiProperty.setPort(8000);
        
        SbiBindProperty sbiBindProperty=new SbiBindProperty();
        sbiBindProperty.setNe(MoNamingFactory.createMoNaming(parent, "ne", 1));
        sbiBindProperty.setSbiNaming(sbiProperty.getSbiNaming());
        
        expect(sbiPropertyDaoMock.getSbiProperty(sbiProperty.getSbiNaming())).andReturn(sbiProperty);
        expect(sbiBindPropertyDaoMock.getSbiBindProperties(sbiProperty.getSbiNaming())).andReturn(null);
        subSystemProxyServiceMock.deleteSubSystemProxy(sbiProperty.getSbiNaming());
        sbiPropertyDaoMock.deleteSbiProperty(sbiProperty.getSbiNaming());
        
        control.replay();
        
        sbiManager.deleteSbi(sbiProperty.getSbiNaming());
    }
    
    
    public void testSetNeToSbi(){
        MoNaming parent=new MoNaming("nm=1/");
        MoNaming sbiNaming=MoNamingFactory.createMoNaming(parent, "sbi", 1);
        MoNaming ne1=MoNamingFactory.createMoNaming(parent, "ne", 1);
        SbiBindProperty sbiBindProperty1=new SbiBindProperty();
        sbiBindProperty1.setNe(ne1);
        sbiBindProperty1.setSbiNaming(sbiNaming);
       
        MoNaming ne2=MoNamingFactory.createMoNaming(parent, "ne", 2);
        SbiBindProperty sbiBindProperty2=new SbiBindProperty();
        sbiBindProperty2.setNe(ne2);
        sbiBindProperty2.setSbiNaming(sbiNaming);
        
        //没有绑定的时候,创建
        expect(sbiBindPropertyDaoMock.getSbiBindProperty(ne1)).andReturn(null);
        sbiBindPropertyDaoMock.createSbiBindProperty(CommonArgumentEquals.matcher(sbiBindProperty1));
        subSystemProxyServiceMock.setSubSystemProxy(sbiNaming, ne1);
        
        //绑定,更新
        expect(sbiBindPropertyDaoMock.getSbiBindProperty(ne2)).andReturn(sbiBindProperty2);
        sbiBindPropertyDaoMock.setSbiBindProperty(sbiBindProperty2);
        subSystemProxyServiceMock.setSubSystemProxy(sbiNaming, ne2);
        
        control.replay();
        
        sbiManager.setNeToSbi(sbiNaming, ne1);
        sbiManager.setNeToSbi(sbiNaming, ne2);
    }
    
    
    private SubSystemProxyProperty toSubSystemProperty(SbiProperty sbiProperty){
        SubSystemProxyProperty subSystemProperty=new SubSystemProxyProperty();
        subSystemProperty.setIp(sbiProperty.getIp());
        subSystemProperty.setPassword(sbiProperty.getPassword());
        subSystemProperty.setPort(sbiProperty.getPort());
        subSystemProperty.setUserName(sbiProperty.getUserName());
        return subSystemProperty;
    }
}
