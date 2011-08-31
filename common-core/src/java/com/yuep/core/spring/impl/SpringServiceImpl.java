/*
 * $Id: SpringServiceImpl.java, 2010-9-17 下午03:03:22 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.spring.impl;

import org.springframework.context.ApplicationContext;

import com.yuep.core.spring.def.SpringService;

/**
 * <p>
 * Title: SpringServiceImpl
 * </p>
 * <p>
 * Description:spring service实现类
 * </p>
 * 
 * @author aaron
 * created 2010-9-19 下午04:33:56
 * modified [who date description]
 * check [who date description]
 */
public class SpringServiceImpl implements SpringService {

    private ApplicationContext ctx;
    
	@Override
	public ApplicationContext getCoreAppCtx() {
		return ctx;
	}
	
	@Override
    public void setCoreAppCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }

	@SuppressWarnings("unchecked")
    @Override
	public <T> T getBean(String beanName, Class<T> clazz) {
		return (T) ctx.getBean(beanName,clazz);
	}

}
