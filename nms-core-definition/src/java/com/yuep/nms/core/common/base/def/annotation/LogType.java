/*
 * $Id: LogType.java, 2011-4-15 下午02:15:35 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def.annotation;

import com.yuep.core.lang.EnumValue;

/**
 * <p>
 * Title: LogType
 * </p>
 * <p>
 * Description: 日志类别
 * </p>
 * 
 * @author sufeng
 * created 2011-4-15 下午02:15:35
 * modified [who date description]
 * check [who date description]
 */
public enum LogType implements EnumValue {
    
    /**操作日志*/
    OPERATION(0),
    /**系统日志*/ 
    SYSTEM(1),
    /**安全日志*/ 
    SECURITY(2);
    
    int code;
    
    LogType(int code){
        this.code=code;
    }
    
    @Override
    public int getValue() {
        return code;
    }
}
