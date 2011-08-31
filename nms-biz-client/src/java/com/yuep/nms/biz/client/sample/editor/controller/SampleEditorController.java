/*
 * $Id: SampleEditorController.java, 2010-3-31 обнГ03:47:30 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.biz.client.sample.editor.controller;

import com.yuep.core.client.mvc.AbstractClientController;
import com.yuep.nms.biz.client.sample.editor.model.SampleEditorData;
import com.yuep.nms.biz.client.sample.editor.model.SampleEditorModel;
import com.yuep.nms.biz.client.sample.editor.view.SampleEditorView;

/**
 * <p>
 * Title: SampleEditorController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-31 обнГ03:47:30
 * modified [who date description]
 * check [who date description]
 */
public class SampleEditorController extends AbstractClientController<SampleEditorData, SampleEditorView, SampleEditorModel>{

    /**
     * @param viewClass
     * @param modelClass
     */
    public SampleEditorController(Class<SampleEditorView> viewClass, Class<SampleEditorModel> modelClass) {
        super(viewClass, modelClass);
    }

}
