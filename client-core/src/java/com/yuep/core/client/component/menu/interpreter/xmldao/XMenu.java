/*
 * $Id: XMenu.java, 2009-2-9 ����03:15:31 aaron lee Exp $
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
 * Title: XMenu
 * </p>
 * <p>
 * Description:�����ļ������õĲ˵���Ϣ
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-9 ����03:15:31
 * modified [who date description]
 * check [who date description]
 */
public class XMenu {
    /**
     * �˵���
     */
    private String name;
    
    /**
     * �˵���ʾ��
     */
    private String text;
    
    /**
     * �˵��Ŀ�ݼ�
     */
    private Character mnemonic;

    /**
     * ���õĲ˵���
     */
    private String referenceMenu;

    /**
     * �˵����б�
     */
    private List<XMenuItem> xMenuItems;

    /**
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the referenceMenu
     */
    public String getReferenceMenu() {
        return referenceMenu;
    }

    /**
     * @return the xMenuItems
     */
    public List<XMenuItem> getXMenuItems() {
        return xMenuItems;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param referenceMenu
     *            the referenceMenu to set
     */
    public void setReferenceMenu(String referenceMenu) {
        this.referenceMenu = referenceMenu;
    }

    /**
     * @param menuItems
     *            the xMenuItems to set
     */
    public void setXMenuItems(List<XMenuItem> menuItems) {
        xMenuItems = menuItems;
    }

    /**
     * @return the mnemonic
     */
    public Character getMnemonic() {
        return mnemonic;
    }

    /**
     * @param mnemonic the mnemonic to set
     */
    public void setMnemonic(Character mnemonic) {
        this.mnemonic = mnemonic;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
}
