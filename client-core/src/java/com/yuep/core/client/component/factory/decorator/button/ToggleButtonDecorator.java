/*
 * $Id: ToggleButtonDecorator.java, 2010-10-18 上午10:37:01 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.factory.decorator.button;

import javax.swing.Icon;
import javax.swing.JToggleButton;

import com.yuep.core.client.component.factory.decorator.DefaultDecorator;

/**
 * <p>
 * Title: ToggleButtonDecorator
 * </p>
 * <p>
 * Description:ToggleButton 按钮的装饰器
 * </p>
 * 
 * @author aaron
 * created 2010-10-18 上午10:37:01
 * modified [who date description]
 * check [who date description]
 */
public class ToggleButtonDecorator extends DefaultDecorator<JToggleButton>{
    
    private Icon icon;
    public ToggleButtonDecorator() {
    }
    
    public ToggleButtonDecorator(Icon icon) {
        this.icon = icon;
    }

    /**
     * @see com.yuep.core.client.component.factory.decorator.Decorator#decorate(java.lang.Object)
     */
    @Override
    public void decorate(JToggleButton t) {
        if(icon == null)
            t.setIcon(icon);
    }

}
