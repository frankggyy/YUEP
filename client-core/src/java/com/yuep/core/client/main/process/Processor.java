/*
 * $Id: Processor.java, 2010-4-27 ����03:08:52 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.process;

/**
 * <p>
 * Title: Processor
 * </p>
 * <p>
 * Description:�ͻ��������������̽ӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 ����03:08:52
 * modified [who date description]
 * check [who date description]
 */
public interface Processor<T> {
    
    /**
     * �ͻ��������������ͻ���������������
     * 
     * @param T
     *            �ͻ�����������
     */
    public void process(T t);
    
}
