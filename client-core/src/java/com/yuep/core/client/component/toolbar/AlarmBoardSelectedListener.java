/*
 * $Id: AlarmBoardSelectedListener.java, 2009-6-6 ����01:30:30 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar;

import twaver.AlarmSeverity;


/**
 * <p>
 * Title: AlarmBoardSelectedListener
 * </p>
 * <p>
 * Description: �澯��ѡ���¼�������
 * </p>
 * 
 * @author yangtao
 * created 2009-6-6 ����01:30:30
 * modified [who date description]
 * check [who date description]
 */
public interface AlarmBoardSelectedListener {
    /**
     * ѡ��澯�ƺ�Ҫ���Ĳ���
     * @param alarmSeverity
     */
    public void selected(AlarmSeverity alarmSeverity);

}
