/*
 * $Id: ItemFilter.java, 2009-6-11 ����10:43:46 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;
/**
 * <p>
 * Title: ItemFilter
 * </p>
 * <p>
 * Description:ö�����Զ�У��ؼ��Ĺ������ӿ�
 * </p>
 * 
 * @author Yao
 * created 2009-6-11 ����10:43:46
 * modified [who date description]
 * check [who date description]
 */
public interface ItemFilter {
    /**
     * ����Ҫ���˵�ö��ֵ
     * @return ö��ֵ������
     */
    Enum<?>[] filterEnums();
}
