/*
 * $Id: CoreContext4Container.java, 2010-12-13 下午05:59:39 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.container.def;

import com.yuep.core.message.def.MessageMetadata;

/**
 * <p>
 * Title: CoreContext4Container
 * </p>
 * <p>
 * Description:模块管理器接口对容器的管理接口
 * </p>
 * 
 * @author sufeng
 * created 2010-12-13 下午05:59:39
 * modified [who date description]
 * check [who date description]
 */
public interface CoreContext4Container {
    
    /**
     * 设置本子系统的消息服务器配置信息(远端子系统的配置信息在RemoteCommunicationObject中)
     * @param messageMetadata
     */
    public void setSelfMessageMetadata(MessageMetadata messageMetadata);

}
