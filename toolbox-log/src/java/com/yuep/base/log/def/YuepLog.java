/*
 * $Id: YuepLog.java, 2011-8-17 ����04:04:48 sufeng Exp $
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
 * Description: ��־tool
 * </p>
 * 
 * @author sufeng
 */
public class YuepLog {

    /**
     * �Ƿ��ʼ����
     */
    private static volatile boolean inited=false;
    
    /**
     * ��ֹ���߳��ظ�init log
     */
    private static final byte[] monitor=new byte[0];
    
    /**
     * logʵ��
     */
    private static LogbackLoggerFactoryImpl factory;
    
    /**
     * ��ʼ����־
     * @param logbackConfigFileLocation logback.xml���ļ�λ��,���������·��
     * ����bootstrap/conf/logback-server.xml,Ҳ������URL·��
     * @param defaultLogName ȱʡ��־��
     */
    public static void initLogging(String logbackConfigFileLocation,String defaultLogName){
        synchronized (monitor) {
            factory=new LogbackLoggerFactoryImpl(logbackConfigFileLocation,defaultLogName);
            inited=true;
        }
    }
    
    /**
     * �õ���־
     * @param logName
     * @return logger
     */
    public static Logger getLogger(String logName){
        if(!inited)
            throw new IllegalStateException("need invoke initLogging first");
        return factory.getLogger(logName);
    }
    
    /**
     * �õ�ȱʡ��־
     * @return ���ܱ�֤��ȡ��һ����־
     */
    public static Logger getDefaultLogger(){
        if(!inited)
            throw new IllegalStateException("need invoke initLogging first");
        return factory.getDefaultLogger();
    }
    
}
