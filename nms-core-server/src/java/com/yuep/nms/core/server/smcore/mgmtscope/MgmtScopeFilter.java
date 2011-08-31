/*
 * $Id: MgmtScopeFilter.java, 2011-3-24 下午01:33:51 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.smcore.mgmtscope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.yuep.core.container.def.CommunicateObject;
import com.yuep.core.facade.def.AbstractFacadeProcessor;
import com.yuep.core.facade.def.FacadeResponse;
import com.yuep.core.message.def.MessageReceiver;
import com.yuep.core.session.def.Session;
import com.yuep.core.session.def.SessionService;
import com.yuep.nms.core.common.base.def.ScopeFilter;
import com.yuep.nms.core.common.base.def.annotation.FacadeMethod;
import com.yuep.nms.core.common.mocore.message.MoMessage;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.naming.MoNamingGetter;
import com.yuep.nms.core.server.smcore.SmCoreContext;

/**
 * <p>
 * Title: MgmtScopeFilter
 * </p>
 * <p>
 * Description: 管理范围过滤
 * </p>
 * 
 * @author sufeng
 * created 2011-3-24 下午01:33:51
 */
public class MgmtScopeFilter extends AbstractFacadeProcessor {

    private Map<String,Set<MoNaming>> userScope=new ConcurrentHashMap<String,Set<MoNaming>>();
    private SessionService sessionService;
    
    public void init(){
        sessionService=SmCoreContext.getLocalService("sessionService", SessionService.class);
        addMoChangedListener();
    }
    
    public FacadeResponse afterInvoke(FacadeResponse lastResponse, Class<?> serviceClz, String methodName, Class<?>[] paramType, Object[] args) {
        if(lastResponse.getReturnValue()==null || lastResponse.getThrowedException()!=null)
            return lastResponse;
        
        FacadeMethod[] anns = SmCoreContext.getFacadeMethods(serviceClz,methodName,paramType);
        // 过滤掉不需要过滤管理范围的情况
        if(anns==null || anns.length==0)
            return lastResponse;
        FacadeMethod ann=anns[0];
        if(!ann.scopeFilter())
            return lastResponse;
        
        doFilter(lastResponse);
        return lastResponse;
    }

    /**
     * 过滤
     * @param lastResponse
     */
    private void doFilter(FacadeResponse lastResponse){
        String userName=getUserName();
        if(userName==null){
            System.err.println("have no user name");
            return;
        }
        
        Set<MoNaming> mos = userScope.get(userName);
        Object obj = lastResponse.getReturnValue();
        if(obj instanceof ScopeFilter){
            objectFilter((ScopeFilter)obj,mos);
        }else if(obj instanceof Collection){
            collectionFilter((Collection<?>)obj,mos);
        }else if(obj instanceof Object[]){
            Object[] newObjs = arrayFilter((Object[])obj, mos);
            lastResponse.setReturnValue(newObjs);
        }else{
            System.out.println("can't do mgmt scope filter");
        }
    }
    
    /**
     * @param filter
     * @param mos
     * @return 是否在管理范围内
     */
    private boolean objectFilter(ScopeFilter filter,Set<MoNaming> mos){
        if(filter.isSelfFilter()){
            return filter.isInScope(mos);
        }
        return true;
    }
    
    private void collectionFilter(Collection<?> elements,Set<MoNaming> mos){
        for(Iterator<?> it=elements.iterator();it.hasNext();){
            Object obj = it.next();
            if(obj instanceof ScopeFilter){
                boolean inMyScope=objectFilter((ScopeFilter)obj,mos);
                if(!inMyScope)
                    it.remove();
            }else if(obj instanceof MoNaming){
                if(!moInScope((MoNaming)obj, mos))
                    it.remove();
            }else if(obj instanceof MoNamingGetter){
                MoNaming managedMo=((MoNamingGetter)obj).getMoNaming();
                if(!moInScope(managedMo, mos))
                    it.remove();
            }
        }
    }
    
    private Object[] arrayFilter(Object[] elements,Set<MoNaming> mos){
        if(elements.length==0)
            return elements;
        
        List<Object> result=new ArrayList<Object>();
        for(int i=0; i<elements.length; i++){
            if(elements[i] instanceof ScopeFilter){
                boolean inMyScope=objectFilter((ScopeFilter)elements[i],mos);
                if(inMyScope)
                    result.add(elements[i]);
            }else if(elements[i] instanceof MoNaming){
                if(moInScope((MoNaming)elements[i], mos))
                    result.add(elements[i]);
            }else if(elements[i] instanceof MoNamingGetter){
                MoNaming managedMo=((MoNamingGetter)elements[i]).getMoNaming();
                if(moInScope(managedMo, mos))
                    result.add(elements[i]);
            }else{
                result.add(elements[i]);
            }
        }
        return result.toArray();
    }
    
    private String getUserName(){
        Session session = sessionService.getSession();
        return session==null ? null : session.getOwner(); 
    }
    
    public void setUserScope(String userName,Set<MoNaming> moNamings){
        if(moNamings==null)
            moNamings=new HashSet<MoNaming>();
        userScope.put(userName,moNamings);
    }
    
    public Set<MoNaming> getUserMgmtScope(String userName){
        return userScope.get(userName);
    }
    
    public void removeUserMgmtScope(String userName){
        userScope.remove(userName);
    }
    
    
    private boolean moInScope(MoNaming mo,Set<MoNaming> scope){
        MoNaming managedMo=mo;
        boolean unmanaged = SmCoreContext.isUnmanagedMo(mo);
        if(unmanaged)
            managedMo=mo.getParent();
        return scope.contains(managedMo);
    }
    
    /**
     * 监听mo变化的消息,mo的增加，删除时也需要把user的管理范围进行变化
     */
    private void addMoChangedListener(){
        SmCoreContext.subscribeLocalMessage(MoMessage.MO_CREATE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreContext.isUnmanagedMo(moNaming)) //板卡,端口等不可管理的mo直接过滤掉
                    return;
                MoNaming parent = mo.getParent();
                // 更新每个用户的管理范围
                for(Map.Entry<String,Set<MoNaming>> entry : userScope.entrySet()){
                    Set<MoNaming> moNamings=entry.getValue();
                    if(moNamings.contains(parent)){
                        moNamings.add(moNaming);
                    }
                }
            }
        });
        
        SmCoreContext.subscribeLocalMessage(MoMessage.MO_DELETE, new MessageReceiver(){
            @Override
            public void receive(CommunicateObject co, String name, Serializable msg) {
                MoMessage moMsg=(MoMessage)msg;
                Mo mo = (Mo)moMsg.getMessageBody();
                MoNaming moNaming = mo.getMoNaming();
                if(SmCoreContext.isUnmanagedMo(moNaming)) //板卡,端口等不可管理的mo直接过滤掉
                    return;
                // 更新每个用户的管理范围
                for(Map.Entry<String,Set<MoNaming>> entry : userScope.entrySet()){
                    Set<MoNaming> moNamings=entry.getValue();
                    moNamings.remove(moNaming);
                }
            }
        });
    }
    
}
