/*
 * $Id: SmException.java, 2011-3-25 上午10:54:30 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.exception;

import com.yuep.base.exception.YuepException;

/**
 * <p>
 * Title: SmException
 * </p>
 * <p>
 * Description: 安全模块的异常
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 上午10:54:30
 * modified [who date description]
 * check [who date description]
 */
public class SmException extends YuepException {

    private static final long serialVersionUID = -4471881031392814269L;
    
    /**
     * 用户不存在
     */
    public static final int USER_NOT_FOUND=2001;
    
    /**
     * 密码错误
     */
    public static final int PASSWORD_WRONG=2002;
    
    /**
     * 账号被禁用
     */
    public static final int USER_DISABLE=2003;
    
    /**
     * 账号过期
     */
    public static final int USER_EXPIRED=2004;
    
    /**
     * 密码过期
     */
    public static final int PASSWORD_EXPIRED=2005;
    
    /**
     * 不在ip范围内
     */
    public static final int NOT_IN_IPRANGE=2006;
    
    /** 该role被user引用,不能被删除*/
    public static final int USER_REFERED_ROLE=2007;
    
    /** 有user已登录进来,不能被删除*/
    public static final int USER_IN_SESSION=2008;
    
    /** 不能踢出自己 */
    public static final int KICK_SELF=2009;
    
    /**
     * 内部错误
     */
    public static final int INTERNAL_ERROR=2999;
    
    /**
     * @param errorCode
     * @param cause 根异常
     * @param source 异常的参数数组
     */
    public SmException(int errorCode, Throwable cause, String... source) {
        super(errorCode, cause, source);
    }
    
    /**
     * 
     * @param errorCode
     * @param source 异常的参数数组
     */
    public SmException(int errorCode, String... source){
        super(errorCode, source);
    }

}
