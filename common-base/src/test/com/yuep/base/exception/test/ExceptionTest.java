/*
 * $Id: ExceptionTest.java, 2010-11-5 下午02:55:32 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.exception.test;

import java.util.Map;

import junit.framework.TestCase;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepBatchException;

/**
 * <p>
 * Title: ExceptionTest
 * </p>
 * <p>
 * Description: 普通异常的测试用例
 * <br>错误码，参数数组以及国际化的无法测试，需要结合client sample进行
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 下午02:55:32
 * modified [who date description]
 * check [who date description]
 */
public class ExceptionTest extends TestCase {

    private void doIt() throws Exception{
        throw new Exception("haha");
    }
    
    public void testGetCommonExceptionInfo(){
        try{
            doIt();
        }catch(Exception ex){
            String info = ExceptionUtils.getCommonExceptionInfo(ex);
            int pos=info.indexOf("haha");
            assertTrue(pos!=-1);
        }
    }
    
    public void testGetExFirstStackInfo(){
        try{
            doIt();
        }catch(Exception ex){
            String info = ExceptionUtils.getExFirstStackInfo(ex);
            // 堆栈会有java文件的行号
            //int pos=info.indexOf(".java");
            //assertTrue(pos!=-1);
            System.out.println(info);
        }
    }
    
    public void testGetRawException(){
        try{
            doIt();
        }catch(Exception ex){
            Throwable rawThrowable = ExceptionUtils.getRawThrowable(ex);
            assertEquals(Exception.class, rawThrowable.getClass());
        }
    }
    
    public void testYuepBatchException(){
        YuepBatchException batch=new YuepBatchException();
        batch.addException("ne=1", new Exception("hello"));
        batch.addException("ne=2", new Exception("world"));
        Map<Object, Throwable> map = batch.getThrowables();
        assertEquals(2, map.size());
        
        Throwable th = batch.getThrowable("ne=2");
        assertEquals("world", th.getMessage());
    }

}
