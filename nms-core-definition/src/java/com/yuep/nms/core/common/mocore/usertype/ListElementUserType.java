/*
 * $Id: ListElementUserType.java, 2009-2-25 ����11:11:25 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.usertype;

/**
 * <p>
 * Title: ListElementUserType
 * </p>
 * <p>
 * Description: 
 *        ����Ԫ�ص��û�����;����List�д��ö������.
 * </p>
 * 
 * @author yangtao
 * created 2009-2-25 ����11:11:25
 * modified [who date description]
 * check [who date description]
 */
public interface ListElementUserType<T> {
    /**
     * ����ǰ����ת��Ϊ�ַ���ӳ��
     * 
     * @return
     */
    public String toString(T t, Class<T> type);

    /**
     * ����ǰ�ַ���ת��Ϊ����ʵ��
     * 
     * @param str
     * @return
     */
    public T toObject(String str, Class<T> type);

}
