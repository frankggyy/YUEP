/*
 * $Id: DaoExecute.java, 2011-3-30 下午02:43:34 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.db.access.basedao;

import com.yuep.core.db.access.hibernate.template.HibernateTemplate;

/**
 * <p>
 * Title: DaoExecute
 * </p>
 * <p>
 * Description: 通用dao execute，在使用中可以使用匿名内部类来简化session的获取,关闭操作
 * </p>
 * 
 * @author sufeng
 * created 2011-3-30 下午02:43:34
 * modified [who date description]
 * check [who date description]
 */
public interface DaoExecute<T> {

    /**
     * 执行一个hibernate template
     * @param template
     * @return
     */
    public Object execute(HibernateTemplate<T> template);
    
}
