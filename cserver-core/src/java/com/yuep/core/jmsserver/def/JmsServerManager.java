/*
 * $Id: JmsServerManager.java, 2010-9-20 ����11:32:51 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.jmsserver.def;

import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: JmsServerManager
 * </p>
 * <p>
 * Description: JMS��������ά�������ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2010-9-20 ����11:32:51
 * modified [who date description]
 * check [who date description]
 */
public interface JmsServerManager {

    /**
     * ����jms������,���ȱ�ִ��
     * @param  messageMetadata
     */
    public void start(MessageMetadata messageMetadata);
    
    /**
     * ֹͣjms������
     * @param messageMetadata
     */
    public void shutdown(MessageMetadata messageMetadata);
    
    /**
     * ��ʼ��topic
     * @param messageMetadata
     * @return true�����ɹ� falseʧ��
     */
    public boolean initTopic(MessageMetadata messageMetadata);

    /**
     * ɾ��topic
     * @param messageMetadata
     */
    public void closeTopic(MessageMetadata messageMetadata);
    
}
