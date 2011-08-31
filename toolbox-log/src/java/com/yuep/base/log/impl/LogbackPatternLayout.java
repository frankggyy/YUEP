/*
 * $Id: LogbackPatternLayout.java, 2011-3-4 下午04:53:32 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.log.impl;

import ch.qos.logback.classic.PatternLayout;

/**
 * <p>
 * Title: com.yuep.core.logger.impl.LogbackPatternLayout
 * </p>
 * <p>
 * Description: 需要配置到logback.xml中
 * </p>
 * 
 * @author sufeng
 * created 2011-3-4 下午04:53:32
 * modified [who date description]
 * check [who date description]
 */
public class LogbackPatternLayout extends PatternLayout{
    
    // 因为对logback做了包装，导致file,line必须打印第1层的堆栈，而不是第0层的堆栈
    
    static{
        defaultConverterMap.put("L", LogbackLineOfCallerConverter.class.getName());
        defaultConverterMap.put("line", LogbackLineOfCallerConverter.class.getName());

        defaultConverterMap.put("F", LogbackFileOfCallerConverter.class.getName());
        defaultConverterMap.put("file", LogbackFileOfCallerConverter.class.getName());
    }

}
