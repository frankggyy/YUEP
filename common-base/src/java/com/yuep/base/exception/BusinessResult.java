/*
 * $Id: BusinessResult.java, 2010-10-14 ����05:52:22 sufeng Exp $
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
 * Description: ҵ����Ľ���������쳣�Ͳ���
 * </p>
 * 
 * @author sufeng
 * created 2010-10-14 ����05:52:22
 * modified [who date description]
 * check [who date description]
 */
public class BusinessResult {
    
    /**
     * �쳣��Ϣ
     */
    private Throwable th;
    
    /**
     * ��ǰ����ִ�в����Ľ��
     */
    private Object[] objs;
    
    public BusinessResult(Throwable th,Object ... objs){
        this.th=th;
        this.objs=objs;
    }
    
    /**
     * �쳣��Ϣ
     * @return
     */
    public Throwable getTh() {
        return th;
    }
    
    /**
     * ��ǰ����ִ�в����Ľ��
     * @return
     */
    public Object[] getObjs() {
        return objs;
    }

}
