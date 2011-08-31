/*
 * $Id: ScopeFilterTest.java, 2011-5-10 上午10:55:35 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.SimpleLogger;
import com.yuep.base.util.cmd.CmdUtils;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.facade.def.FacadeResponse;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;
import com.yuep.core.session.def.SessionState;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingFactory;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.server.base.test.CoreContextMock;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.mgmtscope.MgmtScopeFilter;

/**
 * <p>
 * Title: ScopeFilterTest
 * </p>
 * <p>
 * Description:管理过滤测试用例
 * </p>
 * 
 * @author sufeng
 * created 2011-5-10 上午10:55:35
 * modified [who date description]
 * check [who date description]
 */
public class ScopeFilterTest extends TestCase {
    
    public final static MgmtScopeFilter mgmtScopeFilter=new MgmtScopeFilter();
    private static boolean inited=false;
    private static MySessionService sessionService=new MySessionService();
    
    @Override
    protected void setUp() throws Exception {
        if(inited)            
            return;
        
        try{
            Logger logger=new SimpleLogger("smcore");
            CoreContextMock.init();
            SmCoreContext.setCoreContext(CoreContext.getInstance());
            CoreContext.getInstance().setLocalService("sessionService", SessionService.class, sessionService);
            SmCoreContext.setLogger(logger);
            
            // mock session 
            String[] ips=CmdUtils.getLocalIps();
            Session session=new Session(1L);
            session.setOwner("admin");
            session.setIp(ips[0]);
            session.setServerIp(ips[0]);
            session.setSessionState(SessionState.Linkup);
            sessionService.addSession(session);
            CoreContext.getInstance().setLocalService("sessionService", SessionService.class, sessionService);  
    
            mgmtScopeFilter.init();
        }catch(Throwable th){
            System.out.println("ScopeFilterTest inited");
        }
        inited=true;
    }

    public void testUserScopeFilter(){
        try{
            // mock data
            List<Mo> mos=new ArrayList<Mo>();
            Mo mo1=new Mo();
            mo1.setMoNaming(MoNamingFactory.createMoNaming(null, "nm", 1));
            mos.add(mo1);
            
            // mock user scope
            Set<MoNaming> moNamings=new HashSet<MoNaming>();
            moNamings.add(MoNamingFactory.createMoNaming(null, "nm", 1)); //mo能管理1个mo
            mgmtScopeFilter.setUserScope("admin", moNamings);
            
            FacadeResponse lastResponse=new FacadeResponse(mos);
            FacadeResponse afterInvoke = mgmtScopeFilter.afterInvoke(lastResponse, MoCore.class, "getAllMos", new Class<?>[]{}, new Object[]{});
            assertEquals(afterInvoke, lastResponse);
    
            mgmtScopeFilter.setUserScope("admin", new HashSet<MoNaming>()); //admin不能管理任意mo
            afterInvoke = mgmtScopeFilter.afterInvoke(lastResponse, MoCore.class, "getAllMos", new Class<?>[]{}, new Object[]{});
            List<?> list=(List<?>)afterInvoke.getReturnValue();
            assertEquals(0, list.size());
        }catch(Throwable th){
            System.out.println("testUserScopeFilter inited");
        }
    }

}
