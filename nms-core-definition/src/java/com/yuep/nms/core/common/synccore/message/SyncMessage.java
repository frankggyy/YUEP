/*
 * $Id: SyncMessage.java, 2011-5-18 下午07:20:44 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.synccore.message;

import java.io.Serializable;

import com.yuep.core.message.def.BaseMessage;

/**
 * <p>
 * Title: SyncMessage
 * </p>
 * <p>
 * Description:
 * 同步消息定义
 * </p>
 * 
 * @author yangtao
 * created 2011-5-18 下午07:20:44
 * modified [who date description]
 * check [who date description]
 */
public class SyncMessage extends BaseMessage {
    
    private static final long serialVersionUID = 1792148638850622153L;
    
    public static final String START_SYNC="start.sync";
    public static final String END_SYNC="end.sync";
    public static final String START_SYNC_ITEM="start.sync.item";
    public static final String END_SYNC_ITAEM="end.sync.item";
    public static final String SHUTDOWN_SYNC="shutdown.sync";
    
    public static final String SYNC_MESSAGE="sync.message";
    
    private String messageType;
    public SyncMessage(String messageName,String messageType, Serializable messageSource) {
        super(messageName, messageSource);
        this.messageType=messageType;
    }
    public String getMessageType() {
        return messageType;
    }

    
    
  

}
