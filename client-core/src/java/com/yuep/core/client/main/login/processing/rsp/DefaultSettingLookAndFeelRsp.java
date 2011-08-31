/*
 * $Id: SettingLookAndFeelRsp.java, 2010-4-27 下午03:10:47 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.main.login.processing.rsp;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;

import com.yuep.core.client.main.login.model.LookAndFeelEnum;
import com.yuep.core.client.main.process.rsp.AbstractResponsibility;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: SettingLookAndFeelRsp
 * </p>
 * <p>
 * Description:默认设置LookAndFeel的Responsibility（职责块）
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-21 下午12:00:21
 * modified [who date description]
 * <p>sufeng,2010-3-23,add,store profile properties to client context
 * check [who date description]
 */
public class DefaultSettingLookAndFeelRsp<T> extends AbstractResponsibility<T> {

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.main.process.rsp.Responsibility#execute(java.lang.Object)
     */
    @Override
    public boolean execute(T t) {
        try {
            //String skin = null;
            //if (skin != null) {
            //    UIManager.setLookAndFeel(skin);
            //} else {
            //    UIManager.setLookAndFeel(LookAndFeelEnum.blue.getLaf());
            //}
            UIManager.setLookAndFeel(LookAndFeelEnum.blue.getLaf());

            Enumeration<Object> keys = UIManager.getLookAndFeelDefaults().keys();
            // 设置缺省字体,为Dialog 12
            Font defaultFont = new Font("Dialog", Font.PLAIN, 12);
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();

                if (UIManager.get(key) instanceof Font) {
                    UIManager.put(key, defaultFont);
                }
            }
            
        } catch (Exception e) {
            DialogUtils.showErrorExpandDialog(null, e);
            return false;
        }
        return true;
    }

}
