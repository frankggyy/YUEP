/*
 * $Id: AbstractClientController.java, 2009-2-12 上午10:40:48 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.mvc;

import java.awt.Window;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * <p>
 * Title: AbstractClientController
 * </p>
 * <p>
 * Description:抽象的客户端view类
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-12 上午10:40:48
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractClientView<T extends Object> extends JPanel implements ClientView<T> {
    private static final long serialVersionUID = 9080063142916975770L;

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getWindow()
     */
    @Override
    public Window getWindow() {
        return SwingUtilities.getWindowAncestor(this);
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#dispose()
     */
    @Override
    public void dispose() {
        getWindow().dispose();
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#addDatas(java.util.List)
     */
    @Override
    public void addDatas(List<T> datas) {
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#deleteDatas(java.util.List)
     */
    @Override
    public void deleteDatas(List<T> datas) {
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#modifyDatas(java.util.List)
     */
    @Override
    public void modifyDatas(List<T> datas) {
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#setViewParams(java.lang.Object[])
     */
    @Override
    public void setViewParams(Object... viewParams) {
    }
}
