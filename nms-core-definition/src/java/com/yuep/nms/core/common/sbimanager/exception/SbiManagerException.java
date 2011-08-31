/*
 * $Id: SbiManagerException.java, 2011-4-15 下午02:03:13 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.sbimanager.exception;

import com.yuep.base.exception.YuepException;

/**
 * <p>
 * Title: SbiManagerException
 * </p>
 * <p>
 * Description:
 * SbiManager模块异常定义
 * </p>
 * 
 * @author yangtao
 * created 2011-4-15 下午02:03:13
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerException extends YuepException {
    
    private static final long serialVersionUID = -2681122620185012334L;

    /**
     * sbi已绑定过了NE
     */
    public final static int SBI_BIND_NE=3001;
    /**
     * sbi名字重复
     */
    public final static int SBI_NAME_EXIST=3002;
    
    public SbiManagerException(int errorCode, String... source) {
        super(errorCode, source);
    }

  

}
