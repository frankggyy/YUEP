/*
 * $Id: MoException.java, 2011-3-28 下午03:00:46 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.exception;

import com.yuep.base.exception.YuepException;

/**
 * <p>
 * Title: MoException
 * </p>
 * <p>
 * Description: 
 * Mo模块异常定义
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 下午03:00:46
 * modified [who date description]
 * check [who date description]
 */
public class MoException extends YuepException {
	private static final long serialVersionUID = -72265682164406251L;

	/**连接失败*/
	public static final int CONNETION_FAILURE=4001;
	/**IP地址已经存在*/
	public static final int IP_EXIST=4002;
	public MoException(int errorCode, String... sources) {
		super(errorCode, sources);
	}

}
