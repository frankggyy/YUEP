/*
 * $Id: XMenuItem.java, 2009-2-9 ����03:15:40 aaron lee Exp $
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
 * Title: XMenuItem
 * </p>
 * <p>
 * Description:�����ļ������ò˵�����Ϣ
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:15:40
 * modified [who date description]
 * check [who date description]
 */
public class XMenuItem {

    /**
     * �˵����ͼƬ·��
     */
    private String icon;

    /**
     * �˵������ʾ��Ϣ
     */
    private String text;

    /**
     * �˵���Ŀ�ݼ�
     */
    private Character mnemonic;
    
    /**
     * ѡ�е��Ƿ��Ƕ������
     */
    private boolean multiple;

	/**
     * �˵���ʹ�õ�action��������Ķ������·��
     */
    private String paramClass;
    
    /**
     * ʵ�ʵ�action���·�������ݸ�·����������action����
     */
    private String clazz;

    /**
     * �˵�������ͣ��豸�˵�ʹ����������Ҫ����
     */
    private String itemType;

    /**
     * ��������
     */
    private String sourceType;

    /**
     * ��ť����
     */
    private String buttonGroupName;

    /**
     * �˵���
     */
    private List<XMenuItem> xMenuItems;

    /**
     * ���õĲ˵�
     */
    private String referenceMenu;

    /**
     * @return the buttonGroupName
     */
    public String getButtonGroupName() {
        return buttonGroupName;
    }
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @return the itemType
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * @return the mnemonic
     */
    public Character getMnemonic() {
        return mnemonic;
    }

    /**
     * @return the paramClass
     */
    public String getParamClass() {
        return paramClass;
    }

    /**
     * @return the referenceMenu
     */
    public String getReferenceMenu() {
        return referenceMenu;
    }

    /**
     * @return the sourceType
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the xMenuItems
     */
    public List<XMenuItem> getXMenuItems() {
        return xMenuItems;
    }

    /**
     * @param buttonGroupName
     *            the buttonGroupName to set
     */
    public void setButtonGroupName(String buttonGroupName) {
        this.buttonGroupName = buttonGroupName;
    }

    /**
     * @param icon
     *            the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @param itemType
     *            the itemType to set
     */
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    /**
     * @param mnemonic
     *            the mnemonic to set
     */
    public void setMnemonic(Character mnemonic) {
        this.mnemonic = mnemonic;
    }

    /**
     * @param paramClass
     *            the paramClass to set
     */
    public void setParamClass(String paramClass) {
        this.paramClass = paramClass;
    }

    /**
     * @param referenceMenu
     *            the referenceMenu to set
     */
    public void setReferenceMenu(String referenceMenu) {
        this.referenceMenu = referenceMenu;
    }

    /**
     * @param sourceType
     *            the sourceType to set
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param menuItems
     *            the xMenuItems to set
     */
    public void setXMenuItems(List<XMenuItem> menuItems) {
        xMenuItems = menuItems;
    }
    

    public boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
