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

/**
 * 
 * 表格cell的render显示转换接口
 * @author jimsu
 *
 */
public interface GetRenderVaule {

    /**
     * 把model对象转换为显示的字符串
     * @param value
     * @return
     */
	public String getRenderVaule(Object value);
	
}
