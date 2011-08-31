/*
 * $Id: AbstractResponsibility.java, 2010-4-27 下午03:13:57 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.process.rsp;

import javax.swing.SwingUtilities;

/**
 * <p>
 * Title: AbstractResponsibility
 * </p>
 * <p>
 * Description:任务接口的抽象实现类
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:13:57
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractResponsibility <T> implements Responsibility<T> {

    /**
     * 下一步
     */
    private Responsibility<T> nextrsp = null;
    
    /**
     * 当前处理的结果
     */
    private T result;

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#getNext()
     */
    @Override
    public Responsibility<T> getNext() {
        return this.nextrsp;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#setNext(com.yuep.core.client.main.process.rsp.Responsibility)
     */
    @Override
    public Responsibility<T> setNext(Responsibility<T> rsp) {
        this.nextrsp = rsp;
        return rsp;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#process(java.lang.Object)
     */
    @Override
    public T process(final T t) {
        SwingUtilities.invokeLater(new Runnable(){

            @Override
            public void run() {
                boolean execute = execute(t);
                if (execute) {
                    Responsibility<T> next = getNext();
                    if (next != null)
                        result = next.process(t);
                }
            }
            
        });
        if(result != null)
            return result;
        else
            return t;
        
    }
}
