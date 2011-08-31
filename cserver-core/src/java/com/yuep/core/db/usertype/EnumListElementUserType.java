/*
 * $Id: EnumListElementUserType.java, 2009-2-25 上午11:18:28 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.usertype;

import java.io.Serializable;

/**
 * <p>
 * Title: EnumListElementUserType
 * </p>
 * <p>
 * Description: 
 *        List存放枚举类型，针对枚举类型的UserType
 * </p>
 * 
 * @author yangtao
 * created 2009-2-25 上午11:18:28
 * modified [who date description]
 * check [who date description]
 */
public class EnumListElementUserType implements ListElementUserType<Enum>,
        Serializable {

    private static final long serialVersionUID = -2238827846473642630L;

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.bo.userType.ListElementUserType#toObject(java.lang.String,
     *      java.lang.Class)
     */
    @Override
    public Enum toObject(String str, Class<Enum> type) {
        return Enum.valueOf(type, str);
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.bo.userType.ListElementUserType#toString(java.lang.Object,
     *      java.lang.Class)
     */
    @Override
    public String toString(Enum value, Class<Enum> type) {
        return ((Enum) value).name();
    }

}
