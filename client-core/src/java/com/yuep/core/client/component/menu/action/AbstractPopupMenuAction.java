/*
 * $Id: AbstractPopupMenuAction.java, 2011-2-15 上午10:40:30 aaron Exp $
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
 * Title: AbstractPopupMenuAction
 * </p>
 * <p>
 * Description: 抽象右键菜单的Action
 * </p>
 * 
 * @author aaron
 * created 2011-2-15 上午10:40:30
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractPopupMenuAction extends DefaultPopupMenuAction{

    private static final long serialVersionUID = -8667121603487445516L;

    /**
     * 构造方法
     * @param isMultiple 是否支持多选
     * @param actionId Action的id
     * @param selectedObjects 选中的对象
     * @param paramObj action所需的参数
     * @param paramClazz 参数的类型
     */
    public AbstractPopupMenuAction(Boolean isMultiple, String actionId, Object[] selectedObjects, Object paramObj,
            String paramClazz) {
        super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
    }

}
