/*
 * $Id: MacAddressValidator.java, 2009-3-17 下午02:51:53 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: MacAddressValidator
 * </p>
 * <p>
 * Description:Mac地址自动校验器
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-17 下午02:51:53
 * modified [who date description]
 * check [who date description]
 */
public class MacAddressValidator extends AbstractValidator{

    /**
     * Constructor
     * @param range 校验范围
     */
    public MacAddressValidator(ValidatorRange range) {
        super(range);
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.component.validate.validator.Validator#validate(java.lang.String)
     */
    @Override
    public ValidateMessage validate(String value) {
        int maxLen = range.getMax();
        if (value == null || value.length() < 17) {
            validateMessage.setMessageType(ValidateMessage.MessageType.ERROR);
            String errorMsg = ClientCoreContext.getString("common.validate.mac.message",maxLen);
            validateMessage.setMsg(errorMsg);
            return validateMessage;
        }else{
            validateMessage.setMessageType(ValidateMessage.MessageType.CLEAN);
            validateMessage.setMsg("");
            return validateMessage;
        }
    }

}
