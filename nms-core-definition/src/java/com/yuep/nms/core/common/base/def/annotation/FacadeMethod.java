/*
 * $Id: FacadeMethod.java, 2011-3-28 下午03:40:23 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Title: FacadeMethod
 * </p>
 * <p>
 * Description:提供给客户端、下级网管等子系统的service上的方法注解
 * </p>
 * 
 * @author sufeng
 * created 2011-3-28 下午03:40:23
 * modified [who date description]
 * check [who date description]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FacadeMethod {

    /**
     * @return source参数位置，第一个为0
     */
    int sourceLocation() default 0;
    
    /**
     * 是否记录日志
     * @return
     */
    boolean needLog() default false;
    
    /**
     * @return 操作日志的类别,0操作日志,1系统日志,2安全日志
     */
    LogType oplogType() default LogType.OPERATION;
    
    /**
     * 此方法是否需要管理范围过滤
     * @return true需要过滤 false不需要过滤
     */
    boolean scopeFilter() default false;
    
}
