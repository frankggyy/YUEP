/*
 * $Id: EnumCellRenderer.java, 2009-12-7 上午11:00:27 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table.renderer;

import com.yuep.core.client.ClientCoreContext;


/**
 * <p>
 * Title: EnumCellRenderer
 * </p>
 * <p>
 * Description:枚举型数据的TableCellRenderer
 * </p>
 * 
 * @author yangtao
 * created 2009-12-7 上午11:00:27
 * modified [who date description]
 * check [who date description]
 */
public class EnumCellRenderer extends XTableCellRenderer{
    /**
     * 
     */
    private static final long serialVersionUID = 6622042179307130637L;

    @Override
    public String getRenderVaule(Object value) {
        if(value==null)
            return "";
        return ClientCoreContext.getString(value.getClass().getSimpleName()+"."+value);
    }

}
