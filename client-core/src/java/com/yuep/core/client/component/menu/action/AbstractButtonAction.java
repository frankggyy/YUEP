/*
 * $Id: AbstractButtonAction.java, 2011-2-15 上午10:40:07 aaron Exp $
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
 * Title: AbstractButtonAction
 * </p>
 * <p>
 * Description:抽象的按钮action
 * </p>
 * 
 * @author aaron
 * created 2011-2-15 上午10:40:07
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractButtonAction extends XAbstractAction{

    private static final long serialVersionUID = 4929582945366440067L;

    /**
     * 构造方法
     * @param actionId 按钮action的id
     */
    public AbstractButtonAction(String actionId) {
        super(actionId);
    }

}
