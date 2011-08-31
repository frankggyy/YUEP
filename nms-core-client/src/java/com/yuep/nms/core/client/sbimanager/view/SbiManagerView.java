/*
 * $Id: SbiManagerView.java, 2011-4-20 上午09:16:48 Administrator Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.sbimanager.view;

import java.awt.BorderLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import org.apache.commons.collections.CollectionUtils;

import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.Element;
import twaver.Node;
import twaver.TDataBox;
import twaver.table.TTable;

import com.yuep.base.util.format.YuepObjectUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.Decorator;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.tree.XTree;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.container.def.CoreContext;
import com.yuep.nms.core.client.sbimanager.action.DeleteSbiPropertyAction;
import com.yuep.nms.core.client.sbimanager.action.OpenSbiPropertyViewAction;
import com.yuep.nms.core.client.sbimanager.controller.SbiManagerController;
import com.yuep.nms.core.common.mocore.model.Mo;
import com.yuep.nms.core.common.mocore.module.constants.MoCoreModuleConstants;
import com.yuep.nms.core.common.mocore.service.MoCore;
import com.yuep.nms.core.common.mocore.service.MoFilter;
import com.yuep.nms.core.common.sbimanager.model.SbiProperty;
import com.yuep.nms.core.common.sbimanager.module.constants.SbiManagerModuleConstants;
import com.yuep.nms.core.common.sbimanager.service.SbiManager;

/**
 * <p>
 * Title: SbiManagerView
 * </p>
 * <p>
 * Description:
 * SBI管理
 * </p>
 * 
 * @author yangtao
 * created 2011-4-20 上午09:16:48
 * modified [who date description]
 * check [who date description]
 */
public class SbiManagerView extends AbstractClientView<SbiProperty> {
    private static final long serialVersionUID = -3890847205580532990L;

