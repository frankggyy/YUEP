/*
 * $Id: XMenus.java, 2009-2-9 下午03:15:52 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.interpreter.xmldao;

import java.util.List;

/**
 * <p>
 * Title: XMenus
 * </p>
 * <p>
 * Description:多个xmenu
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 下午03:15:52
 * modified [who date description]
 * check [who date description]
 */
public class XMenus {
    private List<XMenu> menus;

    /**
     * @return the menus
     */
    public List<XMenu> getMenus() {
        return menus;
    }

    /**
     * @param menus
     *            the menus to set
     */
    public void setMenus(List<XMenu> menus) {
        this.menus = menus;
    }
}
