/*
 * $Id: TestSessionService.java, 2010-11-16 ����01:49:28 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.test.session;

import junit.framework.TestCase;

import com.yuep.base.util.sys.SysUtils;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.impl.SessionServiceImpl;

/**
 * <p>
 * Title: TestSessionService
 * </p>
 * <p>
 * Description: ����session service
 * </p>
 * 
 * @author sufeng
 * created 2010-11-16 ����01:49:28
 * modified [who date description]
 * check [who date description]
 */
public class TestSessionService extends TestCase {
    
    private static String homeDir;
    
    @Override
    protected void setUp() throws Exception {
        String userDir = SysUtils.getUserDir();
        if(userDir.endsWith("common-core"))
            homeDir=userDir+"\\..\\yuep-build\\server";
        else
            homeDir=userDir+"\\yuep-build\\server";
        //YuepLoggerFactory factory=new LogbackLoggerFactoryImpl(homeDir+"\\modules\\servercore\\conf\\logback.xml");
    }
    
    private SessionServiceImpl getSessionServiceAndLogin(){
        SessionServiceImpl sessionService=new SessionServiceImpl();
        Session sess = sessionService.connect("127.0.0.1", "127.0.0.1");
        Session currentSess = sessionService.getSession();
        assertEquals(sess, currentSess);
        assertEquals(Long.valueOf(2L),currentSess.getSessionId());
        return sessionService;
    }
    
    public void testLoginAndGetSession(){
        getSessionServiceAndLogin();
    }
    
    public void testLogout(){
        SessionServiceImpl sessionService=getSessionServiceAndLogin();
        Session currentSess = sessionService.getSession();
        
        // ע��֮��Ӧ��Ϊnull
        sessionService.cleanup(currentSess.getSessionId());
        currentSess = sessionService.getSession();
        assertNull(currentSess);
    }
    
    public void testSessionListener(){
        SessionServiceImpl sessionService=new SessionServiceImpl();
        sessionService.connect("127.0.0.1", "127.0.0.1");
    }
    
}
