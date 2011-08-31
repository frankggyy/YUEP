/*
 * $Id: LogbackPatternLayout.java, 2011-3-4 ����04:53:32 sufeng Exp $
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
 * Description: ��Ҫ���õ�logback.xml��
 * </p>
 * 
 * @author sufeng
 * created 2011-3-4 ����04:53:32
 * modified [who date description]
 * check [who date description]
 */
public class LogbackPatternLayout extends PatternLayout{
    
    // ��Ϊ��logback���˰�װ������file,line�����ӡ��1��Ķ�ջ�������ǵ�0��Ķ�ջ
    
    static{
        defaultConverterMap.put("L", LogbackLineOfCallerConverter.class.getName());
        defaultConverterMap.put("line", LogbackLineOfCallerConverter.class.getName());

        defaultConverterMap.put("F", LogbackFileOfCallerConverter.class.getName());
        defaultConverterMap.put("file", LogbackFileOfCallerConverter.class.getName());
    }

}
