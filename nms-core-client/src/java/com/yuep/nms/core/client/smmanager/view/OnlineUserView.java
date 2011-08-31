/*
 * $Id: OnlineUserView.java, 2011-4-13 下午05:59:55 WangRui Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.nms.core.client.smmanager.view;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import twaver.swing.TableLayout;
import twaver.table.TTable;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.navigator.AbstractTabView;
import com.yuep.core.client.component.table.renderer.TimeCellRenderer;
import com.yuep.core.client.component.table.renderer.XTableCellRenderer;
import com.yuep.core.client.component.validate.editor.XTableEditor;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.session.def.Session;
import com.yuep.nms.core.client.smmanager.action.OnlineUserKickAction;
import com.yuep.nms.core.client.smmanager.action.OnlineUserRefreshAction;

/**
 * <p>
 * Title: OnlineUserView
 * </p>
 * <p>
 * Description: 查询在线用户
 * </p>
 * 
 * @author sufeng
 * created 2011-4-25 下午05:59:55
 * modified [who date description]
 * check [who date description]
 */
public class OnlineUserView extends AbstractTabView<Object> {
   
    private static final long serialVersionUID = 4874355264394566965L;
    private  XTableEditor<Session> table;
    private  JButton refreshButton;
    private  JButton kickButton;
   
    private Map<String,XTableCellRenderer> renderers=new HashMap<String, XTableCellRenderer>();
    
    @Override
    protected <V, M> void addButtonListener(ClientController<Object, V, M> controller) {  
        OnlineUserRefreshAction refresh=new OnlineUserRefreshAction(OnlineUserRefreshAction.class.getSimpleName());
        refreshButton.setAction(refresh);
        
        OnlineUserKickAction kick=new OnlineUserKickAction(OnlineUserKickAction.class.getSimpleName());
        kickButton.setAction(kick);
    }

    @Override
    protected String getDescription() {
        return "smmanager.onlineuser.description";
    }

    @Override
    protected JButton[] getSensitiveButtons() {
        return new JButton[]{};
    }

    @Override
    public void constructUi() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        double[][] ds=new double[][]{{TableLayout.FILL,5,100},{TableLayout.FILL}};
        JPanel pane = swingFactory.getPanel(swingFactory.getTableLayout(ds));//整个框架
        
        table = swingFactory.getXTableEditor("onlineuser.table", Session.class, Session.class, "sessionId", new String[] { "sessionId","ip","serverIp","sessionState","loginTime","lastActivetime","owner" });
        table.setAutoResizeMode(TTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane leftPane = swingFactory.getScrollPane(table);
        pane.add(leftPane,"0,0,f,f");
        
        TimeCellRenderer loginTimeRenderer = new TimeCellRenderer();
        table.getColumnByName("loginTime").setCellRenderer(loginTimeRenderer);
        renderers.put("loginTime", loginTimeRenderer);
        
        TimeCellRenderer lastActiveTimeRenderer = new TimeCellRenderer();
        table.getColumnByName("lastActivetime").setCellRenderer(lastActiveTimeRenderer);
        renderers.put("lastActivetime", lastActiveTimeRenderer);

        refreshButton=swingFactory.getButton(new ButtonDecorator("smmanager.onlineuser.refresh",'R'));
        kickButton=swingFactory.getButton(new ButtonDecorator("smmanager.onlineuser.kick",'K'));
        JPanel btnPane = swingFactory.getPanel(swingFactory.getFlowLayout(FlowLayout.CENTER)); //按钮
        btnPane.add(refreshButton);
        btnPane.add(kickButton);
        pane.add(btnPane,"2,0,f,f");

        setLayout(swingFactory.getTableLayout(new double[][]{{TableLayout.FILL,5},{TableLayout.FILL}}));
        add(pane, "0,0,f,f");
    }

    @Override
    public JButton getDefaultButton() {
        return refreshButton;
    }

    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    @Override
    public String getHelpId() {
        return "smmanager.onlineuser";
    }

    @Override
    public String getTitle() {
        return "smmanager.onlineuser.title";
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable o, Object arg) {
        if(!(arg instanceof List))
            return;
        table.clearDatas();
        List list=(List)arg;
        table.addRowDatas(list);
    }

    @Override
    public List<Object> collectData() {
        return null;
    }
    
    @Override
    protected void initializeData(List<Object> data) {
        update(null,data);
    }

    public Map<String, XTableCellRenderer> getRenderers() {
        return renderers;
    }
    
    public XTableEditor<Session> getTable() {
        return table;
    }
    
}
