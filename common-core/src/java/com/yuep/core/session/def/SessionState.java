/*
 * $Id: SessionState.java, 2009-2-20 ����02:05:08 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.session.def;

/**
 * <p>
 * Title: SessionState
 * <p>
 * �ԻỰ״̬��ö������, ƽ̨ͨ��<code>SessionState</code>�����˻Ự�е����п��ܵ�״̬.
 * <p>
 * �Ự״̬�������¼���:<br>
 * ����״̬(Linup)
 * �״̬(Active,���Ӳ���ʼ����ϣ����ڹ���״̬)
 * ���״̬(Deactive)
 * ʧ��״̬(Linkdown):
 * 
 * @author 
 * created 2011-2-20 ����02:05:08
 * 
 * modified [who date description]
 * check [who date description]
 */
public enum SessionState {
   
    /** ����״̬ */
    Linkup,
    
    /**
     * ��Ծ�ģ�����ͨ����ϵͳ����֤������Ծ��
     */
    Active,
    
    /**
     * ����Ծ
     */
    Deactive,
    
    /** ȥ���� */
    Linkdown;
    
}
