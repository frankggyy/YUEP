/*
 * $Id: EnumCellRenderer.java, 2009-12-7 ����11:00:27 yangtao Exp $
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
 * ���cell��render��ʾת���ӿ�
 * @author jimsu
 *
 */
public interface GetRenderVaule {

    /**
     * ��model����ת��Ϊ��ʾ���ַ���
     * @param value
     * @return
     */
	public String getRenderVaule(Object value);
	
}
