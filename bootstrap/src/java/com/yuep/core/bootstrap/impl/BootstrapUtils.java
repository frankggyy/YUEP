/*
 * $Id: BootstrapUtils.java, 2011-7-21 上午10:04:27 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.impl;

import com.yuep.base.log.def.Logger;
import com.yuep.base.log.def.SimpleLogger;

/**
 * <p>
 * Title: BootstrapUtils
 * </p>
 * <p>
 * Description: bootstrap启动模块的工具类
 * </p>
 * 
 * @author sufeng
 */
public class BootstrapUtils {

    /**
     * 日志
     */
    private static Logger logger;
    
    /**
     * 缺省logger
     */
    private static Logger simpleLogger=new SimpleLogger("simple");
    
    /**
     * 获取logger
     * @return
     */
    public synchronized static Logger getLogger(){
        return logger==null ? simpleLogger : logger;
    }
    
    /**
     * 设置bootstrap使用的logger
     * @param logger
     */
    public static void setLogger(Logger logger){
        BootstrapUtils.logger=logger;
    }
    
}
