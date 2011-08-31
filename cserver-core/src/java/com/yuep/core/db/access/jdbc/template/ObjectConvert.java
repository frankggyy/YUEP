/*
 * $Id: ObjectConvert.java, 2011-3-31 ����11:04:47 yangtao Exp $
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
 * �����ݿ��ѯ���ת��Ϊ����
 * </p>
 * 
 * @author yangtao
 * created 2011-3-31 ����11:04:47
 * modified [who date description]
 * check [who date description]
 */
public interface ObjectConvert<T> {
    /**
     * ResultSetת��Ϊ����
     * @param <T>
     * @param resultSet
     * @return
     *       ���󼯺�
     */
    public  List<T> converToObject(ResultSet resultSet);

}
