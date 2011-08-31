/*
 * $Id: DefaultStartCoreContainerRsp.java, 2010-9-29 ����08:59:13 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.processing.rsp;

import com.yuep.core.client.main.process.rsp.AbstractResponsibility;

/**
 * <p>
 * Title: DefaultStartCoreContainerRsp
 * </p>
 * <p>
 * Description:Ĭ�ϼ���ApplicationContext��Responsibility��ְ��飩
 * </p>
 * 
 * @author aaron
 * created 2010-9-29 ����08:59:13
 * modified [who date description]
 * check [who date description]
 */
public class DefaultLoadApplicationContextRsp<T> extends AbstractResponsibility<T> {
    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
     */
    @Override
    public boolean execute(T t) {
        
        return true;
    }

}
