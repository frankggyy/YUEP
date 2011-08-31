/*
 * $Id: MoNamingListUserType, 2009-8-31 下午01:50:29 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.usertype;

import java.io.Serializable;

import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoNamingListUserType
 * </p>
 * <p>
 * Description:
 * CommonListUserType映射元素为MoNaming的UserType
 * </p>
 * 
 * @author yangtao
 * created 2009-8-31 下午01:50:29
 * modified [who date description]
 * check [who date description]
 */
public class MoNamingListUserType implements ListElementUserType<MoNaming>,Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7898157578930601246L;

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.usertype.ListElementUserType#toObject(java.lang.String, java.lang.Class)
     */
    @Override
    public MoNaming toObject(String str, Class<MoNaming> type) {
        return new MoNaming(str);
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.nms.core.common.mocore.usertype.ListElementUserType#toString(java.lang.Object, java.lang.Class)
     */
    @Override
    public String toString(MoNaming MoNaming, Class<MoNaming> type) {
        return MoNaming.toString();
    }

}
