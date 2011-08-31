/*
 * $Id: TopoClientModule.java, 2011-4-15 обнГ04:25:48 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.topo;

import com.yuep.core.client.module.ClientModule;

/**
 * <p>
 * Title: TopoClientModule
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author sufeng
 * created 2011-4-15 обнГ04:25:48
 * modified [who date description]
 * check [who date description]
 */
public class TopoClientModule extends ClientModule{

    @Override
    public void start() {
        initMenuInterpreter();
        getMenuInterpreter().loadMenuFiles("topo-menu-data.xml");
    }

    @Override
    public void stop() {
    
    }

}
