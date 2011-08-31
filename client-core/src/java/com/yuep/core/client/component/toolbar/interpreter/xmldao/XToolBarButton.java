/*
 * $Id: ToolBarButton.java, 2009-2-18 上午09:56:22 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.toolbar.interpreter.xmldao;

/**
 * <p>
 * Title: ToolBarButton
 * </p>
 * <p>
 * Description:工具栏按钮的XML Dao映射类
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-18 上午09:56:22
 * modified [who date description]
 * check [who date description]
 */
public class XToolBarButton {

	/**
     * 实际的action类的路径，根据该路径反射生产action对象
     */
    private String clazz;

    /**
     * Default icon
     */
    private String icon;

    /**
     * Rollover icon
     */
    private String rolloverIcon;

    /**
     * Tool Tip
     */
    private String toolTip;

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the rolloverIcon
     */
    public String getRolloverIcon() {
        return rolloverIcon;
    }

    /**
     * @param rolloverIcon
     *            the rolloverIcon to set
     */
    public void setRolloverIcon(String rolloverIcon) {
        this.rolloverIcon = rolloverIcon;
    }

    /**
     * @return the toolTip
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * @param toolTip
     *            the toolTip to set
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
