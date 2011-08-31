/*
 * $Id: YuepLogTest.java, 2011-8-17 下午04:34:17 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.log.test;

import java.net.URL;

import junit.framework.TestCase;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.YuepLog;


/**
 * <p>
 * Title: YuepLogTest
 * </p>
 * <p>
 * Description: yuep log test case
 * </p>
 * 
 * @author sufeng
 */
public class YuepLogTest extends TestCase{

    private static boolean inited=false;
    
    @Override
    protected void setUp() throws Exception {
        if(!inited){
            URL url=this.getClass().getResource("logback.xml");
            YuepLog.initLogging(url.toString(), "sm");
            inited=true;
        }
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testGetLogger(){
        // 输出到sm.log中
        Logger logger = YuepLog.getLogger("sm");
        logger.error("test sm error");
        logger.debug("test sm debug");
    }
    
    public void testGetLoggerNotExisted(){
        // 除了stdout,不会输出到任何文件中
        Logger logger = YuepLog.getLogger("test");
        logger.error("test test error");
        logger.debug("test test debug");
    }
    
    public void testGetDefaultLogger(){
        // 输出到sm.log中
        Logger logger = YuepLog.getDefaultLogger();
        logger.error("test default error");
        logger.debug("test default debug");
    }
    
}
