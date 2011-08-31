/*
 * $Id: ProcessorShutdownHook.java, 2011-2-25 上午11:20:59 sufeng Exp $
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
 * Description: 注册shutdown时的回调处理thread
 * </p>
 * 
 * @author sufeng
 * created 2011-2-25 上午11:20:59
 * modified [who date description]
 * check [who date description]
 */
public class ProcessorShutdownHook extends Thread{

    public ProcessorShutdownHook(){
        // shutdown前进行拦截处理
        Runtime.getRuntime().addShutdownHook(this);   
    }
    
    @Override
    public void run() {
        ModuleContextImpl.notifyExit();
        // 该thread执行结束后,会留有1小段时间,这样就必须把clean module在当前线程中处理,而不是main thread.
    }
    
}
