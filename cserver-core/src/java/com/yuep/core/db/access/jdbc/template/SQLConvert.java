/*
 * $Id: SQLConvert.java, 2011-3-31 上午11:01:50 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.jdbc.template;

import java.util.List;

/**
 * <p>
 * Title: SQLConvert
 * </p>
 * <p>
 * Description:
 * 根据特定参数转化可执行的SQL语句
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 上午11:01:50
 * modified [who date description]
 * check [who date description]
 */
public interface SQLConvert<T> {

    /**
     * 转化为可执行的SQL语句
     * @param objects
     * @return
     *        SQL语句
     */
    public List<String> convertToSQL(List<T> objects);
}
