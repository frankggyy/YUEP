/*
 * $Id: MoListEquals.java, 2011-5-10 ÏÂÎç04:31:00 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.momanager.service.test;

import static org.easymock.EasyMock.reportMatcher;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.easymock.IArgumentMatcher;

import com.yuep.nms.core.common.mocore.model.Mo;

/**
 * <p>
 * Title: MoListEquals
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 ÏÂÎç04:31:00
 * modified [who date description]
 * check [who date description]
 */
public class MoListEquals implements IArgumentMatcher {
    private List<Mo> in;
    public  MoListEquals(List<Mo> in){
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
    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(Object obj) {
        if(!(obj instanceof List))
            return false;
        List<Mo> mos=(List<Mo>)obj;
        return CollectionUtils.isEqualCollection(in, mos);
    }

    
    public static  List<Mo> equals(List<Mo> in) {
        reportMatcher(new MoListEquals(in));
        return in;
    }   
}
