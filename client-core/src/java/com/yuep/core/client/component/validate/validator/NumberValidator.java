/*
 * $Id: NumberValidator.java, 2009-3-9 上午10:58:45 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: NumberValidator
 * </p>
 * <p>
 * Description:数字的自动校验器
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-9 上午10:58:45
 * modified [who date description]
 * check [who date description]
 */
public class NumberValidator extends AbstractValidator {

    /**
     * Constructor
     * @param range 校验范围
     */
    public NumberValidator(ValidatorRange range) {
        super(range);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.validator.Validator#validate(java.lang.String)
     */
    public ValidateMessage validate(String value) {
        String regx = "^-?[0-9]*[0-9][0-9]*$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(value);
        boolean matches = matcher.matches();
        if (!StringUtils.isEmpty(value)) {
            validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
            String message = ClientCoreContext.getString("common.validate.numeber.message");
            validateMessage.setMsg(message);
            if (!matches) {
                return validateMessage;
            } else {
                int max = range.getMax();
                int min = range.getMin();

                int inputInt = Integer.parseInt(value);
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                String errorMessage = ClientCoreContext.getString("common.validate.numeber.range.message", min,
                        max);
                validateMessage.setMsg(errorMessage);
                if (inputInt < min) {
                    return validateMessage;
                } else if (inputInt > max) {
                    return validateMessage;
                }
            }
        }
        validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
        validateMessage.setMsg("");
        return validateMessage;
    }

}
