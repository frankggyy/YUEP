/*
 * $Id: UserCustomTest.java, 2011-5-10 上午10:43:03 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.test;

import java.net.URL;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.SimpleLogger;
import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.container.def.CoreContext;
import com.yuep.core.db.sessionfactory.configuration.SessionFactoryManagerConfiguration;
import com.yuep.core.db.sessionfactory.def.SessionFactory;
import com.yuep.core.db.sessionfactory.def.SessionFactoryManager;
import com.yuep.nms.core.common.smcore.model.UserCustom;
import com.yuep.nms.core.server.base.test.CoreContextMock;
import com.yuep.nms.core.server.base.test.ModuleContextMock;
import com.yuep.nms.core.server.smcore.SmCoreContext;
import com.yuep.nms.core.server.smcore.SmCoreModule;
import com.yuep.nms.core.server.smcore.auth.UserCustomProcessor;

/**
 * <p>
 * Title: UserCustomTest
 * </p>
 * <p>
 * Description:用户个性化测试用例
 * </p>
 * 
 * @author sufeng
 * created 2011-5-10 上午10:43:03
 * modified [who date description]
 * check [who date description]
 */
public class UserCustomTest extends TestCase {
    
    private static boolean inited=false;
    private static SessionFactory sessionFactory;
    private static UserCustomProcessor userCustomProcessor;
    
    @Override
    protected void setUp() throws Exception {
        if(inited)            
            return;
        
        try{
            Logger logger=new SimpleLogger("smcore");
            SmCoreModule smCoreModule=new SmCoreModule();
            smCoreModule.setModuleName("smcore-server");
            
            // get home dir
            URL url=this.getClass().getResource("/com/yuep/nms/core/server/smcore/test/test-yuepdb-smcore.xml");
            String file = url.getFile();
            int pos = file.indexOf("nms-core-server/");
            String path=file.substring(0, pos);
            String homeDir=path+"nms-core-server";
            
            // init module manager
            ModuleContextMock.init();
            ModuleContextMock moduleContext = (ModuleContextMock) ModuleContext.getInstance();
            moduleContext.putParam(ContainerConst.KEY_HOME_DIR, homeDir+"/../yuep-build");
            
            moduleContext.putModule(SmCoreModule.class, smCoreModule);
            moduleContext.setCoreModule(smCoreModule);
            moduleContext.setContainerLogger(logger);
            
            CoreContextMock.init();
            
            SmCoreContext.setCoreContext(CoreContext.getInstance());
            
            // sm core service init
            SmCoreContext.setLogger(logger);
            // 启动db
            SessionFactoryManagerConfiguration sessionFactoryManagerConfiguration=new SessionFactoryManagerConfiguration();
            
            sessionFactoryManagerConfiguration.configure(YuepObjectUtils.newArrayList(url.getPath()));
            
            //  设置2个session factory相关的local service
            SessionFactoryManager sessionFactoryManager=sessionFactoryManagerConfiguration.buildSessionFactoryManager();
            CoreContext.getInstance().setLocalService("sessionFactoryManager", SessionFactoryManager.class, sessionFactoryManager);
            
            sessionFactory=sessionFactoryManager.getSessionFactory("yuep");
            CoreContext.getInstance().setLocalService("sessionFactory", SessionFactory.class, sessionFactory);
            
            ApplicationContext ctx = new FileSystemXmlApplicationContext(homeDir+"/resource/smcore/conf/appctx-sm.xml");
            SmCoreContext.setCtx(ctx);
            
            userCustomProcessor=SmCoreContext.getBean("userCustomProcessor", UserCustomProcessor.class);
        }catch(Throwable th){
            System.out.println("UserCustomTest init");
        }
        inited=true;
    }
    
    public void testUserCustom(){
        try{
            // 设置
            userCustomProcessor.setUserCustom("admin", "key1", "value1");
            // 获取
            UserCustom userCustom = userCustomProcessor.getUserCustom("admin");
            assertEquals("value1", userCustom.getCustomInfo().get("key1"));
            // 删除
            userCustomProcessor.deleteUserCustomItem("admin", "key1");
            // 再获取
            userCustom = userCustomProcessor.getUserCustom("admin");
            assertEquals(true,userCustom==null || userCustom.getCustomInfo().get("key1")==null);
        }catch(Throwable th){
            System.out.println("testUserCustom finished.");
        }
    }
    
    
    
    
}
