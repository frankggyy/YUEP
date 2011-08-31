/*
 * $Id: PermissionLoader.java, 2011-4-14 下午03:27:40 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.perm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.yuep.base.xml.ArrayListItems;
import com.yuep.base.xml.XmlFileReader;
import com.yuep.core.bootstrap.def.ContainerConst;
import com.yuep.core.bootstrap.def.ModuleContext;
import com.yuep.nms.core.common.smcore.model.Permission;
import com.yuep.nms.core.common.smcore.model.data.PermissionItemCategoryEntry;
import com.yuep.nms.core.common.smcore.model.data.PermissionItemEntry;
import com.yuep.nms.core.server.smcore.SmCoreModule;

/**
 * <p>
 * Title: PermissionLoader
 * </p>
 * <p>
 * Description: 从xml加载权限项
 * </p>
 * 
 * @author sufeng
 * created 2011-4-14 下午03:27:40
 * modified [who date description]
 * check [who date description]
 */
public class PermissionLoader {

    /**
     * 从xml加载权限项
     * @return 所有的权限项
     */
    @SuppressWarnings("unchecked")
    public List<Permission> readPermissionFromXml(){
        String homeDir = ModuleContext.getInstance().getSystemParam(ContainerConst.KEY_HOME_DIR);
        SmCoreModule module = ModuleContext.getInstance().getModule(SmCoreModule.class);
        String path=homeDir+File.separator+"modules"+File.separator+module.getModuleName()+File.separator+"conf"+File.separator;
        List<Permission> perms=new ArrayList<Permission>();
        Object obj = XmlFileReader.getXmlConfig(path, path, "action-permission");
        if(obj==null)
            return perms;
        ArrayListItems<PermissionItemCategoryEntry> items=(ArrayListItems<PermissionItemCategoryEntry>)obj;
        List<PermissionItemCategoryEntry> entries = items.getItems();
        if(entries==null || entries.size()==0)
            return perms;
        for(PermissionItemCategoryEntry category : entries){
            List<PermissionItemEntry> permItems = category.getItems();
            if(permItems==null || permItems.size()==0)
                continue;
            for(PermissionItemEntry permItem : permItems){
                List<String> actions = PermissionItemEntry.string2List(permItem.getActionFunctionsExpress());
                Permission perm=new Permission(category.getCategoryId(),permItem.getPermissionId(),actions);
                perms.add(perm);
            }
        }
        return perms;
    }
    
}
