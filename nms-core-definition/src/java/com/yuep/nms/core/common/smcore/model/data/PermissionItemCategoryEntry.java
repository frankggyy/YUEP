/*
 * $Id: PermissionItemCategoryEntry.java, 2011-4-14 ����03:04:52 sufeng Exp $
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
import java.util.List;

/**
 * <p>
 * Title: PermissionItemCategoryEntry
 * </p>
 * <p>
 * Description: Ȩ�޴����ڰ�����Ȩ����(������castor map)
 * </p>
 * 
 * @author sufeng
 * created 2011-4-14 ����03:04:52
 * modified [who date description]
 * check [who date description]
 */
public class PermissionItemCategoryEntry implements Serializable {

    private static final long serialVersionUID = 3298571726132834530L;

    /**
     * Ȩ�������id
     */
    private String categoryId;
    
    /**
     * �ô����ڰ�����Ȩ����list
     */
    private List<PermissionItemEntry> items;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public List<PermissionItemEntry> getItems() {
        return items;
    }

    public void setItems(List<PermissionItemEntry> items) {
        this.items = items;
    }
    
    
    
}
