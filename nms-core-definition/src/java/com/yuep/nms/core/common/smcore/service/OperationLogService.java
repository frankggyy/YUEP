/*
 * $Id: OperationLogService.java, 2011-3-24 ����11:19:06 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import java.util.List;

import com.yuep.nms.core.common.smcore.model.OperationLog;
import com.yuep.nms.core.common.smcore.model.OperationLogCondition;

/**
 * <p>
 * Title: OperationLogService
 * </p>
 * <p>
 * Description:������־�ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:19:06
 * modified [who date description]
 * check [who date description]
 */
public interface OperationLogService {

    /**
     * ���1������������־
     * @param logs
     */
    public void addOperationLog(OperationLog ... logs);
    
    /**
     * ��������ѯ��־��¼
     * @param cond ��ѯ����
     * @return ��־list
     */
    public List<OperationLog> getOperationLog(OperationLogCondition cond);
    
    /**
     * �õ���ǰ����ϵͳ�����еĲ�������
     * @return ϵͳ�����еĲ�������
     */
    public List<String> getAllOperationObjects();

    /**
     * �õ���ǰ����ϵͳ�����еĲ�������
     * @return ϵͳ�����еĲ�������
     */
    public List<String> getAllOperationNames();
    
    /**
     * �õ���ǰ����ϵͳ�����еĲ���Ա
     * @return
     */
    public List<String> getAllOperationUserNames();
    
}
