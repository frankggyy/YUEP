/*
 * $Id: DbServerManager.java, 2011-3-21 ����09:36:08 Administrator Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.server.manager.def;

import java.util.Map;

/**
 * <p>
 * Title: DbServerManager
 * </p>
 * <p>
 * Description:
 * ���ݿ����ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-21 ����09:36:08
 * modified [who date description]
 * check [who date description]
 */
public interface DbServerManager {
    
    /**
     * �������ݿ������
     * @param dbRunningParams
     *        ���ݿ����������
     */
    public void start(Map<String,String> dbParams);
    
    /**
     * �ر����ݿ������
     */
    public void close();

}
