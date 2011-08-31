/*
 * $Id: ModuleContextAware.java, 2011-3-31 ����01:22:05 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def;

/**
 * <p>
 * Title: ModuleContextAware
 * </p>
 * <p>
 * Description: ģ���ʵ�ָýӿڣ���load module��ʱ��,�ὫModuleContextʵ�����õ���ǰmodule��
 * ���������Ա����õ���ModuleContext.getInstance()��ȡModuleContext����������Ԫ����
 * </p>
 * 
 * @author sufeng
 * created 2011-3-31 ����01:22:05
 * modified [who date description]
 * check [who date description]
 */
public interface ModuleContextAware {

    /**
     * ����module context
     * @param moduleContext ģ��������
     */
    public void setModuleContext(ModuleContext moduleContext);
    
}
