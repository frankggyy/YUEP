/*
 * $Id: CmdUtilsTest.java, 2010-11-15 上午11:41:48 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import junit.framework.TestCase;

import com.yuep.base.util.cmd.CmdStreamListener;
import com.yuep.base.util.cmd.CmdUtils;
import com.yuep.base.util.sys.SysUtils;

/**
 * <p>
 * Title: CmdUtilsTest
 * </p>
 * <p>
 * Description: 命令行执行工具的测试用例
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 上午11:41:48
 * modified [who date description]
 * check [who date description]
 */
public class CmdUtilsTest extends TestCase {
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testExecuteCmd(){
        ParseCmdStream parser=new ParseCmdStream();
        CmdUtils.exeCmdInCurrentThread("hahaha", parser);//会抛异常或控制台输出Error,这样执行hahaha返回失败
        boolean res=parser.getResult();
        assertEquals(false, res);
    }
    
    public void testExecuteCmdOutOfCurrentThread(){
        ParseCmdStream parser=new ParseCmdStream();
        CmdUtils.exeCmdInNewThread("dir", parser);
        SysUtils.sleepNotException(2000); //必须等待，否则test case直接结束
        boolean res=parser.getResult();
        assertEquals(true, res);
    }
    
    public void testGetLocalHostName(){
        String localHostName = CmdUtils.getLocalHostName();
        assertNotNull(localHostName);
    }
    
    public void testGetLocalIps(){
        String[] localIps = CmdUtils.getLocalIps();
        assertNotNull(localIps);
    }
    
    public void testGetLocalMacs(){
        String[] localMacs = CmdUtils.getLocalMacs();
        assertNotNull(localMacs);
    }
    
}

/**
 * <p>
 * Title: ParseCmdStream
 * </p>
 * <p>
 * Description:当发生异常或者控制台输出ERROR时，认为该命令执行失败
 * </p>
 * 
 * @author sufeng
 */
class ParseCmdStream implements CmdStreamListener {

    public boolean res=true;
    
    public boolean getResult() {
        return res;
    }
    
    @Override
    public void outException(Exception ex) {
        res=false; //发生异常，认为失败
    }

    @Override
    public void outLine(String type,String lineMessage) {
        if(type.equals("ERROR")) //控制台输出ERROR，也认为是失败
            res=false;
    }
    
}
