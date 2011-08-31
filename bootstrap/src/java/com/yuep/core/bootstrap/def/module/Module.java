/*
 * $Id: Module.java, 2010-9-17 下午01:34:50 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

import java.util.Map;

import com.yuep.base.log.def.Logger;


/**
 * <p>
 * Title: Module
 * </p>
 * <p>
 * Description: 模块
 * </p>
 * 
 * @author aaron
 * created 2010-9-17 下午01:34:50
 * modified [who date description]
 * check [who date description]
 */
public interface Module extends ModuleConstants{

    /**
     * 获取模块名
     * @return 模块名
     */
    public String getModuleName();
    
    /**
     * 获取模块当前状态 
     * @return 模块状态
     */
    public String getModuleStatus();

    /**
     * 获取模块所有参数信息
     * @return 模块参数信息
     */
    public Map<String, String> getModuleParams();
    
    /**
     * 获取模块的Logger
     * @return 模块Logger
     */
    public Logger getLogger();
    /**
     * 获取模块运行路径
     * @return
     */
    public String getModulePath();
    
}
