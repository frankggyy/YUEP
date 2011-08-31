/*
 * $Id: MoDaoTest.java, 2011-4-1 下午03:15:43 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.mocore.dao.test;

import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.expect;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.IMocksControl;

import com.yuep.core.db.session.def.Session;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.transaction.def.Transaction;
import com.yuep.nms.core.common.mocore.enums.SyncState;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.server.mocore.dao.MoDao;
import com.yuep.nms.core.server.mocore.dao.impl.MoDaoImpl;

/**
 * <p>
 * Title: MoDaoTest
 * </p>
 * <p>
 * Description:
 * MoDao单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 下午03:15:43
 * modified [who date description]
 * check [who date description]
 */
public class MoDaoTest extends TestCase{
  
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
       
    }
    
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
     
    }
    
    
    
    public void testCreateMos(){
        Mo mo=getMo();
        
        List<Mo> mos=new ArrayList<Mo>();
        mos.add(mo);
        
        IMocksControl control = createControl();
        
        SessionFactory mockSessionFactory=control.createMock(SessionFactory.class);
        Session mockSession=control.createMock(Session.class);
        Transaction transaction=control.createMock(Transaction.class);
        
        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.beginTransaction()).andReturn(transaction);
        
        mockSession.save(mos);
        
        transaction.commit();
        mockSession.close();
        
        control.replay();
        
        MoDao moDao=new MoDaoImpl(mockSessionFactory);
        moDao.createMos(mos);
        
        control.verify();
    }
    

    public void testDeleteMos(){

        Mo mo=getMo();
        
        List<Mo> mos=new ArrayList<Mo>();
        mos.add(mo);
        
        IMocksControl control = createControl();
        
        SessionFactory mockSessionFactory=control.createMock(SessionFactory.class);
        Session mockSession=control.createMock(Session.class);
        Transaction transaction=control.createMock(Transaction.class);
        
        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.beginTransaction()).andReturn(transaction);
        
        mockSession.delete(mos);
        
        transaction.commit();
        mockSession.close();
        
        control.replay();
        
        MoDao moDao=new MoDaoImpl(mockSessionFactory);
        moDao.deleteMos(mos);
        
        control.verify();
    
    }
  
    
    public void testUpdateMos(){

        Mo mo=getMo();
        
        List<Mo> mos=new ArrayList<Mo>();
        mos.add(mo);
        
        IMocksControl control = createControl();
        
        SessionFactory mockSessionFactory=control.createMock(SessionFactory.class);
        Session mockSession=control.createMock(Session.class);
        Transaction mockTransaction=control.createMock(Transaction.class);
        
        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.beginTransaction()).andReturn(mockTransaction);
        
        mockSession.update(mos);
        
        mockTransaction.commit();
        mockSession.close();
        
        control.replay();
        
        MoDao moDao=new MoDaoImpl(mockSessionFactory);
        moDao.updateMos(mos);
        
        control.verify();
    
    }
    
    public void testGetAllMos(){
        Mo mo=getMo();
        List<Object> mos=new ArrayList<Object>();
        mos.add(mo);
        
        IMocksControl control = createControl();
        
        SessionFactory mockSessionFactory=control.createMock(SessionFactory.class);
        Session mockSession=control.createMock(Session.class);
        
        expect(mockSessionFactory.openSession()).andReturn(mockSession);
        expect(mockSession.query("from "+Mo.class.getSimpleName())).andReturn(mos);
        
        mockSession.close();
        
        control.replay();
        
        MoDao moDao=new MoDaoImpl(mockSessionFactory);
        List<Mo> mosInDB=moDao.getAllMos();
        assertTrue(mosInDB.contains(mo));
        
        control.verify();
        
    }
    
    private Mo getMo(){
        Mo mo=new Mo();
        mo.setMoNaming(new MoNaming("domain=1/ne=1/"));
        mo.setDisplayName("192.168.11.2");
        mo.setLinkstate(false);
        mo.setParent(mo.getMoNaming().getParent());
        mo.setSyncstate(SyncState.SYNC);
        mo.setType("C8000");
        
        return mo;
    }
    

}
