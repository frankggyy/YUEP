/*
 * $Id: MgmtScopeManager.java, 2011-3-24 下午01:38:59 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.mgmtscope;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.yuep.core.container.def.CoreContext;
import com.yuep.core.facade.def.FacadeManager;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MgmtScopeManager
 * </p>
 * <p>
 * Description:管理范围管理器
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:38:59
 */
public class MgmtScopeManager {

    private MgmtScopeFilter scopeFilter;
    
    public void init(){
        scopeFilter=new MgmtScopeFilter();
        scopeFilter.init();
        FacadeManager facadeManager = CoreContext.getInstance().local().getService("facadeManager", FacadeManager.class);
        facadeManager.appendFacadeProcessor(scopeFilter);
    }
    
    /**
     * 初始化用户的管理范围
     * @param userName
     * @param moNamings
     */
    public void setUserMgmtScope(String userName,Collection<MoNaming> moNamings){
        Set<MoNaming> moNamingSet=new HashSet<MoNaming>();
        if(moNamings!=null)
            moNamingSet.addAll(moNamings);
        //通知filter
        scopeFilter.setUserScope(userName, moNamingSet);
    }
    
    /**
     * 得到用户可管理的mo
     * @param userName
     * @return mo naming set
     */
    public Set<MoNaming> getUserMgmtScope(String userName){
        return scopeFilter.getUserMgmtScope(userName);
    }
    
    public void removeUserMgmtScope(String userName){
        scopeFilter.removeUserMgmtScope(userName);
    }
    
    /**
     * 是否在管理范围内
     * @param user
     * @param moNamings
     * @return
     */
    public boolean isInMgmtScope(String user, MoNaming... moNamings){
        Set<MoNaming> scope = getUserMgmtScope(user);
        if(scope==null)
            return false;
        for(MoNaming naming : moNamings){
            if(!scope.contains(naming))
                return false;
        }
        return true;
    }

}
