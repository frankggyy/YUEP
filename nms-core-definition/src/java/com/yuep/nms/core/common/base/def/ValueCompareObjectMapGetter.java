/*
 * $Id: ValueCompareObjectMapGetter.java, 2011-3-30 ����03:36:51 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

import java.util.Map;

/**
 * <p>
 * Title: ValueCompareObjectMapGetter
 * </p>
 * <p>
 * Description:��ȡһ���������Щ���Է����˱仯
 * </p>
 * 
 * @author sufeng
 * created 2011-3-30 ����03:36:51
 * modified [who date description]
 * check [who date description]
 */
public interface ValueCompareObjectMapGetter<T> {

    /**
     * �Ƚ��¾ɶ���仯�����ԣ�״ֵ̬��ֻ��Ҫ������Ҫ�����Ժ�״̬��
     * @param otherObject this��ö�����жԱ�
     * @return
     */
    public Map<String,ValueCompareObject> getValueCompareObjectMap(T otherObject);
    
}
