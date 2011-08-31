/*
 * $Id: UserProfileService.java, 2011-3-24 ����11:18:54 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import com.yuep.nms.core.common.smcore.model.UserCustom;

/**
 * <p>
 * Title: UserProfileService
 * </p>
 * <p>
 * Description:�û����Ի�����ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:18:54
 * modified [who date description]
 * check [who date description]
 */
public interface UserProfileService {

    /**
     * ��ȡָ���û��ĸ��Ի���Ϣ
     * @param userName �û���
     * @return ���û��ĸ��Ի���Ϣ
     */
    public UserCustom getUserCustom(String userName);
    
    /**
     * ����ָ���û���ĳ�����Ի���Ϣ
     * @param userName �û���
     * @param key ���Ի���Ϣ������
     * @param value ���Ի���Ϣ��ֵ
     */
    public void setUserCustom(String userName,String key,String value);
    
    /**
     * ɾ��ָ���û����Ի���Ϣ��item
     * @param userName �û���
     * @param key ���Ի���Ϣ������
     */
    public void deleteUserCustomItem(String userName,String key);

}
