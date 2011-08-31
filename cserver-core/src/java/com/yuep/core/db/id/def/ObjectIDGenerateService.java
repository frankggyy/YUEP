/*
 * $Id: ObjectIDGenerateService.java, 2011-3-29 ����12:31:58 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.id.def;

/**
 * <p>
 * Title: ObjectIDGenerateService
 * </p>
 * <p>
 * Description:
 * ����ΨһID������
 * </p>
 * 
 * @author yangtao
 * created 2011-3-29 ����12:31:58
 * modified [who date description]
 * check [who date description]
 */
public interface ObjectIDGenerateService {
    /**
     * ����objectType����һ��Ψһ��ID
     * @param objectType
     *        
     * @return
     */
    public Long generateObjectId(String objectType);
}
