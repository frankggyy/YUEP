/*
 * $Id: ModuleConstants.java, 2009-2-10 ����01:15:55 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Communications Industry Group Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

/**
 * <p>
 * Title: ModuleConstants
 * </p>
 * <p>
 * Description: ģ�����Ƶĳ����࣬����ģ���״̬
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-10 ����01:15:55
 * modified [who date description]
 * check [who date description]
 */
public interface ModuleConstants {
    
    /**
     * �����״̬
     */
    String STATUS_NONE = "NONE";
    
    /**
     * ģ���ѱ�ʶ�𲢼���,����δ����
     */
    String STATUS_LOADED="LOADED";
    
    /**
     * ����������
     */
    String STATUS_STARTING = "STARTING";
    
    /**
     * ����ʧ����
     */
    String STATUS_FAILED="FAILED";
    
    /**
     * ������ϣ������ṩ����
     */
    String STATUS_ACTIVE = "ACTIVE";
    
    /**
     * ����ֹͣ��
     */
    String STATUS_STOPPING = "STOPPING";
    
    /**
     * ֹͣ��ϣ�����deactive״̬���޷��ṩ����
     */
    String STATUS_DEACTIVE = "DEACTIVE";
    
}
