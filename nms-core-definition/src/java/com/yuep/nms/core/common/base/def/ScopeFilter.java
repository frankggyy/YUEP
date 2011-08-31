/*
 * $Id: ScopeFilter.java, 2011-3-29 ����09:24:50 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.base.def;

import java.util.Set;

import com.yuep.nms.core.common.mocore.naming.MoNaming;


/**
 * <p>
 * Title: ScopeFilter
 * </p>
 * <p>
 * Description: ����Χ����
 * </p>
 * 
 * @author sufeng
 * created 2011-3-29 ����09:24:50
 * modified [who date description]
 * check [who date description]
 */
public interface ScopeFilter {

    /**
     * �Թ���Χ���й���
     * @param moNamings
     * @return �Ƿ��ڹ���Χ�� true�ڹ���Χ�� false���ڹ���Χ��
     */
    public boolean isInScope(Set<MoNaming> moNamings);
    
    /**
     * �Ƿ�����Լ����й��ˣ�����ʹ�ù����ࣨ���缯���У����й���
     * @return true�Լ����� false����������й���
     */
    public boolean isSelfFilter();
    
}
