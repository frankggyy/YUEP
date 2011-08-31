/*
 * $Id: SampleWizardView.java, 2010-3-31 上午10:23:40 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.wizard.view;

import com.yuep.core.client.component.wizard.ContainerWizardView;

/**
 * <p>
 * Title: SampleWizardView
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 上午10:23:40
 * modified [who date description]
 * check [who date description]
 */
public class SampleWizardView extends ContainerWizardView<Object>{

    private static final long serialVersionUID = 7562347910236017982L;

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.wizard.ContainerWizardView#getTitle()
     */
    @Override
    public String getTitle() {
        return "向导控件的例子";
    }

}
