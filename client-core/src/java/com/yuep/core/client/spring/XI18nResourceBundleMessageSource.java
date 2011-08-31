/*
 * $Id: XI18nResourceBundleMessageSource.java, 2009-12-1 上午09:08:03 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.spring;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.context.support.ResourceBundleMessageSource;

import com.yuep.base.resource.FileLoader;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: XI18nResourceBundleMessageSource
 * </p>
 * <p>
 * Description:对Spring的ResourceBundleMessageSource进行控制，使其可以根据部署的模块目录进行国际化文件的加载
 * </p>
 * 
 * @author aaron lee
 * created 2009-12-1 上午09:08:03
 * modified [who date description]
 * check [who date description]
 */
public class XI18nResourceBundleMessageSource extends ResourceBundleMessageSource {

    public XI18nResourceBundleMessageSource() {
    }

    /**
     * 
     * (non-Javadoc)
     * 
     * @see org.springframework.context.support.ResourceBundleMessageSource#
     *      doGetBundle(java.lang.String, java.util.Locale)
     */
    protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
        return ResourceBundle.getBundle(basename, locale, getBundleClassLoader());
    }

    /**
     * 国际化文件名
     */
    private List<String> i18nnames;

    public void setI18nnames(List<String> names) throws URISyntaxException {
        if(names==null)
            return;
        
        i18nnames=new ArrayList<String>();
        for(String name : names)
            i18nnames.add(replaceCoreModuleName(name));
    }
    
    /**
     * 如果里面配置的是硬编码的module/core，需要改为module/ourcorename/
     * @param name
     * @return
     */
    private String replaceCoreModuleName(String name){
        String pattern=null;
        int pos=name.indexOf("modules/core/");
        if(pos!=-1){
            pattern="modules/core/";
        }else{
            pos=name.indexOf("modules/launch/"); 
            if(pos!=-1)
                pattern="modules/launch/";
        }
        
        if(pos!=-1){
            // may be the core module's name is not 'core'
            // avoid hard code
            String oldCoreModuleName=ModuleContext.getInstance().getCoreModule().getModuleName();
            String coreModuleName = name.replace(pattern, "modules/"+oldCoreModuleName+"/");
            return coreModuleName;
        }
        
        return name; //the raw value
    }

    /**
     * 扩展接口，根据模块信息，对部署目录下的国际化文件进行加载
     * @param moduleNames 模块名列表
     */
    public void loadI18nFiles(List<String> moduleNames) {
        for (String moduleName : moduleNames) {
            String path = new StringBuilder(FileLoader.ROOT_DIR).append(moduleName).append("/").append(FileLoader.CONF_DIR)
                    .append(FileLoader.I18N_DIR).toString();
            URL url = ClientCoreContext.getResourceFactory().getResource(
                    path);
            Set<String> fileNameSet = new HashSet<String>();
            try {
                File file = new File(url.toURI());
                String[] list = file.list();
                for (String string : list) {
                    if (!string.endsWith("properties"))
                        continue;
                    int enIndex = string.indexOf("_en.properties");
                    int zhIndex = string.indexOf("_zh.properties");
                    if (enIndex != -1) {
                        String substring = string.substring(0, enIndex);
                        fileNameSet.add(path + substring);
                    } else if (zhIndex != -1) {
                        String substring = string.substring(0, zhIndex);
                        fileNameSet.add(path + substring);
                    } else {
                        logger.error(string + " is valid i18n file!");
                    }
                }
            } catch (Exception e) {
                System.err.println("read i18n "+moduleName+" warning,will ignored");
            }

            if (i18nnames == null) {
                i18nnames = new ArrayList<String>();
                for (String basename : fileNameSet) {
                    if (!i18nnames.contains(basename)) {
                        i18nnames.add(basename);
                    }
                }
            } else {
                for (String basename : fileNameSet) {
                    if (!i18nnames.contains(basename)) {
                        i18nnames.add(basename);
                    }
                }
            }
        }
        super.setBasenames(this.i18nnames.toArray(new String[] {}));
    }
}
