/*
 * $Id: TimeCellRenderer.java, 2011-4-26 上午09:37:22 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table.renderer;

import java.util.Date;

import com.yuep.base.util.format.DateFormatter;

/**
 * <p>
 * Title: TimeCellRenderer
 * </p>
 * <p>
 * Description:表格中时间国际化Renderer。时间可以为两种形式1)Long 2)Date
 * </p>
 * 
 * @author sufeng
 * created 2011-4-26 上午09:37:22
 * modified [who date description]
 * check [who date description]
 */
public class TimeCellRenderer extends XTableCellRenderer {

    private static final long serialVersionUID = -8664622599308787437L;

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.component.table.renderer.GetRenderVaule#getRenderVaule(java.lang.Object)
     */
    @Override
    public String getRenderVaule(Object value) {
        if(value instanceof Long){
            return DateFormatter.getLongDate((Long)value);
        }else if(value instanceof Date){
            return DateFormatter.getLongDate((Date)value);
        }
        return null;
    }

}
