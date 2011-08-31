/*
 * $Id: YotcResourceBundleMessageSource.java, 2009-3-5 ����04:38:48 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.spring;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * <p>
 * Title: YotcResourceBundleMessageSource
 * </p>
 * <p>
 * Description:��oem���ʻ���֧�֣������oem����Ҫ�ڶ�Ӧ���ʻ��ļ�Ŀ¼���½�һ����oem����һ����Ŀ¼��Ȼ��ѹ��ʻ��ļ�copy��ȥ�޸�
 * </p>
 * 
 * @author Victor
 * created 2009-3-5 ����04:38:48
 * modified [who date description]
 * check [who date description]
 */
public class XResourceBundleMessageSource extends ResourceBundleMessageSource {
    
    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        return ResourceBundle.getBundle(basename, locale, getBundleClassLoader());
    }

}
