/*
 * $Id: MessageReceiver.java, 2010-9-27 下午04:30:50 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.message.def;

import java.io.Serializable;

import com.yuep.core.container.def.CommunicateObject;

/**
 * <p>
 * Title: MessageReceiver
 * </p>
 * <p>
 * Description: 消息接收回调
 * </p>
 * 
 * @author aaron
 * created 2010-9-27 下午04:30:50
 * modified [who date description]
 * check [who date description]
 * @see com.yuep.core.container.def.CommunicateObject
 */
public interface MessageReceiver {
    
    /**
     * 消息接收到的回调处理
     * @param co  通信对象
     * @param name 消息名
     * @param msg  消息体
     */
    public void receive(CommunicateObject co, String name, Serializable msg);
    
}
