/*
 * $Id: Module4Container.java, 2010-11-5 ����10:35:10 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

import com.yuep.base.log.def.Logger;



/**
 * <p>
 * Title: Module4Container
 * </p>
 * <p>
 * Description: ��������ģ����ڲ�����ӿڣ�����¶���ϲ�Ӧ�ã�
 * </p>
 * 
 * @author sufeng
 * created 2010-11-5 ����10:35:10
 * modified [who date description]
 * check [who date description]
 */
public interface Module4Container {

    /**
     * ����ģ����
     * @param name
     */
    public void setModuleName(String name);
 
    /***
     * ����ģ���״̬
     * @param moduleStatus
     */
    public void setModuleStatus(String moduleStatus);
    
    /**
     * �����������
     * @param classLoader
     */
    public void setClassLoader(ClassLoader classLoader);
    
    /**
     * ����module����־
     * @param logger
     */
    public void setLogger(Logger logger);

    /**
     * ģ���������ʼ��
     * @return
     */
    public void start();
 
    /**
     * ֹͣģ��
     * @return
     */
    public void stop();

    
}
