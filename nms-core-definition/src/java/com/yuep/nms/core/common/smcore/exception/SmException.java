/*
 * $Id: SmException.java, 2011-3-25 ����10:54:30 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.exception;

import com.yuep.base.exception.YuepException;

/**
 * <p>
 * Title: SmException
 * </p>
 * <p>
 * Description: ��ȫģ����쳣
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 ����10:54:30
 * modified [who date description]
 * check [who date description]
 */
public class SmException extends YuepException {

    private static final long serialVersionUID = -4471881031392814269L;
    
    /**
     * �û�������
     */
    public static final int USER_NOT_FOUND=2001;
    
    /**
     * �������
     */
    public static final int PASSWORD_WRONG=2002;
    
    /**
     * �˺ű�����
     */
    public static final int USER_DISABLE=2003;
    
    /**
     * �˺Ź���
     */
    public static final int USER_EXPIRED=2004;
    
    /**
     * �������
     */
    public static final int PASSWORD_EXPIRED=2005;
    
    /**
     * ����ip��Χ��
     */
    public static final int NOT_IN_IPRANGE=2006;
    
    /** ��role��user����,���ܱ�ɾ��*/
    public static final int USER_REFERED_ROLE=2007;
    
    /** ��user�ѵ�¼����,���ܱ�ɾ��*/
    public static final int USER_IN_SESSION=2008;
    
    /** �����߳��Լ� */
    public static final int KICK_SELF=2009;
    
    /**
     * �ڲ�����
     */
    public static final int INTERNAL_ERROR=2999;
    
    /**
     * @param errorCode
     * @param cause ���쳣
     * @param source �쳣�Ĳ�������
     */
    public SmException(int errorCode, Throwable cause, String... source) {
        super(errorCode, cause, source);
    }
    
    /**
     * 
     * @param errorCode
     * @param source �쳣�Ĳ�������
     */
    public SmException(int errorCode, String... source){
        super(errorCode, source);
    }

}
