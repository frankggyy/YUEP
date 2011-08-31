/*
 * $Id: MoStaticInfoManager.java, 2011-3-29 ����11:17:56 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.service;

import com.yuep.nms.core.common.mocore.model.MoStaticInfo;

/**
 * <p>
 * Title: MoStaticInfoManager
 * </p>
 * <p>
 * Description:
 * Mo��̬��Ϣ����ӿ�
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 ����11:17:56
 * modified [who date description]
 * check [who date description]
 */
public interface MoStaticInfoManager {
    /**
     * ����Mo��ϸ���ͻ�ȡMo��̬��Ϣ
     * @param type
     * @return
     *        Mo��̬��Ϣ
     */
    MoStaticInfo getMoStaticInfo (String type);
    
    /**
     * �ڸ��������Ƿ��ܽ���ָ����������
     * @param parentType
     *        ������
     * @param childType
     *        ������
     * @return
     *        true:���Խ���,false:�����Խ���
     */
    boolean accept(String parentType,String childType);
    
    
    /**
     * ����type�Ƿ������subKinds��
     * @param subKinds
     *        �������������
     * @param type
     *        ���������ϸ����
     * @return
     *        true:����,false:������
     */
    boolean contain(String type,String... subKinds);
}
