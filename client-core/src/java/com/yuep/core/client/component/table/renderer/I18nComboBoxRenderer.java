/*
 * $Id: I18nComboBoxRenderer.java, 2011-4-26 上午10:06:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table.renderer;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: I18nComboBoxRenderer
 * </p>
 * <p>
 * Description:ComboBox在表格中的国际化Renderer
 * </p>
 * 
 * @author sufeng
 * created 2011-4-26 上午10:06:47
 * modified [who date description]
 * check [who date description]
 */
public class I18nComboBoxRenderer extends BasicComboBoxRenderer{

    private static final long serialVersionUID = 4394119756113909314L;

    @Override
    public Component getListCellRendererComponent(JList jlist, Object obj, int i, boolean flag, boolean flag1) {
        Component comp = super.getListCellRendererComponent(jlist, obj, i, flag, flag1);
        if(obj!=null){
            String key=obj.toString();
            String i18nText = ClientCoreContext.getString(key);
            setText(i18nText);
        }else{
            setText(null);
        }
        return comp;
    }
    
}
