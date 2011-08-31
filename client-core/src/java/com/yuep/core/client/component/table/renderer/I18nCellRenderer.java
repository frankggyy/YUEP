/*
 * $Id: I18nCellRenderer.java, 2011-4-26 上午09:42:27 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table.renderer;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: I18nCellRenderer
 * </p>
 * <p>
 * Description:表格国际化字符串Renderer
 * </p>
 * 
 * @author sufeng
 * created 2011-4-26 上午09:42:27
 * modified [who date description]
 * check [who date description]
 */
public class I18nCellRenderer extends XTableCellRenderer {

    private static final long serialVersionUID = 5314791815353782470L;

    @Override
    public String getRenderVaule(Object value) {
        if(value==null)
            return null;
        String key=value.toString();
        return ClientCoreContext.getString(key);
    }

}
