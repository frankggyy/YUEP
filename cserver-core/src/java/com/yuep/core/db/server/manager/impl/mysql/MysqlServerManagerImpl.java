/*
 * $Id: MysqlServerManagerImpl.java, 2011-3-24 上午11:40:24 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.server.manager.impl.mysql;

import java.io.File;
import java.util.Map;

import com.yuep.base.util.cmd.impl.RunCmdService;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.db.module.DbModule;
import com.yuep.core.db.server.manager.def.DbServerManager;

/**
 * <p>
 * Title: MysqlServerManagerImpl
 * </p>
 * <p>
 * Description:
 * Mysql服务器管理实现
 * </p>
 * 
 * @author yangtao
 * created 2011-3-24 上午11:40:24
 * modified [who date description]
 * check [who date description]
 */
public class MysqlServerManagerImpl implements DbServerManager {
    
    @Override
    public void close() {
      
        String cmdPath="mysql/shutdown-mysql.bat";
        String cmdFullPath=getCmdFullPath(cmdPath);
       
        RunCmdService service=new RunCmdService();
        service.setStartupMode(RunCmdService.OUTSIDE_THREAD);
        service.setStartCmdLine(cmdFullPath);
        service.startService();
    }

    @Override
    public void start(Map<String, String> dbParams) {
        String cmdPath="mysql/run-mysql.bat";
        String cmdFullPath=getCmdFullPath(cmdPath);
        
        RunCmdService service=new RunCmdService();
        service.setStartupMode(RunCmdService.OUTSIDE_THREAD);
        service.setStartCmdLine(cmdFullPath);
        service.startService();
    }
    
    /**
     * 拼接全路径cmd
     * @param cmdPath
     * @return
     */
    private String getCmdFullPath(String cmdPath){
    	  String homeDir=ModuleContext.getInstance().getSystemParam(ContainerConst.KEY_HOME_DIR);
          String db=ModuleContext.getInstance().getModule(DbModule.class).getModuleName(); 
          String fullCmdPath=homeDir+File.separator+"modules"+File.separator+db+File.separator+cmdPath; 
          return fullCmdPath;
    }
    
}
