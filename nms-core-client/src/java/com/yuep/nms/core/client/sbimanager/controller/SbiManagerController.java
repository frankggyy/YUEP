/*
 * $Id: SbiManagerController.java, 2011-4-20 ����09:19:17 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.controller;

import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.nms.core.client.sbimanager.model.SbiManagerModel;
import com.yuep.nms.core.client.sbimanager.view.SbiManagerView;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;

/**
 * <p>
 * Title: SbiManagerController
 * </p>
 * <p>
 * Description:sbi����
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 ����09:19:17
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerController extends
        AbstractClientController<SbiProperty, SbiManagerView, SbiManagerModel> {

    public SbiManagerController(Class<SbiManagerView> viewClass,Class<SbiManagerModel> modelClass) {
        super(viewClass, modelClass);
    }
    /**
     * ���SbiProperty
     * @param sbiProperties
     */
    public void addSbiProperties(SbiProperty... sbiProperties){
        this.getClientView().addSbiProperties(sbiProperties);
    }
    /**
     * ɾ��SbiProperty
     * @param sbiProperty
     */
    public void deleteSbiProperty(SbiProperty sbiProperty){
        this.getClientView().deleteSbiProperty(sbiProperty);
    }
    /**
     * ��ȡ��ѡ���SbiProperty
     * @return
     */
    public SbiProperty getSelectedSbiProperty(){
        return this.getClientView().getSelectedSbiProperty();
    }

    /**
     * ��ȡ��ѡ�������Mo����
     * @return
     */
    public Mo getSelectedNm(){
        return this.getClientView().getSelectedNm();
    }
    /**
     * ���ñ�ѡ�������Mo����
     * @param nm
     */
    public void setSelectedNm(Mo nm){
         this.getClientView().setSelectedNm(nm);
    }
}
