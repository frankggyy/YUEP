/*
 * $Id: ModulePriority.java, 2010-9-27 上午09:29:08 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.bootstrap.def.module;

import java.util.List;

/**
 * <p>
 * Title: ModulePriority
 * </p>
 * <p>
 * Description: 模块的优先级
 * </p>
 * 
 * @author sufeng
 * created 2010-9-27 上午09:29:08
 * modified [who date description]
 * check [who date description]
 */
public interface ModulePriority {

    /**
     * 按启动顺序排列的module
     * @return
     */
    public List<Module> getStartOrder();
    
    /**
     * 关闭时或卸载时的顺序
     * @return
     */
    public List<Module> getShutdownOrder();

}
