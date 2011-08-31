/*
 * $Id: TreeConstants.java, 2011-3-2 下午01:56:00 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.tree;

/**
 * <p>
 * Title: TreeConstants
 * </p>
 * <p>
 * Description:YUEP Tree控件的常量
 * </p>
 * 
 * @author aaron
 * created 2011-3-2 下午01:56:00
 * modified [who date description]
 * check [who date description]
 */
public class TreeConstants {

    /**
     * 默认状态（所有节点都会变为非CheckBox方式显示）
     */
    public static final int DEFAULT_SELECTION = 0;
    /**
     * 单选（只对当前操作节点生效）
     */
    public static final int CHECK_SELECTION = 1;
    /**
     * 父到子递归（对当前选择节点和子节点生效，选中子节点父节点不会被选中，子节点全部取消选中父节点依然选选中）
     */
    public static final int CHECK_CHILDREN_SELECTION = 2;
    /**
     * 递归（对当前选择节点、子节点、父节点都生效，选中子节点父节点不会被选中，子节点全部取消选中父节点依然选选中）
     */
    public static final int CHECK_DESCENDANT_ANCESTOR_SELECTION = 4;
}
