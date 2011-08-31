/*
 * $Id: YuepLog.java, 2011-8-17 下午04:04:48 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.log.def;

import com.yuep.base.log.impl.LogbackLoggerFactoryImpl;

/**
 * <p>
 * Title: YuepLog
 * </p>
 * <p>
 * Description: 日志tool
 * </p>
 * 
 * @author sufeng
 */
public class YuepLog {

    /**
     * 是否初始化过
     */
    private static volatile boolean inited=false;
    
    /**
     * 防止多线程重复init log
     */
    private static final byte[] monitor=new byte[0];
    
    /**
     * log实现
     */
    private static LogbackLoggerFactoryImpl factory;
    
    /**
     * 初始化日志
     * @param logbackConfigFileLocation logback.xml的文件位置,可以是相对路径
     * 比如bootstrap/conf/logback-server.xml,也可以是URL路径
     * @param defaultLogName 缺省日志名
     */
    public static void initLogging(String logbackConfigFileLocation,String defaultLogName){
        synchronized (monitor) {
            factory=new LogbackLoggerFactoryImpl(logbackConfigFileLocation,defaultLogName);
            inited=true;
        }
    }
    
    /**
     * 得到日志
     * @param logName
     * @return logger
     */
    public static Logger getLogger(String logName){
        if(!inited)
            throw new IllegalStateException("need invoke initLogging first");
        return factory.getLogger(logName);
    }
    
    /**
     * 拿到缺省日志
     * @return 总能保证获取到一个日志
     */
    public static Logger getDefaultLogger(){
        if(!inited)
            throw new IllegalStateException("need invoke initLogging first");
        return factory.getDefaultLogger();
    }
    
}
