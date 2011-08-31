/*
 * $Id: LookAndFeelEnum.java, 2010-4-27 下午03:17:54 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.model;

/**
 * <p>
 * Title: LookAndFeelEnum
 * </p>
 * <p>
 * Description:LookAndFell的枚举类
 * </p>
 * 
 * @author aaron lee
 * created 2010-4-27 下午03:17:54
 * modified [who date description]
 * check [who date description]
 */
public enum LookAndFeelEnum {
    blue("org.jvnet.substance.skin.OfficeBlue2007Skin",
            "org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel",
            "common.mainframe.menu.appearance.blue2007"), bluesteel(
            "org.jvnet.substance.skin.BusinessBlueSteelSkin",
            "org.jvnet.substance.skin.SubstanceBusinessBlueSteelLookAndFeel",
            "common.mainframe.menu.appearance.bluesteel"), gray("org.jvnet.substance.skin.BusinessSkin",
            "org.jvnet.substance.skin.SubstanceBusinessLookAndFeel", "common.mainframe.menu.appearance.gray"), silver(
            "org.jvnet.substance.skin.OfficeSilver2007Skin",
            "org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel",
            "common.mainframe.menu.appearance.silver2007");

    private String skin;
    private String laf;
    private String menuText;

    /**
     * @return the menuText
     */
    public String getMenuText() {
        return menuText;
    }

    /**
     * @return the skin
     */
    public String getSkin() {
        return skin;
    }

    /**
     * @return the laf
     */
    public String getLaf() {
        return laf;
    }

    LookAndFeelEnum(String skin, String laf, String menuText) {
        this.skin = skin;
        this.laf = laf;
        this.menuText = menuText;
    }

    public static LookAndFeelEnum parse(String skinOrLaf) {
        for (LookAndFeelEnum element : values()) {
            if (element.getLaf().equals(skinOrLaf) || element.getSkin().equals(skinOrLaf)) {
                return element;
            }
        }
        return blue;
    }
}
