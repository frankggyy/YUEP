/*
 * $Id: MoCache.java, 2011-3-28 下午03:00:46 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.common.mocore.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.naming.MoNaming;

/**
 * <p>
 * Title: MoCache
 * </p>
 * <p>
 * Description: Mo缓存
 * </p>
 * 
 * @author yangtao
 * created 2011-3-28 下午03:00:46
 * modified [who date description]
 * check [who date description]
 */
final public class MoCache {

    private final ConcurrentMap<MoNaming, MoNode> allMoNodes = new ConcurrentHashMap<MoNaming, MoNode>();
    
    public MoCache() {
        
    }

    /**
     * 向缓存中添加Mo
     * 
     * @param mos
     */
    public  void addMos(List<Mo> mos) {
        for (Mo mo : mos)
            addMo(mo);
    }

    /**
     * add mo
     * @param mo
     */
    private void addMo(Mo mo) {
        MoNode  parentMoNode=null;
        if(mo.getParent()!=null)
        	parentMoNode=allMoNodes.get(mo.getParent());  
        MoNode moNode=null;
        if(allMoNodes.containsKey(mo.getMoNaming())){
            moNode=allMoNodes.get(mo.getMoNaming());
            moNode.setMo(new Mo(mo));
            moNode.setParent(parentMoNode);
        }else{
            moNode=new MoNode();
            moNode.setMo(new Mo(mo));
            moNode.setParent(parentMoNode);
            allMoNodes.put(mo.getMoNaming(), moNode);
        }
        
        if(parentMoNode!=null)
            parentMoNode.addChild(moNode);
        
        
      
    }

    /**
     * 更新Mo
     * 
     * @param mos
     */
    public void updateMos(List<Mo> mos) {
      for(Mo mo:mos){
          MoNode moNode=allMoNodes.get(mo.getMoNaming());
          moNode.setMo(new Mo(mo));
      }
    }

    /**
     * 删除Mo
     * 
     * @param mos
     */
    public void deleteMos(List<Mo> mos) {
        for(Mo mo:mos){
            MoNode moNode=allMoNodes.get(mo.getMoNaming());
            MoNode parentMoNode=moNode.getParent();
            if(parentMoNode!=null)
                parentMoNode.removeChild(moNode);
            allMoNodes.remove(mo.getMoNaming());
        }
    }

    /**
     * 查询Mo
     * 
     * @param mo
     * @return
     */
    public Mo getMo(MoNaming mo) {
        MoNode moNode=allMoNodes.get(mo);
        if(moNode!=null&&moNode.getMo()!=null)
            return new Mo(moNode.getMo());
        return null;
    }

    
    public MoNode getMoNode(MoNaming mo){
        return allMoNodes.get(mo);
    }
    /**
     * 查询子节点Mo
     * 
     * @return
     */
    public List<Mo> getChildren(MoNaming... parents) {
        List<Mo> mos=new ArrayList<Mo>();
        for(MoNaming parent:parents){
            MoNode parentMoNode=allMoNodes.get(parent);
            if(parentMoNode==null)
                continue;
            List<MoNode> children=parentMoNode.getChildren();
            for(MoNode child:children){
                if(child.getMo()==null)
                    continue;
                mos.add(new Mo(child.getMo()));
            }
            for(MoNode child:children){
                List<Mo> childrenMos=getChildren(child.getMo().getMoNaming());
                if(CollectionUtils.isNotEmpty(childrenMos))
                    mos.addAll(childrenMos);
            }
        }
        return mos;
    }

    /**
     * 查询所有Mos
     * 
     * @return
     */
    public List<Mo> getAllMos() {
        List<Mo> mos=new ArrayList<Mo>();
        Mo rootMo=getRootMo();
        if(rootMo==null)
        	return mos;
        MoNode rootMoNode=this.getMoNode(rootMo.getMoNaming());
        mos.add(new Mo(rootMoNode.getMo()));
        addMoToList(mos,rootMoNode);
        return mos;
    }
    
    private void addMoToList(List<Mo> mos,MoNode moNode){
    	 for(MoNode child:moNode.getChildren()){
    		 if(child.getMo()!=null){
    			 mos.add(new Mo(child.getMo()));
    			 addMoToList(mos,child);
    		 }
        }
    }
    
    /**
     * 得到根mo
     * @return
     */
    public Mo getRootMo(){
        Mo root=null;
        for(Entry<MoNaming, MoNode> entry:allMoNodes.entrySet()){
            Mo mo=entry.getValue().getMo();
            if(mo==null)
            	continue;
            root=mo;
            while(root.getParent()!=null){
                MoNaming moNaming=root.getParent();
                if(moNaming==null||this.getMo(moNaming)==null)
                    return root;
                else
                    root=this.getMo(moNaming);
            }
           
        }
        return root;
    }
    /**
     * 清除缓存
     */
    public void clear(){
        allMoNodes.clear();
    }
}
