/*
 * $Id: MoCacheTest.java, 2011-4-2 上午09:28:59 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.cache.test;

import java.util.List;

import junit.framework.TestCase;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.nms.core.common.mocore.cache.MoCache;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoCacheTest
 * </p>
 * <p>
 * Description:
 * MoCache单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-2 上午09:28:59
 * modified [who date description]
 * check [who date description]
 */
public class MoCacheTest extends TestCase{
    
    private MoCache moCache=null;
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    public void setUp(){
    	moCache=new MoCache();
        setUpMoCache();
       
    }
    
    private void setUpMoCache(){
        Mo mo=new Mo();
        mo.setMoNaming(new MoNaming("domain=1/"));
        mo.setActualtype("domain");
        mo.setDisplayName("root");
        mo.setType("domain");
        mo.setLinkstate(true);
      
        
        Mo mo1=new Mo();
        mo1.setMoNaming(new MoNaming("domain=1/ne=2/"));
        mo1.setParent(mo.getMoNaming());
        mo1.setActualtype("c8000");
        mo1.setDisplayName("192.168.11.2");
        mo1.setType("c8000");
        mo1.setLinkstate(true);
        
        moCache.addMos(YuepObjectUtils.newArrayList(mo,mo1));
        
    }
    /**
     * 
     * (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @Override
    public void tearDown(){
        moCache.clear();
    }
    
    
    public void testAddMos(){
        Mo slot=new Mo();
        slot.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot.setActualtype("lpu4");
        slot.setDisplayName("card1");
        slot.setType("lpu4");
        slot.setLinkstate(true);
        slot.setParent(slot.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot));
        Mo moInCache=moCache.getMo(slot.getMoNaming());
        
        assertTrue(slot!=moInCache);
        assertEquals(slot, moInCache);
    }
    
    public void testUpdateMos(){
        Mo slot=new Mo();
        slot.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot.setActualtype("lpu4");
        slot.setDisplayName("card1");
        slot.setType("lpu4");
        slot.setLinkstate(true);
        slot.setParent(slot.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot));
        
        slot.setDisplayName("11111");
        moCache.updateMos(YuepObjectUtils.newArrayList(slot));
        
        Mo moInCache=moCache.getMo(slot.getMoNaming());
        
        assertEquals("11111", moInCache.getDisplayName());
    }
    
    
    public void testDeleteMos(){
        Mo slot=new Mo();
        slot.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot.setActualtype("lpu4");
        slot.setDisplayName("card1");
        slot.setType("lpu4");
        slot.setLinkstate(true);
        slot.setParent(slot.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot));
        
        Mo mo=moCache.getMo(slot.getMoNaming());
        assertNotNull(mo);
        
        moCache.deleteMos(YuepObjectUtils.newArrayList(slot));
        
        mo=moCache.getMo(slot.getMoNaming());
        assertNull(mo);

    }
    
    
    public void testGetMo(){
        Mo slot=new Mo();
        slot.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot.setActualtype("lpu4");
        slot.setDisplayName("card1");
        slot.setType("lpu4");
        slot.setLinkstate(true);
        slot.setParent(slot.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot));
        
        Mo mo=moCache.getMo(slot.getMoNaming());
        assertNotNull(mo);
    }
    
    
    public void testGetChildren(){
        Mo slot1=new Mo();
        slot1.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot1.setActualtype("lpu4");
        slot1.setDisplayName("card1");
        slot1.setType("lpu4");
        slot1.setLinkstate(true);
        slot1.setParent(slot1.getMoNaming().getParent());
        
        Mo port1=new Mo();
        port1.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/port=3/"));
        port1.setActualtype("ethernet3");
        port1.setDisplayName("port3");
        port1.setType("ethernet3");
        port1.setLinkstate(true);
        port1.setParent(port1.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot1,port1));
        
        List<Mo> children=moCache.getChildren(new MoNaming("domain=1/ne=2/"));
        assertTrue(children.size()==2);
        assertTrue(children.contains(slot1));
        assertTrue(children.contains(port1));
        
    }
    
    
    public void testClear(){
        Mo slot=new Mo();
        slot.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot.setActualtype("lpu4");
        slot.setDisplayName("card1");
        slot.setType("lpu4");
        slot.setLinkstate(true);
        slot.setParent(slot.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot));
        
        moCache.clear();
        
        List<Mo> allMos=moCache.getAllMos();
        assertTrue(allMos.size()==0);
    }
    
    
    public void testGetAllMos(){
        moCache.clear();
        
        Mo slot=new Mo();
        slot.setMoNaming(new MoNaming("domain=1/ne=2/slot=2/"));
        slot.setActualtype("lpu4");
        slot.setDisplayName("card1");
        slot.setType("lpu4");
        slot.setLinkstate(true);
        slot.setParent(slot.getMoNaming().getParent());
        
        moCache.addMos(YuepObjectUtils.newArrayList(slot));
        
        List<Mo> allMos=moCache.getAllMos();
        assertTrue(allMos.size()==1);
        assertTrue(allMos.contains(slot));

    }

}
