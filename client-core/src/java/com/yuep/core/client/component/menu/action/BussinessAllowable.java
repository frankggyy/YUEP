/*
 * $Id: BussinessAllowable.java, 2009-2-9 ����03:30:53 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

/**
 * <p>
 * Title: BussinessAllowable
 * </p>
 * <p>
 * Description:�ͻ���ҵ�����ж�ʹ��״̬
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:30:53
 * modified [who date description]
 * check [who date description]
 */
public interface BussinessAllowable {

    /**
     * ��instanceProvider�ṩ�����ݽ���ʹ��״̬���ж�
     * 
     * @param instanceProvider
     *            ʵ���ṩ��
     * @return ʹ��״̬��<code>true</code>��ʾ���ã�<code>false</code>��ʾ�����á�
     */
    boolean bussinessAllowabled(InstanceProvider instanceProvider);

    /**
     * ��selectedObjects�ṩ�����ݽ���ʹ��״̬���ж�
     * 
     * @param selectedObjects
     *            ���ݶ����б�
     * @return ʹ��״̬��<code>true</code>��ʾ���ã�<code>false</code>��ʾ�����á�
     */
    boolean bussinessAllowabled(Object[] selectedObjects);
}
