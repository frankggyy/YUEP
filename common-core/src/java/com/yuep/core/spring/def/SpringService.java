/*
 * $Id: SpringService.java, 2010-9-17 ����03:03:22 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.spring.def;

import org.springframework.context.ApplicationContext;

/**
 * <p>
 * Title: SpringService
 * </p>
 * <p>
 * Description: ����spring��ܣ������ṩ�ķ��񣬱����ȡspring�������ġ���ȡһ��spring bean
 * </p>
 * 
 * @author aaron
 * created 2010-9-19 ����04:34:52
 * modified [who date description]
 * check [who date description]
 */
public interface SpringService {
	
    /**
     * ��ȡspring application context
     * @return
     */
	public ApplicationContext getCoreAppCtx();
	
	/**
	 * ����spring application context
	 * @param ctx
	 */
	public void setCoreAppCtx(ApplicationContext ctx);
	
	/**
	 * ��spring�����л�ȡһ��bean
	 * @param <T>
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public <T> T getBean(String beanName,Class<T> clazz);
	
}