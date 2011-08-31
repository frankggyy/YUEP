/*
 * $Id: AbstractValidator.java, 2009-3-9 ����10:59:01 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: AbstractValidator
 * </p>
 * <p>
 * Description:�Զ�У�����ӿڵĳ���ʵ����
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 ����10:59:01
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractValidator implements Validator {

    /**
     * У�鷶Χ
     */
    protected ValidatorRange range;

    /**
     * У������Ϣ
     */
    protected ValidateMessage validateMessage;

    /**
     * Constructor
     * @param range У�鷶Χ
     */
    public AbstractValidator(ValidatorRange range) {
        this.range = range;
        validateMessage = new ValidateMessage();
    }

    /**
     * Constructor
     */
    public AbstractValidator() {
        validateMessage = new ValidateMessage();
    }

    /**
     * ����У�鷶Χ
     * @return the range
     */
    public ValidatorRange getRange() {
        return range;
    }

    /**
     * @see com.yuep.core.client.component.validate.validator.Validator#setRange(com.yuep.core.client.component.validate.validator.ValidatorRange)
     */
    @Override
    public void setRange(ValidatorRange range) {
        this.range = range;
    }
}
