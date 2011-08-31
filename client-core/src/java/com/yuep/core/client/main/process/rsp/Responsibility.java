/*
 * $Id: Responsibility.java, 2010-4-27 下午03:07:36 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.process.rsp;

/**
 * <p>
 * Title: Responsibility
 * </p>
 * <p>
 * Description:任务接口，主要是将做不同事的功能区分开，为以后的扩展性考虑，可参考start-process.xml
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:08:52
 * modified [who date description]
 * check [who date description]
 */
public interface Responsibility<T> {
    /**
     * 执行功能（任务）
     * 
     * @param t
     *            T 参数对象
     * @return 返回执行是否成功
     */
    public boolean execute(T t);

    /**
     * 任务执行
     * 
     * @param t
     * @return null,说明执行链异常中断; not null代表执行链正常退出，可能是从中间某一环或最后一环正常结束
     */
    public T process(T t);

    /**
     * 返回当前任务的下一个任务
     * 
     * @return Responsibility 任务
     */
    public Responsibility<T> getNext();

    /**
     * 设置下一个任务
     * 
     * @param rsp
     *            Responsibility 任务
     * @return 约定：内部实现返回的Responsibility必须与输入的参数的引用相同! 此返回值只为方便组合编码
     */
    public Responsibility<T> setNext(Responsibility<T> rsp);
}
