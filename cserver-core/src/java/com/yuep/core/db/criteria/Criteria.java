/*
 * $Id: Criteria.java, 2011-3-25 ����04:35:31 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.criteria;

/**
 * <p>
 * Title: Criteria
 * </p>
 * <p>
 * Description: ��ѯ������
 * </p>
 * 
 * @author sufeng
 * created 2011-3-25 ����04:35:31
 * modified [who date description]
 * check [who date description]
 */
public interface Criteria {

    /**
     * criteriaת��Ϊsql
     * @return
     */
    public String toSql();
    
}
