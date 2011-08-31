/*
 * $Id: LoginProcessor.java, 2010-4-27 下午03:10:47 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.processing;

import com.yuep.core.client.main.process.DefaultProcessor;

/**
 * <p>
 * Title: LoginProcessor
 * </p>
 * <p>
 * Description:默认的客户端登陆进程启动类
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:10:47
 * modified [who date description]
 * check [who date description]
 */
public class DefaultLoginProcessor<T> extends DefaultProcessor<T> {

    public DefaultLoginProcessor() {
        // 读取登录流程
        interpreterProcessor("login-process.xml");
    }
}
