/*
 * $Id: Module.java, 2010-9-17 ����01:34:50 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

import java.util.Map;

import com.yuep.base.log.def.Logger;


/**
 * <p>
 * Title: Module
 * </p>
 * <p>
 * Description: ģ��
 * </p>
 * 
 * @author aaron
 * created 2010-9-17 ����01:34:50
 * modified [who date description]
 * check [who date description]
 */
public interface Module extends ModuleConstants{

    /**
     * ��ȡģ����
     * @return ģ����
     */
    public String getModuleName();
    
    /**
     * ��ȡģ�鵱ǰ״̬ 
     * @return ģ��״̬
     */
    public String getModuleStatus();

    /**
     * ��ȡģ�����в�����Ϣ
     * @return ģ�������Ϣ
     */
    public Map<String, String> getModuleParams();
    
    /**
     * ��ȡģ���Logger
     * @return ģ��Logger
     */
    public Logger getLogger();
    /**
     * ��ȡģ������·��
     * @return
     */
    public String getModulePath();
    
}
