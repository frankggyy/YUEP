/*
 * $Id: LogbackLineOfCallerConverter.java, 2011-3-4 下午04:47:55 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.log.impl;

import ch.qos.logback.classic.pattern.LineOfCallerConverter;
import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.LoggingEvent;

/**
 * <p>
 * Title: LogbackLineOfCallerConverter
 * </p>
 * <p>
 * Description:logback对调用者进行了处理,从第1层堆栈中获取line
 * </p>
 * 
 * @author sufeng
 * created 2011-3-4 下午04:47:55
 * modified [who date description]
 * check [who date description]
 */
public class LogbackLineOfCallerConverter extends LineOfCallerConverter{
    
    @Override
    public String convert(LoggingEvent le) {
        CallerData[] callerData = le.getCallerData();
        if (callerData != null && callerData.length>0) {
            if(callerData.length > 1)
                return Integer.toString(callerData[1].getLineNumber());//因为做了包装,需要取堆栈[1]的信息
            else
                return Integer.toString(callerData[0].getLineNumber());
        } else {
            return CallerData.NA;
        }
    }
    
}
