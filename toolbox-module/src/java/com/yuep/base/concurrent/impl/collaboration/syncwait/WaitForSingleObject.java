/*
 * $Id: WaitForSingleObject.java, 2009-11-30 下午02:15:16 jimsu Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.impl.collaboration.syncwait;

import com.yuep.base.concurrent.def.collaboration.syncwait.ResultHandler;
import com.yuep.base.concurrent.def.collaboration.syncwait.ResultStauts;
import com.yuep.base.exception.ExceptionUtils;

/**
 * <p>
 * Title: WaitForSingleObject
 * </p>
 * <p>
 * Description: 等待某个结果，类似于IPC中的WaitForSingleObject机制
 * </p>
 * 
 * @author sufeng
 * created 2009-11-30 下午02:15:16
 * modified [who date description]
 * check [who date description]
 */
public class WaitForSingleObject {
    
    /**
     * 执行的间隔：毫秒
     */
    private long interval=3000;
    
    private ResultHandler handler;
    
    public WaitForSingleObject(ResultHandler handler){
        this.handler=handler;
    }
    public void setInterval(long interval) {
        this.interval = interval;
    }
    
    /**
     * 等待返回结果
     * @param timeout 超时时间,毫秒数 0表示等待到结果返回,永不timeout
     * @return
     */
    public ResultStauts get(long timeout){
        long startTime=System.currentTimeMillis();
        for(;;){
            long now = System.currentTimeMillis();
            if (timeout > 0) {
                long waitTime = now - startTime;
                if (waitTime >= timeout) // 超时了
                    return new ResultStauts(true,true);
            }
            try {
                ResultStauts status = handler.getSingleResult();
                if (status.isFinished())
                    return status;
            } catch (Exception ex) {
                System.out.println(ExceptionUtils.getCommonExceptionInfo(ex));
            }
            
            try {
                Thread.sleep(interval);
            } catch (Exception x) {
                System.out.println(ExceptionUtils.getCommonExceptionInfo(x));
            }
        }
    }

}
