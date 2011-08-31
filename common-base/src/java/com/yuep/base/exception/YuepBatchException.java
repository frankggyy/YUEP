/*
 * $Id: YuepBatchException.java, 2009-11-24 ����07:21:47 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Communications Industry Group Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Title: YuepBatchException
 * </p>
 * <p>
 * Description: �����������쳣�࣬������ܰ����˶���쳣
 * </p>
 * 
 * @author yangtao
 * created 2009-11-24 ����07:21:47
 * modified [who date description]
 * check [who date description]
 */
public class YuepBatchException extends RuntimeException {
    
    private static final long serialVersionUID = 474485532847499330L;

    /**
     * �쳣�Ķ���Դ
     */
    private List<Object> keys=new ArrayList<Object>(); // ͨ��list����˳����
    
    /**
     * �쳣�б������Դһһ��Ӧ
     */
    private Map<Object, Throwable> throwables=new HashMap<Object, Throwable>();

    /**
     * �������쳣�б��м�һ���쳣
     * @param key ��
     * @param th  �쳣
     */
    public void addException(Object key,Throwable th){
        keys.add(key);
        throwables.put(key, th);
    }
    
    /**
     * �õ������쳣�еļ�
     * @return
     */
    public List<Object> getKeys() {
        return keys;
    }

    /**
     * �õ�ĳ���쳣
     * @param key
     * @return
     */
    public Throwable getThrowable(Object key){
        return throwables.get(key);
    }
    
    /**
     * �õ������쳣
     * @return
     */
    public Map<Object, Throwable> getThrowables() {
        // ����һ�ݣ���ֹ�������ʵ��µ�����
        Map<Object, Throwable> result=new HashMap<Object, Throwable>();
        result.putAll(throwables);
        return result;
    }
    
}
