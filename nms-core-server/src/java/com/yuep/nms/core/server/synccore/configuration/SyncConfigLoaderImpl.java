/*
 * $Id: SyncConfigLoaderImpl.java, 2011-5-18 下午07:14:12 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.server.synccore.configuration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.yuep.base.resource.FileLoader;
import com.yuep.base.xml.ArrayListItems;
import com.yuep.base.xml.XmlFileReader;
import com.yuep.nms.core.common.mocore.service.MoStaticInfoManager;
import com.yuep.nms.core.common.synccore.model.SyncNode;

/**
 * <p>
 * Title: SyncConfigLoaderImpl
 * </p>
 * <p>
 * Description:
 * 同步配置文件加载类
 * </p>
 * 
 * @author yangtao
 * created 2011-5-18 下午07:14:12
 * modified [who date description]
 * check [who date description]
 */
public class SyncConfigLoaderImpl implements SyncConfigLoader {

    private MoStaticInfoManager mostaticInfoManager;
    private String[] syncConfigFiles;
    private List<SyncNode> syncNodes=new ArrayList<SyncNode>();
    
    public SyncConfigLoaderImpl(String... syncConfigFiles){
        this.syncConfigFiles=syncConfigFiles;
    }
    
    public void setMoStaticInfoManager(MoStaticInfoManager mostaticInfoManager){
        this.mostaticInfoManager=mostaticInfoManager;
    }
    
    public void init(){
      for(String syncConfigFile:syncConfigFiles){
          List<SyncNode> syncNodes=loadSyncConfig(syncConfigFile);
          this.syncNodes.addAll(syncNodes);
      }
    
    }
    
    
    @SuppressWarnings("unchecked")
    private List<SyncNode> loadSyncConfig(String dataFile){
        InputStream mappingFileInputStream=this.getClass().getResourceAsStream("syncconfig-mapping.xml");
        InputStream dataFileInputStream=FileLoader.getInputStream(dataFile);
        List<SyncNode> syncNodes=new ArrayList<SyncNode>();
        Object mod_data = XmlFileReader.getXmlConfig(mappingFileInputStream, dataFileInputStream);
        ArrayListItems<SyncNode> syncConfigItems = (ArrayListItems<SyncNode>) mod_data;
        List<SyncNode> newSyncNodes = syncConfigItems.getItems();
        if(CollectionUtils.isEmpty(newSyncNodes))
            return syncNodes;
        return newSyncNodes;
    }
    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.configuration.SyncConfigLoader#getSyncNodes(java.lang.String)
     */
    @Override
    public List<SyncNode> getSyncNodes(String type) {
        List<SyncNode> result = new ArrayList<SyncNode>();
        for (SyncNode syncNode : syncNodes) {
            if (result.contains(syncNode))
                continue;
            if (CollectionUtils.isEmpty(syncNode.getIncludes()))
                continue;
            if (!mostaticInfoManager.contain(type, syncNode.getIncludes().toArray(new String[0])))
                continue;
            if (CollectionUtils.isNotEmpty(syncNode.getDepends())) {
                addDependSyncNode(syncNode, result,type);
            }
            result.add(syncNode);

        }
        return result;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.configuration.SyncConfigLoader#getSyncNodesByNodeName(java.lang.String, java.lang.String[])
     */
    @Override
    public List<SyncNode> getSyncNodesByNodeName(String type,String... syncNodes) {
        List<SyncNode> newSyncNodes = new ArrayList<SyncNode>();
        SyncNode neededSyncNode = null;
        for(String scope:syncNodes){
            for (SyncNode syncNode : this.syncNodes) {
                if (!syncNode.getName().equals(scope))
                    continue;
                if (CollectionUtils.isEmpty(syncNode.getIncludes()))
                    continue;
                if (!mostaticInfoManager.contain(type, syncNode.getIncludes().toArray(new String[0]))){
                    continue;
                }
                neededSyncNode=syncNode;
                break;
            }
            if (neededSyncNode == null)
                return newSyncNodes;
            addDependSyncNode(neededSyncNode, newSyncNodes,type);//添加依赖的同步范围
            newSyncNodes.add(neededSyncNode);
            addDependedSyncNode(newSyncNodes,scope,type);//添加被依赖的同步范围
        }
      
        return newSyncNodes;
    }

    /**
     * (non-Javadoc)
     * @see com.yuep.nms.core.server.synccore.configuration.SyncConfigLoader#getSyncNodesByNodeType(java.lang.String, java.lang.String[])
     */
    @Override
    public List<SyncNode> getSyncNodesByNodeType(String type,String... syncNodeTypes) {
        if (type == null || syncNodeTypes == null)
            throw new NullPointerException();
        List<SyncNode> result = new ArrayList<SyncNode>();
        for (SyncNode syncNode : this.syncNodes) {
            if (!isContain(syncNode, syncNodeTypes))
                continue;
            if (result.contains(syncNode))
                continue;
            if (CollectionUtils.isEmpty(syncNode.getIncludes()))
                continue;
            if (!mostaticInfoManager.contain(type, syncNode.getIncludes().toArray(new String[0])))
                break;
            if (CollectionUtils.isNotEmpty(syncNode.getDepends())) {
                addDependSyncNode(syncNode, result,type);// 添加当前syncScope依赖的syncScope
            }
            result.add(syncNode);
        }
        return result;

    }

    private void addDependSyncNode(SyncNode syncNode, List<SyncNode> syncNodes,String type) {
        if (CollectionUtils.isEmpty(syncNode.getDepends()))
            return;
        for (String depend : syncNode.getDepends()) {
            SyncNode dependScope = findSyncNode(depend);
            if (dependScope == null)
                continue;
            if (!mostaticInfoManager.contain(type, dependScope.getIncludes().toArray(new String[0])))
                continue;
            addDependSyncNode(dependScope, syncNodes,type);
            if (!syncNodes.contains(dependScope))
                syncNodes.add(dependScope);
        }
    }
    
    private void addDependedSyncNode(List<SyncNode> syncNodes,String synNodeName,String type){
        for (SyncNode syncNode : syncNodes) {
            if (syncNodes.contains(syncNode))
                continue;
            if (CollectionUtils.isEmpty(syncNode.getIncludes()))
                continue;
            if (!mostaticInfoManager.contain(type, syncNode.getIncludes().toArray(new String[0])))
                continue;
            if (CollectionUtils.isEmpty(syncNode.getDepends()))
                continue;
            if (syncNode.getDepends().contains(synNodeName)){
                syncNodes.add(syncNode);
                addDependedSyncNode(syncNodes,syncNode.getName(),type);
            }
        }
    }
    
    
    private boolean isContain(SyncNode syncNode, String... syncNodeTypes) {
        for (String syncNodeType : syncNodeTypes) {
            if (syncNode.getNodeType().equals(syncNodeType))
                return true;
        }
        return false;
    }
    
    private SyncNode findSyncNode(String name) {
        for (SyncNode syncNode : syncNodes) {
            if (syncNode.getName().equals(name))
                return syncNode;

        }
        return null;
    }

}
