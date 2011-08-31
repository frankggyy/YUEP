/*
 * $Id: BusinessErrorTest.java, 2010-11-5 下午02:55:09 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.exception.test;

import java.util.List;

import junit.framework.TestCase;

import com.yuep.base.exception.BusinessErrorException;
import com.yuep.base.exception.BusinessResult;
import com.yuep.base.exception.ExceptionUtils;

/**
 * <p>
 * Title: BusinessErrorTest
 * </p>
 * <p>
 * Description: 业务错误测试
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 下午02:55:09
 * modified [who date description]
 * check [who date description]
 */
public class BusinessErrorTest extends TestCase {
    
    @Override
    protected void setUp() throws Exception {
        System.out.println("-----------BusinessError test case执行开始-----------");
    }
    
    private void throwBizErr() throws BusinessErrorException{
        BusinessErrorException bizErr=new BusinessErrorException();
        
        // step 1 , 正常处理
        Exception tempEx=null;
        try{
            System.out.println("step 1");
        }catch(Exception ex){
            tempEx=ex;
        }
        BusinessResult res1=new BusinessResult(tempEx,"step 1");
        bizErr.addResult(res1);
       
        // step 2 , 有异常
        tempEx=null;
        try{
            System.out.println("step 2");
            throw new IllegalArgumentException();
        }catch(Exception ex){
            tempEx=ex;
        }
        BusinessResult res2=new BusinessResult(tempEx,"step 2");
        bizErr.addResult(res2);
        
        // step 3 , 正常处理
        tempEx=null;
        try{
            System.out.println("step 3");
        }catch(Exception ex){
            tempEx=ex;
        }
        BusinessResult res3=new BusinessResult(tempEx,"step 3");
        bizErr.addResult(res3);
        
        // 所有步骤执行结束后，抛异常
        throw bizErr;
    }
    
    private boolean isSuccessed(BusinessErrorException e){
        List<BusinessResult> resultStack = e.getResultStack();
        for(BusinessResult bizRes : resultStack){
            if(bizRes.getTh()!=null)
                return false;
        }
        return true;
    }
    
    public void testBusinessError(){
        try {
            throwBizErr();
        } catch (BusinessErrorException e) {
            List<BusinessResult> resList = e.getResultStack();
            if(isSuccessed(e)){
                // 执行成功，没有任何异常
                System.out.println("执行成功");
                int i=1;
                for(BusinessResult res : resList){
                    System.out.print(i+"的执行结果:");
                    for(int j=0; j<res.getObjs().length; j++){
                        System.out.print(res.getObjs()[j]+",");
                    }
                    System.out.print("\n");
                    i++;
                }
            }else{
                // 执行有异常
                System.out.println("执行过程中有异常发生");
                int i=1;
                for(BusinessResult res : resList){
                    System.out.print(i+"的执行结果:");
                    for(int j=0; j<res.getObjs().length; j++){
                        System.out.print(res.getObjs()[j]+",");
                    }
                    if(res.getTh()!=null){
                        System.out.println(ExceptionUtils.getCommonExceptionInfo(res.getTh()));
                    }
                    System.out.print("\n");
                    i++;
                }
            }
            
            assertEquals(3, resList.size());
            assertEquals(null, resList.get(0).getTh());
            assertEquals(IllegalArgumentException.class, resList.get(1).getTh().getClass());
            assertEquals(null, resList.get(2).getTh());
        }
    }
    
    @Override
    protected void tearDown() throws Exception {
        System.out.println("-----------BusinessError test case执行结束-----------");
    }
    
}
