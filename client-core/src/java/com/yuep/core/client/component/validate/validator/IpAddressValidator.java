/*
 * $Id: IpAddressValidator.java, 2009-3-16 下午08:50:29 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.validate.validator;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: IpAddressValidator
 * </p>
 * <p>
 * Description:IP地址自动校验器
 * </p>
 * 
 * @author aaron lee
 * created 2009-3-16 下午08:50:29
 * modified [who date description]
 * check [who date description]
 */
public class IpAddressValidator extends AbstractValidator {

    /**
     * Constructor
     * @param range 校验范围
     */
    public IpAddressValidator(ValidatorRange range) {
        super(range);
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.component.validate.validator.Validator#validate(java.lang.String)
     */
    @Override
    public ValidateMessage validate(String value) {
        ValidateMessage message = new ValidateMessage();
        if (!StringUtils.isEmpty(value)) {
            int max = range.getMax();
            int min = range.getMin();
            String tmp = value.trim();
            if (Integer.parseInt(tmp) > max) {
                
                message.setMessageType(ValidateMessage.MessageType.ERROR);
                String msg = ClientCoreContext.getString("common.validate.ip.message",min,max);
                message.setMsg(msg);
                return message;
            }else{
                message.setMessageType(ValidateMessage.MessageType.CLEAN);
                message.setMsg("");
            }
        }
        return message;
    }

}
