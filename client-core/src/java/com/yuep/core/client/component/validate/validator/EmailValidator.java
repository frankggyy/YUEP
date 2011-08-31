/*
 * $Id: EmailValidator.java, 2009-4-10 下午02:48:09 aaron lee Exp $
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
 * Title: EmailValidator
 * </p>
 * <p>
 * Description:邮件自动校验器
 * </p>
 * 
 * @author aaron lee
 * created 2009-4-10 下午02:48:09
 * modified [who date description]
 * check [who date description]
 */
public class EmailValidator extends AbstractValidator{

    /**
     * Constructor
     * @param range 校验范围
     */
    public EmailValidator(ValidatorRange range) {
        super(range);
    }
    
    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.validator.Validator#validate(java.lang.String)
     */
    public ValidateMessage validate(String value) {
        String regx = "^([\\w]+)(([-\\.][\\w]+)?)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(value);
        boolean matches = matcher.matches();
        if (!StringUtils.isEmpty(value)) {
            if (!matches) {
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                String message = ClientCoreContext.getString("common.email.verify.msg");
                validateMessage.setMsg(message);
                return validateMessage;
            } else {
                int max = range.getMax();
                int min = range.getMin();
                int inputInt = value.length();
                validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
                String errorMessage = ClientCoreContext.getString(ClientCoreContext.getString("common.email.verify.msg",
                        min, max));
                validateMessage.setMsg(errorMessage);
                if (inputInt < min) {
                    return validateMessage;
                } else if (inputInt > max) {
                    return validateMessage;
                }
            }
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
        }else{
            validateMessage.setMessageType("");
            validateMessage.setMsg("");
        }
        return validateMessage;
    }
}
