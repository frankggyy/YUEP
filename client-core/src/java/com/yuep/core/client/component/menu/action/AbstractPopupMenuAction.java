/*
 * $Id: AbstractPopupMenuAction.java, 2011-2-15 ����10:40:30 aaron Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.menu.action;

/**
 * <p>
 * Title: AbstractPopupMenuAction
 * </p>
 * <p>
 * Description: �����Ҽ��˵���Action
 * </p>
 * 
 * @author aaron
 * created 2011-2-15 ����10:40:30
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractPopupMenuAction extends DefaultPopupMenuAction{

    private static final long serialVersionUID = -8667121603487445516L;

    /**
     * ���췽��
     * @param isMultiple �Ƿ�֧�ֶ�ѡ
     * @param actionId Action��id
     * @param selectedObjects ѡ�еĶ���
     * @param paramObj action����Ĳ���
     * @param paramClazz ����������
     */
    public AbstractPopupMenuAction(Boolean isMultiple, String actionId, Object[] selectedObjects, Object paramObj,
            String paramClazz) {
        super(isMultiple, actionId, selectedObjects, paramObj, paramClazz);
    }

}
