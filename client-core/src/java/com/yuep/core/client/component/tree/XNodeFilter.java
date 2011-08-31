/*
 * $Id: NodeFilter.java, 2011-3-24 ����03:34:47 aaron Exp $
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
 * created 2011-3-24 ����03:34:47
 * modified [who date description]
 * check [who date description]
 */
public interface XNodeFilter {
    /**
     * �Ƿ���ʾ�ڵ�Ĺ��˽ӿ�
     * @param node
     * @return �����ʾ�ýڵ㷵��true�����򷵻�false
     */
    boolean filter(Element node);
}
