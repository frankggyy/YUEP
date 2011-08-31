/*
 * $Id: MoNamingTest.java, 2011-4-1 下午03:29:31 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.naming.test;

import junit.framework.TestCase;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoNamingTest
 * </p>
 * <p>
 * Description:
 * MoNaming单元测试类
 * </p>
 * 
 * @author yangtao
 * created 2011-4-1 下午03:29:31
 * modified [who date description]
 * check [who date description]
 */
public class MoNamingTest extends TestCase{
    
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
    
    
    
    public void testGetMoType(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3:c8000/slot=4/port=1/");
        assertEquals("port", moNaming.getMoType());
    }
    
    
    public void testGetInstance(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertEquals(1, moNaming.getInstance());
    }
    
    
    public void testGetAddition(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertEquals("2", moNaming.getAddition());
    }
    
    public void testGetMoNamingByMoType(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertEquals(new MoNaming("domain=1/ne=3/slot=4/"), moNaming.getMoNamingByMoType("slot"));
    }
    
    
    public void testGetParent(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertEquals(new MoNaming("domain=1/ne=3/slot=4/"), moNaming.getParent());
    }
    
    
    public void testIsChild(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertTrue(moNaming.isChild(new MoNaming("domain=1/ne=3/slot=4/")));
    }
    
    public void testIsParent(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertTrue(moNaming.isParent(new MoNaming("domain=1/ne=3/slot=4/port=1:2/pvc=1/")));
    }
    
    public void testContain(){
        MoNaming moNaming=new MoNaming("domain=1/ne=3/slot=4/port=1:2/");
        assertTrue(!moNaming.contain(new MoNaming("domain=1/ne=3/slot=4/port=1:2/pvc=1/")));
        assertTrue(new MoNaming("domain=1/ne=3/slot=4/port=1:2/pvc=1/").contain(moNaming));
    }
    
   

}
