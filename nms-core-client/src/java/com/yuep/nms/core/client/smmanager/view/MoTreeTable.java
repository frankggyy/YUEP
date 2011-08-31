/*
 * $Id: MoTreeTable.java, 2011-4-18 下午03:52:29 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;

import org.apache.commons.collections.CollectionUtils;

import twaver.Element;
import twaver.Node;
import twaver.TDataBox;
import twaver.table.TTreeTable;

import com.yuep.base.resource.ResourceFactory;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.naming.MoNaming;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.smmanager.model.MoPermission;

/**
 * <p>
 * Title: MoTreeTable
 * </p>
 * <p>
 * Description:用树表来展现角色对mo的操作权限集
 * </p>
 * 
 * @author WangRui
 * created 2011-4-18 下午03:52:29
 * modified [who date description]
 * check [who date description]
 */
public class MoTreeTable extends TTreeTable {
    private static final long serialVersionUID = 2568204710631489012L;
    private static final String MODULE = "smmanager-client";
    private static final String FILE_PATH = "twaver/";

    /**
     * @param box
     */
    public MoTreeTable(TDataBox box) {
        super(box);
        initTable();
    }

    public void initTable() {
        JTableHeader header = this.getTableHeader();
        header.setReorderingAllowed(false);
        header.setBorder(BorderFactory.createEmptyBorder());
        setTableBodyPopupMenuFactory(null);
        setTableHeaderPopupMenuFactory(null);
        setRowHeight(22);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // setAutoResizeMode(TTreeTable.AUTO_RESIZE_ALL_COLUMNS);
        setColumnResizable(true);
        setColumnAutoResizable(false);
        getTree().setRootVisible(false);
        getTree().setIconVisible(false);
        initColumn();
    }

    public void initColumn() {
        setElementClass(Node.class);
         ResourceFactory resourceFactory = ClientCoreContext.getResourceFactory();
         URL url = resourceFactory.getFile(MODULE, FILE_PATH, "mo-permission-info.xml");
        registerElementClassXML(Node.class, url.toString());
        getColumnByName(TTreeTable.TREE_COLUMN).setDisplayName(ClientCoreContext.getString("MoPermGroup.name"));        
        getColumnByName(TTreeTable.TREE_COLUMN).setColumnWith(50);
//        getColumnByName("moName").setDisplayName(ClientCoreContext.getString("MoPermGroup.name"));
//        getColumnByName("moName").setColumnWith(50);
        getColumnByName("permGroup").setDisplayName(ClientCoreContext.getString("MoPermGroup.groupName"));
        getColumnByName("permGroup").setColumnWith(50);
    }

    @Override
    protected boolean isEditable(int rowIndex) {
        Element element = getElementByRowIndex(rowIndex);
        if (element.getParent() == null)
            return false;
        return super.isEditable(rowIndex);
    }

    public void addMos(List<MoPermission> moPermList) {
        if (CollectionUtils.isEmpty(moPermList))
            return;
        List<MoPermission> rootMos = new ArrayList<MoPermission>();
        for (MoPermission perm : moPermList) {
            Mo mo = perm.getMo();
            if (null == mo.getParent()) {
                rootMos.add(perm);
            }
        }
        for (MoPermission rootPerm : rootMos) {
            Mo mo = rootPerm.getMo();
            Node node = new Node(mo.getMoNaming().getName());
            node.setName(mo.getDisplayName());
            node.setUserObject(rootPerm);
//            node.putClientProperty("moName", mo.getDisplayName());
            node.putClientProperty("permGroup", rootPerm.getGroupName());
            getDataBox().addElement(node);      
            moPermList.remove(rootPerm);
            constructChildren(mo.getMoNaming(), node, moPermList);          
        }
    }

    private void constructChildren(MoNaming moNaming, Node pNode, List<MoPermission> moPermList) {
        if(CollectionUtils.isEmpty(moPermList)){
            return;
        }
        for (int i=0;i<moPermList.size();i++) {
            MoPermission perm = moPermList.get(i);
            Mo mo = perm.getMo();
            if (!mo.getParent().equals(moNaming))
                continue;
            MoCore moCore = ClientCoreContext.getLocalService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
            List<Mo> moList = moCore.getChildrenMos(null, moNaming);
            if (moList.size()==0) {
                Node child = new Node(mo.getMoNaming().getName());
                child.setName(mo.getDisplayName());
                child.setUserObject(perm);
//                child.putClientProperty("moName", mo.getDisplayName());
                child.putClientProperty("permGroup", perm.getGroupName());
                child.setParent(pNode);
                getDataBox().addElement(child);
                moPermList.remove(perm);
                i--;
            } else {
                Node child = new Node(mo.getMoNaming().getName());
                child.setName(mo.getDisplayName());
                child.setUserObject(perm);
//                child.putClientProperty("moName", mo.getDisplayName());
                child.putClientProperty("permGroup", perm.getGroupName());
                child.setParent(pNode);
                getDataBox().addElement(child);
                moPermList.remove(perm);
                constructChildren(mo.getMoNaming(), child, moPermList);
                i--;
            }
        }
    }
    
    /**
     *  得到树表中选择的数据
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<MoPermission> getSelectedDatas(){
        List<MoPermission> selectedMos= new ArrayList<MoPermission>();
        final List<Element> selectedElements = this.getDataBox().getSelectionModel().getAllSelectedElement();
        if (CollectionUtils.isEmpty(selectedElements))
            return null;
        for (Element seleElement : selectedElements) {
            if (seleElement.getUserObject() instanceof MoPermission) {
                selectedMos.add((MoPermission) seleElement.getUserObject());
            }
        }
        return selectedMos;        
    }

    /**
     * 得到树表中的所有数据
     * @return null或数据
     */
    @SuppressWarnings("unchecked")
    public List<MoPermission> getAllDatas(){
        List<MoPermission> allMos= new ArrayList<MoPermission>();
        final List<Element> allElements = this.getDataBox().getAllElements();
        if (CollectionUtils.isEmpty(allElements))
            return null;
        for (Element element : allElements) {
            if (element.getUserObject() instanceof MoPermission) {
                allMos.add((MoPermission) element.getUserObject());
            }
        }
        return allMos;        
    }
    
    @SuppressWarnings("unchecked")
    public void updateMoTree(List<String> datas){
        if(CollectionUtils.isEmpty(datas))
            return;
        String groupName = datas.get(0);
        List allSelectedElement = getDataBox().getSelectionModel().getAllSelectedElement();
        if(CollectionUtils.isNotEmpty(allSelectedElement)){
            Object object = allSelectedElement.get(0);
            if(object instanceof Element){
                Element element = (Element) object;
                Object userObject = element.getUserObject();
                if(userObject instanceof MoPermission){
                    MoPermission permission = (MoPermission) userObject;
                    permission.setGroupName(groupName);
                }
                element.putClientProperty("permGroup", groupName);
            }
        }
    }

}
