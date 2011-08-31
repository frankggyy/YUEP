/*
 * $Id: MessageServant.java, 2010-9-27 ����05:56:37 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.message.impl;

/**
 * <p>
 * Title: MessageServant
 * </p>
 * <p>
 * Description: ����Զ����Ϣ�Ĺ���ӿ�(������ƽ̨��ʹ��,�ϲ����ܲ��ܵ��ø���)
 * </p>
 * 
 * @author aaron
 * created 2010-9-27 ����05:56:37
 * modified [who date description]
 * check [who date description]
 */
public interface MessageServant {
	
	/**
	 * ע�ᵽ�����е�service����
	 */
	public static final String SERVICE_NAME="messageServant";
	
	/**
	 * ��Ϣ�˿�
	 * @return
	 */
    public int getMsgPort();
    
    /**
     * ������Ϣ
     * @param sessionId �����ߵĻỰid
     * @param name ��Ϣ�����
     */
    public void subscribe(long sessionId, String name);
    
    /**
     * ������Ϣ
     * @param sessionId �����ߵĻỰid
     * @param name ��Ϣ�����
     */
    public void unsubscribe(long sessionId, String name);
    
}
