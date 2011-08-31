/*
 * $Id: LogType.java, 2011-4-15 ����02:15:35 sufeng Exp $
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
 * Description: ��־���
 * </p>
 * 
 * @author sufeng
 * created 2011-4-15 ����02:15:35
 * modified [who date description]
 * check [who date description]
 */
public enum LogType implements EnumValue {
    
    /**������־*/
    OPERATION(0),
    /**ϵͳ��־*/ 
    SYSTEM(1),
    /**��ȫ��־*/ 
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
