/*
 * $Id: ThreadPoolManager.java, 2010-7-1 ����10:15:30 jimsu Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.base.concurrent.def.threadpool;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 * Title: ThreadPoolManager
 * </p>
 * <p>
 * Description: �̳߳ع���ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2010-7-1 ����10:15:30
 * modified [who date description]
 * check [who date description]
 */
public interface ThreadPoolManager {
    
    /**
     * ��ȡ�̳߳�
     * @param poolName eg:polltask,batchworker,���û�����
     * @return ���poolName�����ڣ��᷵��һ��Ĭ�ϵ��̳߳�
     */
    public ExecutorService getThreadPool(String poolName);
    
    /**
     * ��ȡȱʡ���̳߳�����
     * @return �̳߳���
     */
    public List<String> getDefaultPoolNames();
    
    /**
     * �����µ��̳߳�
     * @param poolName �̳߳����ƣ��ظ������쳣
     * @param threadNum ���̳߳ع̶����߳���
     */
    public void addThreadPool(String poolName,Integer threadNum);
    
    /**
     * ɾ��һ���̳߳�,���������ύ�µ�ִ������,�����pool��thread��ִ�У���threadִ�н�����thread pool�ͷ���Դ
     * @param poolName �̳߳���
     */
    public void removeThreadPool(String poolName);

}
