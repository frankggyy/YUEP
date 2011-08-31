/*
 * $Id: SensitiveAction.java, 2009-2-9 下午03:42:39 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

/**
 * <p>
 * Title: SensitiveAction
 * </p>
 * <p>
 * Description:对用户Table选择事件敏感的一个接口。
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 下午03:42:39
 * modified [who date description]
 * check [who date description]
 */
public interface SensitiveAction {

    /**
     * 该接口定义了selectionChanged 方法，当用户选择对象发生变化时，可以通过该方法通知本Action，
     * 
     * @param instanceProvider
     *            用户选择对象实例提供者
     */
    void selectionChanged(InstanceProvider instanceProvider);
}
