/*
 * $Id: MoManagerException.java, 2011-5-10 ����03:40:38 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.momanager.exception;

import com.yuep.base.exception.YuepException;

/**
 * <p>
 * Title: MoManagerException
 * </p>
 * <p>
 * Description:
 * MoManagerģ���쳣����
 * </p>
 * 
 * @author yangtao
 * created 2011-5-10 ����03:40:38
 * modified [who date description]
 * check [who date description]
 */
public class MoManagerException extends YuepException {
    
    private static final long serialVersionUID = 797256706198474945L;
    /**
     * Mo��̬�����ļ�û���ҵ�
     */
    public static final int STATIC_CONFIG_NOTFOUND=5001;
    /**
     * ��Mo�ڵ㲻��������Mo�ڵ�
     */
    public static final int PARENT_NOTACCEPT_CHILD=5002;
    
    public MoManagerException(int errorCode, String... sources) {
        super(errorCode, sources);
    }
}
