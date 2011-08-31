/*
 * $Id: FacadeManagerImpl.java, 2011-1-31 上午09:09:47 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.facade.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.yuep.base.scheduler.SchedulerExecutor;
import com.yuep.core.container.impl.ContainerSchedulerExecutor;
import com.yuep.core.facade.def.FacadeManager;
import com.yuep.core.facade.def.FacadeProcessor;

/**
 * <p>
 * Title: FacadeManagerImpl
 * </p>
 * <p>
 * Description: facade manager实现类,包括注册,反注册,获取facade processor
 * </p>
 * 
 * @author sufeng
 * created 2011-1-31 上午09:09:47
 * modified [who date description]
 * check [who date description]
 */
public class FacadeManagerImpl implements FacadeManager {

    /**
     * 顺序的facade processor
     */
    private List<FacadeProcessor> processors=new LinkedList<FacadeProcessor>();
    /**
     * after processor以逆序来执行
     */
    private List<FacadeProcessor> afterSortProcessors=new LinkedList<FacadeProcessor>();
    
    public void init(){
        // 启动session检查的job
        SchedulerExecutor schedulerExecutor = ContainerSchedulerExecutor.getSchedulerExecutor();
        schedulerExecutor.startSchedulerJob(new CheckSessionJob(30));
    }

    @Override
    public void appendFacadeProcessor(FacadeProcessor processor) {
        processors.add(processor);
        resetAfterProcessor();
    }

    @Override
    public List<String> getAllFacadeProcessorNames() {
        List<String> names=new ArrayList<String>();
        for(FacadeProcessor bp : processors)
            names.add(bp.getName());
        return names;
    }

    @Override
    public List<FacadeProcessor> getFacadeProcessorByAfterOrder() {
        return afterSortProcessors;
    }

    @Override
    public List<FacadeProcessor> getFacadeProcessorByBeforeOrder() {
        return processors;
    }

    @Override
    public void insertFacadeProcessor(String previousProcessorName, FacadeProcessor processor) {
        int index=0;
        boolean inserted=false;
        for(FacadeProcessor p :processors){
            if(previousProcessorName.equals(p.getName())){
                // match and insert to next position
                processors.add(index+1, processor); 
                inserted=true;
                break;
            }
            index++;
        }
        
        if(!inserted)
            processors.add(processor);
        
        resetAfterProcessor();
    }

    @Override
    public void removeFacadeProcessor(String processorName) {
        Iterator<FacadeProcessor> it=processors.iterator();
        for(;it.hasNext();){
            FacadeProcessor p = it.next();
            if(processorName.equals(p.getName())){
                it.remove();
                break;
            }
        }
        
        resetAfterProcessor();
    }
    
    /**
     * 重新设置after processor  
     */
    private void resetAfterProcessor(){
        afterSortProcessors.clear();
        if(processors.size()==0)
            return;
        
        // 逆序排列
        int maxIndex=processors.size()-1;
        for(int i=maxIndex; i>=0; i--){
            afterSortProcessors.add(processors.get(i));
        }
    }
    
}
