/*
 * $Id: ObjectConvert.java, 2011-3-31 上午11:04:47 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.jdbc.template;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 * Title: ObjectConvert
 * </p>
 * <p>
 * Description:
 * 将数据库查询结果转化为对象
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 上午11:04:47
 * modified [who date description]
 * check [who date description]
 */
public interface ObjectConvert<T> {
    /**
     * ResultSet转化为对象
     * @param <T>
     * @param resultSet
     * @return
     *       对象集合
     */
    public  List<T> converToObject(ResultSet resultSet);

}
