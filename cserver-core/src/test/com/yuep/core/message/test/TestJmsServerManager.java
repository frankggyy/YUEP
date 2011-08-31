/*
 * $Id: TestJmsServerManager.java, 2010-11-5 下午03:31:46 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.message.test;

import junit.framework.TestCase;

import com.yuep.base.util.cmd.CmdUtils;
import com.yuep.base.util.sys.SysUtils;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.container.def.ContainerPropertiesManager;
import com.yuep.core.container.impl.CoreContextImpl;
import com.yuep.core.jmsserver.def.JmsServerManager;
import com.yuep.core.jmsserver.impl.JmsServerModule;
import com.yuep.core.jmsserver.impl.joram.JoramManagerImpl;
import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: TestJmsServerManager
 * </p>
 * <p>
 * Description: jms消息服务器启动，停止测试用例(目前规定的发布根路径在yuep-build下)
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 下午03:31:46
 * modified [who date description]
 * check [who date description]
 */
public class TestJmsServerManager extends TestCase{

    /**
     * 发布环境工程名
     * 如果与不符,请修改该变量的值
     */
    private String deployProjectName="yuep-build";
    
    private static String homeDir;
    
    @Override
    protected void setUp() throws Exception {
        String userDir = SysUtils.getUserDir();
        if(userDir.endsWith("common-core"))
            homeDir=userDir+"\\..\\"+deployProjectName;
        else
            homeDir=userDir+"\\"+deployProjectName;
    }
    
    public void testStartJoram(){
        JmsServerManager mgr=new JoramManagerImpl();
        
        // mock module
        ModuleContextMock.init();
        ModuleContextMock moduleContext = (ModuleContextMock) ModuleContext.getInstance();
        JmsServerModule module=new JmsServerModule();
        module.setModuleName("jmsserver");
        moduleContext.putModule(module.getClass(), module);
        
        CoreContextImpl.init();
        ModuleContext.getInstance().getSystemParams().put(ContainerPropertiesManager.KEY_HOME_DIR, homeDir);

        MessageMetadata messageMetadata=new MessageMetadata();
        messageMetadata.setPort(16010);
        messageMetadata.setNamingPort(16400);
        
        mgr.start(messageMetadata);
        SysUtils.sleepNotException(3000);
        Boolean[] res=CmdUtils.checkPorts("TCP",new String[]{"16010"} );
        System.out.println(res[0].booleanValue());
    }
    
    public void testStopJoram(){
        try{
            JmsServerManager mgr=new JoramManagerImpl();
            MessageMetadata messageMetadata=new MessageMetadata();
            messageMetadata.setPort(16010);
            messageMetadata.setNamingPort(16400);
            mgr.shutdown(messageMetadata);
            SysUtils.sleepNotException(3000);
            Boolean[] res2=CmdUtils.checkPorts("TCP",new String[]{"16010"} );
            assertEquals(false, res2[0].booleanValue());
        }catch(Exception ex){
            System.out.println("stop joram:"+ex.getClass().getSimpleName());
        }
    }
    
}
