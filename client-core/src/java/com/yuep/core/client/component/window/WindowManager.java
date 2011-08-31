/*
 * $Id: WindowManager.java, 2009-3-2 ����10:04:51 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.window;

import java.awt.Window;

import com.yuep.core.client.mvc.AbstractClientModel;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;

/**
 * <p>
 * Title: WindowManager
 * </p>
 * <p>
 * Description: �ͻ��˴��ڹ�����
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-2 ����10:04:51
 * modified [who date description]
 * check [who date description]
 */
public interface WindowManager {
    
    /**
     * �򿪴���
     * @param <T>
     * @param controller
     * @param title
     */
    <T extends Object> void openAsFrame(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,Object... titles);

    /**
     * �򿪴���
     * @param <T>
     * @param controller
     * @param title
     */
    <T extends Object> void openAsDialog(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,Object... titles);
    
    /**
     * �򿪴���
     * @param <T>
     * @param controller
     * @param isResizeable
     * @param titles
     */
    public <T> void openAsDialog(
            ClientController<T, ? extends AbstractClientView<T>, ? extends AbstractClientModel<T>> controller,
            boolean resizable, Object... titles);

    /**
     * ��õ�ǰ���򿪵Ĵ��ڣ�������������
     * 
     * @return ���б��򿪵Ĵ��ڣ�ʹ��map�洢��keyΪҳ���helpId��valueΪ���򿪵�ҳ��
     */
    Window getCurrentOpenWindow(String id);
    
}
