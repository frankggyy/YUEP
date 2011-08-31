/*
 * $Id: BusinessResult.java, 2010-10-14 下午05:52:22 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.exception;


/**
 * <p>
 * Title: BusinessResult
 * </p>
 * <p>
 * Description: 业务处理的结果，包括异常和参数
 * </p>
 * 
 * @author sufeng
 * created 2010-10-14 下午05:52:22
 * modified [who date description]
 * check [who date description]
 */
public class BusinessResult {
    
    /**
     * 异常信息
     */
    private Throwable th;
    
    /**
     * 当前步骤执行产生的结果
     */
    private Object[] objs;
    
    public BusinessResult(Throwable th,Object ... objs){
        this.th=th;
        this.objs=objs;
    }
    
    /**
     * 异常信息
     * @return
     */
    public Throwable getTh() {
        return th;
    }
    
    /**
     * 当前步骤执行产生的结果
     * @return
     */
    public Object[] getObjs() {
        return objs;
    }

}