    private XTable<SbiProperty> sbiPropertyTable;
    private XTree nmTree=new XTree();
    private JButton addBtn;
    private JButton delBtn;
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#addListener(com.yuep.core.client.mvc.ClientController)
     */
    @Override
    public <V, M> void addListener(ClientController<SbiProperty, V, M> controller) {
        addBtn.addActionListener(new OpenSbiPropertyViewAction("",(SbiManagerController)controller));
        delBtn.addActionListener(new DeleteSbiPropertyAction("",(SbiManagerController)controller));
        nmTree.getDataBox().getSelectionModel().addDataBoxSelectionListener(new DataBoxSelectionListener(){
            @Override
            public void selectionChanged(DataBoxSelectionEvent databoxselectionevent) {
              List<Element> allElements= nmTree.getSelectedNodes();
              sbiPropertyTable.getTableModel().clearRawData();
              if(CollectionUtils.isEmpty(allElements)){
                  addBtn.setEnabled(false);
                  delBtn.setEnabled(false);
              }
              else{
                  addBtn.setEnabled(true);
                  delBtn.setEnabled(true);
                  final Mo mo=(Mo)allElements.get(0).getID();
                  
                  final SbiManager sbiManager=ClientCoreContext.getRemoteService(SbiManagerModuleConstants.SBIMANAGER_REMOTE_SERVICE, SbiManager.class);
          
                  new SwingWorker<Object,Object>(){

                    @Override
                    protected Object doInBackground() throws Exception {
                        List<SbiProperty> sbiProperties=sbiManager.getAllSbiProperties(mo.getMoNaming());
                        return sbiProperties;
                    }
                    
                    @Override
                    public void done(){
                        try {
                            Object result=this.get();
                            sbiPropertyTable.addRowDatas((List<SbiProperty>)result);
                        } catch (Exception ex) {
                             DialogUtils.showErrorExpandDialog(SwingUtilities.getWindowAncestor(SbiManagerView.this), ex);
                        } 
                  
                    }
                  }.execute();
              }
            }
        });
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#collectData()
     */
    @Override
    public List<SbiProperty> collectData() {
        return sbiPropertyTable.getAllDatas();
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#constructUi()
     */
    @Override
    public void constructUi() {
        SwingFactory swingFactory=ClientCoreContext.getSwingFactory();
        JPanel contentPanel=swingFactory.getPanel();
        contentPanel.setLayout(swingFactory.getBorderLayout());
        
        JSplitPane splitPane=swingFactory.getSplitPane();
        contentPanel.add(splitPane,BorderLayout.CENTER);
        splitPane.setDividerLocation(200);
        splitPane.setRightComponent(contructSbiPropertyPanel(swingFactory));
        splitPane.setLeftComponent(contructClientMoTreePanel(swingFactory));
        
        contentPanel.add(contructBtnPanel(swingFactory),BorderLayout.EAST);
        
        this.setLayout(swingFactory.getBorderLayout());
        this.add(contentPanel,BorderLayout.CENTER);
    }
    
    private JPanel contructSbiPropertyPanel(SwingFactory swingFactory){
        JPanel sbiPropertyTablePanel=swingFactory.getPanel();
        sbiPropertyTablePanel.setLayout(swingFactory.getBorderLayout());
        sbiPropertyTable=swingFactory.getXTableEditor("", SbiProperty.class, SbiProperty.class, "sbiName", new String[]{"sbiName","ip","port"});
        sbiPropertyTable.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        sbiPropertyTablePanel.add(swingFactory.getScrollPane(sbiPropertyTable));
        return sbiPropertyTablePanel;
    }
    
    private JPanel contructClientMoTreePanel(SwingFactory swingFactory){
        JPanel clientMoTreePanel=swingFactory.getPanel();
        clientMoTreePanel.setLayout(swingFactory.getBorderLayout());
        
        MoCore moCore=CoreContext.getInstance().local().getService(MoCoreModuleConstants.MOCORE_LOCAL_SERVICE, MoCore.class);
        List<Mo> mos=moCore.getMos(new MoFilter(){
            @Override
            public boolean accept(Mo mo) {
                return mo.getMoNaming().getMoType().equals("nm");
            }
        });
        
        nmTree.setDataBox(new TDataBox(ClientCoreContext.getString("sbimanager.nm")));
        
        Collections.sort(mos, new Comparator<Mo>(){
            @Override
            public int compare(Mo mo1, Mo mo2) {
                return mo1.getMoNaming().getName().compareTo(mo2.getMoNaming().getName());
            }
        });
        
        for(Mo mo:mos){
            Node moNode=new Node(mo);
            moNode.setUserObject(mo);
            moNode.setName(mo.getDisplayName());
            nmTree.getDataBox().addElement(moNode);
        }
        
        nmTree.expandAll();
        clientMoTreePanel.add(swingFactory.getScrollPane(nmTree));
        return clientMoTreePanel;
    }

    private JPanel contructBtnPanel(SwingFactory swingFactory){
        JPanel btnPanel=swingFactory.getPanel();
        double[][] ds=new double[][]{{10,90,10},swingFactory.getTableLayoutRowParam(2, 5, 5)};
        btnPanel.setLayout(swingFactory.getTableLayout(ds));
        addBtn=swingFactory.getButton(new Decorator<JButton>(){
            @Override
            public JButton contructEditor() {
                return new JButton();
            }

            @Override
            public void decorate(JButton t) {
                t.setText(ClientCoreContext.getString("sbimanager.add"));
            }
            
        });
        addBtn.setEnabled(false);
        btnPanel.add(addBtn,"1,1,f,c");
        
        
        delBtn=swingFactory.getButton(new Decorator<JButton>(){
            @Override
            public JButton contructEditor() {
                return new JButton();
            }

            @Override
            public void decorate(JButton t) {
                t.setText(ClientCoreContext.getString("sbimanager.delete"));
                
            }
        });
        delBtn.setEnabled(false);
        btnPanel.add(delBtn,"1,3,f,c");
        
        return btnPanel;
    }
    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return null;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return this.addBtn;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getHelpId()
     */
    @Override
    public String getHelpId() {
        return null;
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#getTitle()
     */
    @Override
    public String getTitle() {
        return "sbimanager.title";
    }

    /**
     * 
     * (non-Javadoc)
     * @see com.yuep.core.client.mvc.ClientView#initData(java.util.List)
     */
    @Override
    public void initData(List<SbiProperty> sbiProperties) {
        sbiPropertyTable.clearDatas();
        sbiPropertyTable.addRowDatas(sbiProperties);
    }

    /**
     * 
     * (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable o, Object arg) {
        List<SbiProperty> sbiProperties=(List<SbiProperty>)arg;
        initData(sbiProperties);
    }
    /**
     * 添加SBI Property
     * @param sbiProperties
     */
    public void addSbiProperties(SbiProperty... sbiProperties){
        sbiPropertyTable.addRowDatas(YuepObjectUtils.newArrayList(sbiProperties));
    }
    
    /**
     * 获取选中的SBIProperty
     * @return
     */
    public SbiProperty getSelectedSbiProperty(){
        List<SbiProperty> sbiProperties=sbiPropertyTable.getSelectionDatas();
        if(CollectionUtils.isEmpty(sbiProperties))
            return null;
        return sbiProperties.get(0);
    }
    /**
     * 获取选中的网管Mo对象
     * @return
     */
    public Mo getSelectedNm(){
        List<Element> elements=nmTree.getSelectedNodes();
        if(CollectionUtils.isEmpty(elements))
            return null;
        return (Mo)elements.get(0).getID();
    }

    /**
     * 删除SBIProperty
     * @param sbiProperty
     */
    public void deleteSbiProperty(SbiProperty sbiProperty){
        sbiPropertyTable.deleteRowData(sbiProperty);
    }
    /**
     * 设置选择网管Mo对象
     * @param nm
     */
    public void setSelectedNm(Mo nm){
        nmTree.getDataBox().getElementByID(nm).setSelected(true);
    }
    
    
}
