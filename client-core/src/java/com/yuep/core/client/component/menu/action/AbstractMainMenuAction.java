/*
 * $Id: AbstractMainMenuAction.java, 2011-2-15 ����10:41:03 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

/**
 * <p>
 * Title: AbstractMainMenuAction
 * </p>
 * <p>
 * Description:��������˵�action
 * </p>
 * 
 * @author aaron
 * created 2011-2-15 ����10:41:03
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractMainMenuAction extends XAbstractAction{

    private static final long serialVersionUID = 3421665019470657988L;

    /**
     * ���췽��
     * @param actionId ���˵���action��id
     */
    public AbstractMainMenuAction(String actionId) {
        super(actionId);
    }

}
