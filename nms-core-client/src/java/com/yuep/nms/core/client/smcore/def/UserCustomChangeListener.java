/*
 * $Id: UserCustomChangeListener.java, 2011-3-29 ����11:18:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smcore.def;

import com.yuep.nms.core.common.smcore.model.UserCustom;

/**
 * <p>
 * Title: UserCustomChangeListener
 * </p>
 * <p>
 * Description:�û����Ի���Ϣ�仯�ļ�����
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 ����11:18:47
 * modified [who date description]
 * check [who date description]
 */
public interface UserCustomChangeListener {

    /**
     * ��ǰ�û��ĸ��Ի���Ϣ�仯��
     * @param updatedUserCustom ���º�ĸ��Ի���Ϣ
     */
    public void update(UserCustom updatedUserCustom);
    
    /**
     * ɾ���û��ĸ��Ի���Ϣ
     */
    public void clearAll();
    
}
