/*
 * $Id: MoStaticInfoManagerTest.java, 2011-4-2 ÉÏÎç09:20:07 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service.test;

import java.net.URL;

import junit.framework.TestCase;

import com.yuep.nms.core.common.mocore.model.MoStaticInfo;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManagerImpl;

/**
 * <p>
 * Title: MoStaticInfoManagerTest
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2011-4-2 ÉÏÎç09:20:07
 * modified [who date description]
 * check [who date description]
 */
public class MoStaticInfoManagerTest extends TestCase {
    
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
    
    
    public void testGetMoStaticInfo(){
        URL url=this.getClass().getResource("mo_static_info.xml");
        MoStaticInfoManagerImpl moStaticInfoManager=new MoStaticInfoManagerImpl(url.getPath());
        moStaticInfoManager.init();
        MoStaticInfo moStaticInfo=moStaticInfoManager.getMoStaticInfo("nm");
        assertNotNull(moStaticInfo);
    }
    

}
