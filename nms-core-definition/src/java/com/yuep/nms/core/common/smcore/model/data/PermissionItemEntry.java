/*
 * $Id: PermissionItemEntry.java, 2011-4-14 下午03:02:04 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.smcore.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Title: PermissionItemEntry
 * </p>
 * <p>
 * Description:权限项(用来做castor map)
 * </p>
 * 
 * @author sufeng
 * created 2011-4-14 下午03:02:04
 * modified [who date description]
 * check [who date description]
 */
public class PermissionItemEntry implements Serializable {

    private static final long serialVersionUID = -6372517562081110666L;
    
    /**
     * 权限项id
     */
    private String permissionId;
    
    /**
     * 该权限包含的action或者function list，用,隔开
     */
    private String actionFunctionsExpress;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getActionFunctionsExpress() {
        return actionFunctionsExpress;
    }

    public void setActionFunctionsExpress(String actionFunctionsExpress) {
        this.actionFunctionsExpress = actionFunctionsExpress;
    }

    public static List<String> string2List(String express) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isEmpty(express))
            return list;

        String[] fields = express.split(",");
        if (fields == null || fields.length == 0)
            return list;
        for (int i = 0; i < fields.length; i++) {
            if (StringUtils.isEmpty(fields[i]))
                continue;
            list.add(fields[i].trim());
        }
        return list;
    }

    public static String list2String(List<String> list) {
        if (CollectionUtils.isEmpty(list)) 
            return null;
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String field : list) {
            if (first) {
                sb.append(field);
                first = false;
            } else {
                sb.append(",").append(field);
            }
        }
        return sb.toString();
    }
    
}
