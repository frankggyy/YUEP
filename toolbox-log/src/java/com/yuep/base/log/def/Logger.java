/*
 * $Id: Logger.java, 2011-3-4 ����02:10:30 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.log.def;


/**
 * <p>
 * Title: Logger
 * </p>
 * <p>
 * Description: ��־
 * </p>
 * 
 * @author sufeng
 * created 2011-3-4 ����02:10:30
 * modified [who date description]
 * check [who date description]
 */
public interface Logger {

    /**
     * �������
     * @param s
     */
    public void debug(String s);

    public void debug(String s, Object obj);

    public void debug(String s, Object[] aobj);

    public void debug(String s, Throwable throwable);

    public void debug(String s, Object obj, Object obj1);

    public void error(String s);

    public void error(String s, Object obj);

    public void error(String s, Object[] aobj);

    public void error(String s, Throwable throwable);

    public void error(String s, Object obj, Object obj1);

    public String getName();
    
    public void info(String s);

    public void info(String s, Object obj);

    public void info(String s, Object[] aobj);

    public void info(String s, Throwable throwable);

    public void info(String s, Object obj, Object obj1);

    public boolean isDebugEnabled();

    public boolean isErrorEnabled();

    public boolean isInfoEnabled();

    public boolean isTraceEnabled();

    public boolean isWarnEnabled();

    public void trace(String s);

    public void trace(String s, Object obj);

    public void trace(String s, Object[] aobj);

    public void trace(String s, Throwable throwable);

    public void trace(String s, Object obj, Object obj1);

    public void warn(String s);

    public void warn(String s, Object obj);

    public void warn(String s, Object[] aobj);

    public void warn(String s, Throwable throwable);

    public void warn(String s, Object obj, Object obj1);

}
