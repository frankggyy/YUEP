/*
 * $Id: PermissionService.java, 2011-3-24 ����11:18:09 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.service;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: PermissionService
 * </p>
 * <p>
 * Description:��Ȩ�ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 ����11:18:09
 * modified [who date description]
 * check [who date description]
 */
public interface PermissionService {

    /**
     * ��һ������MO��һ������Action����������֤
     * @param user �û���
     * @param actionId action����function��
     * @param moNamings MO������
     * @return
     */
    public boolean check(String user,String actionId, MoNaming ... moNamings);
    
}
