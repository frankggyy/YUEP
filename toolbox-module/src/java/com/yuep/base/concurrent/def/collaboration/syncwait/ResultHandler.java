/*
 * $Id: ResultHandler.java, 2009-11-30 ����02:18:25 jimsu Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.def.collaboration.syncwait;


/**
 * <p>
 * Title: ResultHandler
 * </p>
 * <p>
 * Description: ��Ҫ����ʵ�����࣬ר��������ȡ��������жϽ���Ƿ����
 * </p>
 * 
 * @author sufeng
 * created 2009-11-30 ����02:18:25
 * modified [who date description]
 * check [who date description]
 */
public interface ResultHandler {

    /**
     * �������󣬱�������ݿ��ȡһ����¼���·����õ��豸�ȣ������ؽ��
     * @return
     */
    public ResultStauts getSingleResult();
    
    /**
     * �Ժ����������첽�����ⲿ����Ϣ����
     * @param data
     */
    public void postData(Object data);
    
}
