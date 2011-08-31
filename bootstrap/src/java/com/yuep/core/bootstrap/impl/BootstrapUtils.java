/*
 * $Id: BootstrapUtils.java, 2011-7-21 ����10:04:27 sufeng Exp $
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
 * Description: bootstrap����ģ��Ĺ�����
 * </p>
 * 
 * @author sufeng
 */
public class BootstrapUtils {

    /**
     * ��־
     */
    private static Logger logger;
    
    /**
     * ȱʡlogger
     */
    private static Logger simpleLogger=new SimpleLogger("simple");
    
    /**
     * ��ȡlogger
     * @return
     */
    public synchronized static Logger getLogger(){
        return logger==null ? simpleLogger : logger;
    }
    
    /**
     * ����bootstrapʹ�õ�logger
     * @param logger
     */
    public static void setLogger(Logger logger){
        BootstrapUtils.logger=logger;
    }
    
}
