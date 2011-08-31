/*
 * $Id: StartClientProcessor.java, 2010-4-27 下午03:24:05 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.mainframe.processing;

import com.yuep.core.client.main.process.DefaultProcessor;

/**
 * <p>
 * Title: StartClientProcessor
 * </p>
 * <p>
 * Description:客户端启动流程处理器默认实现
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:24:05
 * modified [who date description]
 * check [who date description]
 */
public class DefaultStartClientProcessor<T> extends DefaultProcessor<T> {

    public DefaultStartClientProcessor() {
        interpreterProcessor("start-process.xml");
    }

}
