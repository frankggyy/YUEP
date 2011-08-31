/*
 * $Id: JComponentActionHelper.java, 2009-2-9 下午04:24:08 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table;

import java.util.HashSet;
import java.util.Set;

import com.yuep.core.client.component.menu.action.SensitiveAction;

/**
 * <p>
 * Title: JComponentActionHelper
 * </p>
 * <p>
 * Description:JComponent action helper.
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 下午04:24:08
 * modified [who date description]
 * check [who date description]
 */
public class JComponentActionHelper {

    /**
     * 存储对用户JComponent选择事件敏感的action for <code>actionSet</code>
     */
    protected Set<SensitiveAction> actionSet = new HashSet<SensitiveAction>();

    /**
     * 添加对用户JComponent选择事件敏感的action。
     * 
     * @param sensitiveAction
     *            对用户JComponent选择事件敏感的action
     */
    public void addSensitiveAction(SensitiveAction sensitiveAction) {
        actionSet.add(sensitiveAction);
    }
}
