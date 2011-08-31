/*
 * $Id: YuepException.java, 2009-2-9 ����11:10:33 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Communications Industry Group Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.exception;


/**
 * <p>
 * Title: YuepException
 * </p>
 * <p>�쳣���࣬����Error Code,Source,cause,message
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����11:10:33
 * check [who date description]
 */
public class YuepException extends RuntimeException {
    
    private static final long serialVersionUID = 5893725839548086058L;
    
    /**
     * δ������ڲ�����,{0}
     */
    public final static int INTERNAL_ERROR=0;
    
    /** ������ */
    private int errorCode;
    
    /** ������Ϣ��������Է������Դ�� */
    private String[] source;

    public YuepException(int errorCode, String... source){
        super(getDefaultDetailMessage(errorCode,source)); //��Ҫ��һ��message,�����ӡ�������쳣��Ϣ���ڼ�,�����ڲ��
        this.errorCode = errorCode;
        this.source = source;
    }
    
    /**
     * ͨ��error code,source�õ�ȱʡ����ʾ��Ϣ
     * @param errorCode
     * @param source
     * @return
     */
    private static String getDefaultDetailMessage(int errorCode, String... source){
        if(source==null||source.length==0){
            return "code="+errorCode;
        }else{
            if(source.length==1){
                return "code="+errorCode+",source="+source[0];
            }else{
                StringBuilder sb=new StringBuilder();
                sb.append("code=").append(errorCode);
                for(int i=0;i<source.length;i++){
                    sb.append(",source=").append(source[i]);
                }
                return sb.toString();
            }
        }
    }
    
    public YuepException(int errorCode,Throwable th,String... source) {
        super(th);
        this.errorCode = errorCode;
        this.source = source;
    }

    public YuepException(String message) {
        super(message);
    }
    
    /**
     * ���쳣
     * @param th rootCause
     */
    public YuepException(Throwable th) {
        super(th);
    }
    
    /**
     * ������
     * @return the errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * ������Ϣ��������Է������Դ��
     * @return the source
     */
    public String[] getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String[] source) {
        this.source = source;
    }
}
