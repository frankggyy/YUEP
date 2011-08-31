/*
 * $Id: FacadeMethod.java, 2011-3-28 ����03:40:23 sufeng Exp $
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
 * Description:�ṩ���ͻ��ˡ��¼����ܵ���ϵͳ��service�ϵķ���ע��
 * </p>
 * 
 * @author sufeng
 * created 2011-3-28 ����03:40:23
 * modified [who date description]
 * check [who date description]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FacadeMethod {

    /**
     * @return source����λ�ã���һ��Ϊ0
     */
    int sourceLocation() default 0;
    
    /**
     * �Ƿ��¼��־
     * @return
     */
    boolean needLog() default false;
    
    /**
     * @return ������־�����,0������־,1ϵͳ��־,2��ȫ��־
     */
    LogType oplogType() default LogType.OPERATION;
    
    /**
     * �˷����Ƿ���Ҫ����Χ����
     * @return true��Ҫ���� false����Ҫ����
     */
    boolean scopeFilter() default false;
    
}
