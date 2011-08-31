/*
 * $Id: ExitServiceImpl.java, 2010-10-11 ����02:24:57 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.servercore.impl;

import com.yuep.core.bootstrap.impl.ModuleContextImpl;
import com.yuep.core.servercore.def.ExitService;

/**
 * <p>
 * Title: ExitServiceImpl
 * </p>
 * <p>
 * Description:�˳���ϵͳ
 * </p>
 * 
 * @author sufeng
 * created 2010-10-11 ����02:24:57
 * modified [who date description]
 * check [who date description]
 */
public class ExitServiceImpl extends Thread implements ExitService {

    public ExitServiceImpl(){
        // ����ctrl+c�Ķ�����ע��һ��hook
        Runtime.getRuntime().addShutdownHook(this);   
    }
    
    @Override
    public void exit() {
        // ֪ͨ���߳��˳��� close����module
        ModuleContextImpl.notifyExit();
    }

    @Override
    public void run() {
        // ctrl+cʱ�ᴥ��exit
        exit();
    }
    
}
