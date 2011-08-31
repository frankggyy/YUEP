/*
 * $Id: SensitiveAction.java, 2009-2-9 ����03:42:39 aaron lee Exp $
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
 * Title: SensitiveAction
 * </p>
 * <p>
 * Description:���û�Tableѡ���¼����е�һ���ӿڡ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:42:39
 * modified [who date description]
 * check [who date description]
 */
public interface SensitiveAction {

    /**
     * �ýӿڶ�����selectionChanged ���������û�ѡ��������仯ʱ������ͨ���÷���֪ͨ��Action��
     * 
     * @param instanceProvider
     *            �û�ѡ�����ʵ���ṩ��
     */
    void selectionChanged(InstanceProvider instanceProvider);
}
