/*
 * $Id: JmsServerManager.java, 2010-9-20 上午11:32:51 sufeng Exp $
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
 * Description: JMS服务器的维护操作接口
 * </p>
 * 
 * @author sufeng
 * created 2010-9-20 上午11:32:51
 * modified [who date description]
 * check [who date description]
 */
public interface JmsServerManager {

    /**
     * 启动jms服务器,最先被执行
     * @param  messageMetadata
     */
    public void start(MessageMetadata messageMetadata);
    
    /**
     * 停止jms服务器
     * @param messageMetadata
     */
    public void shutdown(MessageMetadata messageMetadata);
    
    /**
     * 初始化topic
     * @param messageMetadata
     * @return true创建成功 false失败
     */
    public boolean initTopic(MessageMetadata messageMetadata);

    /**
     * 删除topic
     * @param messageMetadata
     */
    public void closeTopic(MessageMetadata messageMetadata);
    
}
