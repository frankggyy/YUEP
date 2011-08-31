/*
 * $Id: Module4Container.java, 2010-11-5 上午10:35:10 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

import com.yuep.base.log.def.Logger;



/**
 * <p>
 * Title: Module4Container
 * </p>
 * <p>
 * Description: 容器管理模块的内部管理接口（不暴露给上层应用）
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 上午10:35:10
 * modified [who date description]
 * check [who date description]
 */
public interface Module4Container {

    /**
     * 设置模块名
     * @param name
     */
    public void setModuleName(String name);
 
    /***
     * 设置模块的状态
     * @param moduleStatus
     */
    public void setModuleStatus(String moduleStatus);
    
    /**
     * 设置类加载器
     * @param classLoader
     */
    public void setClassLoader(ClassLoader classLoader);
    
    /**
     * 设置module的日志
     * @param logger
     */
    public void setLogger(Logger logger);

    /**
     * 模块启动与初始化
     * @return
     */
    public void start();
 
    /**
     * 停止模块
     * @return
     */
    public void stop();

    
}
