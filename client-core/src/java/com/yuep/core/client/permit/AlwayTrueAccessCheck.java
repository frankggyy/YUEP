/*
 * $Id: AlwayTrueAccessCheck.java, 2011-3-29 ����02:24:24 sufeng Exp $
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
 * Title: AlwayTrueAccessCheck
 * </p>
 * <p>
 * Description: Ĭ�ϵĲ�����Ȩ�޼���ʵ��
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 ����02:24:24
 * modified [who date description]
 * check [who date description]
 */
public class AlwayTrueAccessCheck implements AccessCheck {

    @Override
    public boolean check(String actionId, Object... objectIds) {
        // �ܷ���true����ʾ�����Ȩ��
        return true;
    }

}
