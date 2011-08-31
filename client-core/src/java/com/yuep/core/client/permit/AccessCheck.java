/*
 * $Id: AccessCheck.java, 2011-3-29 ����02:20:15 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.permit;


/**
 * <p>
 * Title: AccessCheck
 * </p>
 * <p>
 * Description: ����Ȩ����֤�ӿ�
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 ����02:20:15
 * modified [who date description]
 * check [who date description]
 */
public interface AccessCheck {

    /**
     * ��ǰ�û���mo�Ƿ���actionid��Ȩ��
     * @param actionId action ID����ID
     * @param objectIds ��������
     * @return true��Ȩ��
     */
    public boolean check(String actionId, Object... objectIds);
    
}
