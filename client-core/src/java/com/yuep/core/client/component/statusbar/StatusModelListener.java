/*
 * $Id: StatusModelListener.java, 2009-3-5 ионГ09:19:27 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.statusbar;

import java.util.EventListener;

/**
 * <p>
 * Title: StatusModelListener
 * </p>
 * <p>
 * Description:Status model listener
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-5 ионГ09:19:27
 * modified [who date description]
 * check [who date description]
 */
public interface StatusModelListener extends EventListener {
    /**
     * This method will be invoked if statusModel was changed.
     * @param event StatusChangeEvent
     */
    public void statusChanged(StatusChangeEvent event);
}