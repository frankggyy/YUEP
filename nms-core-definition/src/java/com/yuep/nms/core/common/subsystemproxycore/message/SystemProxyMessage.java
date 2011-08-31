/*
 * $Id: SystemProxyMessage.java, 2011-5-24 ����12:58:14 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.subsystemproxycore.message;

import java.io.Serializable;

import com.yuep.core.message.def.BaseMessage;

/**
 * <p>
 * Title: SystemProxyMessage
 * </p>
 * <p>
 * Description:
 * ��ϵͳ����ģ����Ϣ
 * </p>
 * 
 * @author yangtao
 * created 2011-5-24 ����12:58:14
 * modified [who date description]
 * check [who date description]
 */
public class SystemProxyMessage extends BaseMessage {
   
    private static final long serialVersionUID = 7684320854840213254L;
    /** ��ϵͳ�������� */
    public static final String CREATE_SUBSYSTEMPROXY="createsubsystemproxy";
    /**��ϵͳ��������*/
    public static final String UPDATE_SUBSYSTEMPROXY="updatesubsystemproxy";
    /**��ϵͳ����ɾ��*/
    public static final String DELETE_SUBSYSTEMPEOXY="deletesubsystemproxy";
   
    public SystemProxyMessage(String messageName, Serializable messageSource) {
        super(messageName, messageSource);
    }
    
}
