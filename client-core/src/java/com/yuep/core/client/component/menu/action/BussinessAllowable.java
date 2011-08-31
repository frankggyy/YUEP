/*
 * $Id: BussinessAllowable.java, 2009-2-9 下午03:30:53 aaron lee Exp $
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
 * Title: BussinessAllowable
 * </p>
 * <p>
 * Description:客户端业务上判断使能状态
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 下午03:30:53
 * modified [who date description]
 * check [who date description]
 */
public interface BussinessAllowable {

    /**
     * 对instanceProvider提供的数据进行使能状态的判断
     * 
     * @param instanceProvider
     *            实例提供者
     * @return 使能状态，<code>true</code>表示可用，<code>false</code>表示不可用。
     */
    boolean bussinessAllowabled(InstanceProvider instanceProvider);

    /**
     * 对selectedObjects提供的数据进行使能状态的判断
     * 
     * @param selectedObjects
     *            数据对象列表
     * @return 使能状态，<code>true</code>表示可用，<code>false</code>表示不可用。
     */
    boolean bussinessAllowabled(Object[] selectedObjects);
}
