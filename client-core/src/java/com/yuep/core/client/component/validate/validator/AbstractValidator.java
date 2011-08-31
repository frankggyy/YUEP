/*
 * $Id: AbstractValidator.java, 2009-3-9 上午10:59:01 aaron lee Exp $
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
 * Description:自动校验器接口的抽象实现类
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 上午10:59:01
 * modified [who date description]
 * check [who date description]
 */
public abstract class AbstractValidator implements Validator {

    /**
     * 校验范围
     */
    protected ValidatorRange range;

    /**
     * 校验结果信息
     */
    protected ValidateMessage validateMessage;

    /**
     * Constructor
     * @param range 校验范围
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
     * 返回校验范围
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
