/*
 * $Id: InstanceProvider.java, 2009-2-9 ����03:32:53 aaron lee Exp $
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
 * Title: InstanceProvider
 * </p>
 * <p>
 * Description:�ýӿڶ�����һЩ�����û���ȡ�û�ѡ�е�ʵ��
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:32:53
 * modified [who date description]
 * check [who date description]
 */
public interface InstanceProvider {
    /**
     * ��ȡ�û�ѡ���������
     * 
     * @return ѡ�������
     */
    int getSelectedCount();
}
