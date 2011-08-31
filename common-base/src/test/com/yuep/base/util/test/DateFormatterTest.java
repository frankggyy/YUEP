/*
 * $Id: DateFormatterTest.java, 2010-11-15 上午11:36:19 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.util.test;

import java.util.Date;

import junit.framework.TestCase;

import com.yuep.base.util.format.DateFormatter;

/**
 * <p>
 * Title: DateFormatterTest
 * </p>
 * <p>
 * Description: 日期型数据类型转换测试用例
 * </p>
 * 
 * @author sufeng
 * created 2010-11-15 上午11:36:19
 * modified [who date description]
 * check [who date description]
 */
public class DateFormatterTest extends TestCase {
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    public void testSwitchLongDate(){
        Date now=new Date();
        System.out.println("date0:"+now);
        String str = DateFormatter.getLongDate(now);
        System.out.println("date1:"+str);
        Date date2=null;
        try {
            date2 = DateFormatter.getDateByString(str);
            System.out.println("date2:"+date2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        assertEquals(now.toString(),date2.toString());
    }
    
}
