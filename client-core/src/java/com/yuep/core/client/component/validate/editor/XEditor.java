/*
 * $Id: XEditor.java, 2009-3-12 ����10:31:15 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.editor;

import java.beans.PropertyChangeListener;

import com.yuep.core.client.component.validate.validator.Validator;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: Editor
 * </p>
 * <p>
 * Description:�Զ�У��ؼ��Ľӿ�
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-12 ����10:31:15
 * modified [who date description]
 * check [who date description]
 */
public interface XEditor {

    /**
     * ���ؿؼ��ı���У����
     * @return ValidateMessage ����У����
     */
    ValidateMessage initValidateRequire();

    /**
     * ���ؿؼ����Զ�У����
     * @return Validator �ؼ����Զ�У����
     */
    Validator getValidator();

    /**
     * ���ؿؼ���������
     * @return String �ؼ���������
     */
    String getPropertyName();
    
    void setPropertyName(String propertyName);

    /**
     * �ؼ�����������Ƿ�����ύ
     * @return boolean �Ƿ���<code>true</code>�����򷵻�<code>false</code>
     */
    boolean isCommited();

    /**
     * �ؼ��Ƿ񾭹��޸�
     * @return boolean �Ƿ���<code>true</code>�����򷵻�<code>false</code>
     */
    boolean isModified();

    /**
     * ������Ա仯�ļ�����
     * @param propertyName ������
     * @param listener ������
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

    /**
     * ɾ�����Ա仯�ļ�����
     * @param propertyName ������
     * @param listener ������
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
    
    /**
     * �������Ա仯�¼�����Ҫ�Ƿ��Ϳؼ���������Ϣ
     * @param propertyName ������
     * @param newValue У������Ϣ
     */
    void propertyChange(String propertyName, Object newValue);

    /**
     * �����Ƿ��ʼ�����ÿؼ�
     * @param isInitialized �Ƿ��ʼ��
     */
    void setInitialized(boolean isInitialized);
    
    /**
     * ���ÿؼ���������Ϣ
     * @param description ������Ϣ
     */
    void setDescription(String description);
    
    /**
     * ���ÿؼ�����߿�
     */
    void setErrorBorder();
    
    /**
     * ����ؼ��Ĵ���߿�
     */
    void clearErrorBorder();
}
