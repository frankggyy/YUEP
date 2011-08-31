/*
 * $Id: MoNamingEquals.java, 2011-5-10 ÏÂÎç07:03:51 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.mock.test;

import static org.easymock.EasyMock.reportMatcher;

import org.apache.commons.lang.ObjectUtils;
import org.easymock.IArgumentMatcher;

/**
 * <p>
 * Title: MoNamingEquals
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 ÏÂÎç07:03:51
 * modified [who date description]
 * check [who date description]
 */
public class CommonArgumentEquals<T> implements IArgumentMatcher {
   
    private T in ;
    public CommonArgumentEquals(T in){
        this.in=in;
    }
    /**
     * (non-Javadoc)
     * @see org.easymock.IArgumentMatcher#appendTo(java.lang.StringBuffer)
     */
    @Override
    public void appendTo(StringBuffer stringbuffer) {
        stringbuffer.append(in);
    }
    /**
     * (non-Javadoc)
     * @see org.easymock.IArgumentMatcher#matches(java.lang.Object)
     */
    @Override
    public boolean matches(Object obj) {
        return ObjectUtils.equals(obj, in);
    }

    public  static <T>  T matcher(T in) {
        reportMatcher(new CommonArgumentEquals(in));
        return in;
    }   
}
