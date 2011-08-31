/*
 * $Id: NodeFilter.java, 2011-3-24 下午03:34:47 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.tree;

import twaver.Element;

/**
 * <p>
 * Title: NodeFilter
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron
 * created 2011-3-24 下午03:34:47
 * modified [who date description]
 * check [who date description]
 */
public interface XNodeFilter {
    /**
     * 是否显示节点的过滤接口
     * @param node
     * @return 如果显示该节点返回true，否则返回false
     */
    boolean filter(Element node);
}
