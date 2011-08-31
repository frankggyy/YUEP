/*
 * $Id: ProcessorShutdownHook.java, 2011-2-25 ����11:20:59 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.impl;

/**
 * <p>
 * Title: ProcessorShutdownHook
 * </p>
 * <p>
 * Description: ע��shutdownʱ�Ļص�����thread
 * </p>
 * 
 * @author sufeng
 * created 2011-2-25 ����11:20:59
 * modified [who date description]
 * check [who date description]
 */
public class ProcessorShutdownHook extends Thread{

    public ProcessorShutdownHook(){
        // shutdownǰ�������ش���
        Runtime.getRuntime().addShutdownHook(this);   
    }
    
    @Override
    public void run() {
        ModuleContextImpl.notifyExit();
        // ��threadִ�н�����,������1С��ʱ��,�����ͱ����clean module�ڵ�ǰ�߳��д���,������main thread.
    }
    
}