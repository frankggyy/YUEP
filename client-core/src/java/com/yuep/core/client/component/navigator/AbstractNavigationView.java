/*
 * $Id: AbstractNavigationView.java, 2009-3-16 ����10:40:52 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.TreeSelectionModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import twaver.CheckableFilter;
import twaver.DataBoxSelectionEvent;
import twaver.DataBoxSelectionListener;
import twaver.Element;
import twaver.Node;
import twaver.PopupMenuFactory;
import twaver.tree.TTree;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.editor.StringEditorDecorator;
import com.yuep.core.client.component.factory.decorator.editor.TreeEditorDecorator;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;
import com.yuep.core.client.mvc.ClientController;
import com.yuep.core.client.mvc.validate.AbstractValidateView;
import com.yuep.core.client.mvc.validate.ValidateMessage;

/**
 * <p>
 * Title: AbstractNavigationView
 * </p>
 * <p>
 * Description:����ĵ����ؼ���View
 * </p>
 * 
 * @author Victor,aaron lee
 * created 2009-3-16 ����10:40:52
 * @modified aaron lee 2010-3-30 �����<code>AbstractValidateView<T></code>�̳�
 * check [who date description]
 */
public abstract class AbstractNavigationView<T> extends AbstractValidateView<T> {
    private static final long serialVersionUID = 267831199369815290L;
    /**
     * ������
     */
    protected TTree navigationTree;
    /**
     * ���˿ؼ�
     */
    private JTextField filterField;
    /**
     * ���ı���
     */
    private JLabel treeTitle;
    /**
     * �������Ĺ�������
     */
    private List<AbstractFilter> filters;
    /**
     * ������ʾ����
     */
    protected JPanel tabPanel;
    
    /**
     * ��ѡ�ڵ�
     */
    protected Node selectedNode;
    /**
     * ��Tabҳʱ��Tabҳ����
     */
    protected JTabbedPane tp;
    /**
     * ����Tabҳ��Ϣ
     */
    private Map<String, List<AbstractTabController<?, ?, ?>>> tabMap = new ConcurrentHashMap<String, List<AbstractTabController<?, ?, ?>>>();

    /**
     * ��һ�α�ѡ���Tabҳ��Controller
     */
    private AbstractTabController<?, ?, ?> lastSelectedTabController;

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#constructUi()
     */
    public void constructUi() {
        // ��Ҫ�ȳ�ʼ��������
        filters = new ArrayList<AbstractFilter>();
        // name filter of default
        filters.add(new AbstractFilter() {
            @Override
            public String getDescription() {
                return ClientCoreContext.getString("common.navigation.filter.namefilter");
            }

            @Override
            public boolean filter(Element data, String value) {
                return data.getName().toLowerCase().indexOf(value) != -1;
            }

            @Override
            public boolean isFilter(String name) {
                return name != null && name.length() > 0;
            }
        });
        List<AbstractFilter> list = getFilters();
        if (!CollectionUtils.isEmpty(list)) {
            filters.addAll(list);
        }
        super.constructUi();
    }

    /**
     * ��ȡҪ���˵��ַ���
     * @return <code>String</code> Ҫ���˵��ַ���
     */
    public String getFilterText() {
        return filterField.getText();
    }

    /**
     * ���õ�����������
     * @param title
     *            ����������
     */
    public void setTreeTitle(String title) {
        treeTitle.setText(title);
    }

    /**
     * ��յ������ڵ�
     */
    public void resetNavigation() {
        navigationTree.clearSelection();
        navigationTree.getDataBox().clear();
        // ��յ������˿�
        filterField.setText("");
    }

    /**
     * ͨ���ڵ����Ƶ�����ĳ���ڵ㣬���������������ͬ�Ķ���ڵ�ֻ�ܵ���������һ�����˳�����ʹ��navigationByNodeId
     * 
     * ����Ҳ�����Ӧ�ڵ㣬�򵼺�����һ���ڵ�
     * 
     * @param nodeName
     *            �ڵ�����
     * @see navigationByNodeId
     */
    public void navigationByNodeName(final String nodeName) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                while (navigationTree.getDataBox().size() == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                    }
                }
                Element node = navigationTree.getDataBox().getElementByName(nodeName);
                if (node == null) {
                    node = navigationTree.getElementByRowIndex(0);
                }
                System.out.println("" + node.getName());
                navigationTree.getDataBox().getSelectionModel().setSelection(node);
                // node.setSelected(true);
            }
        });
    }

    /**
     * ͨ���ڵ�ID������ĳ���ڵ�
     * 
     * ����Ҳ�����Ӧ�ڵ㣬�򵼺�����һ���ڵ�
     * 
     * @param nodeId
     *            �ڵ�ID,�϶���Ψһ
     * @see navigationByNodeName
     */
    public void navigationByNodeId(final Object nodeId) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                while (navigationTree.getDataBox().size() == 0) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e1) {
                    }
                }
                Element node = navigationTree.getDataBox().getElementByID(nodeId);
                if (node == null) {
                    node = navigationTree.getElementByRowIndex(0);
                }
                System.out.println("" + node.getID());
                navigationTree.getDataBox().getSelectionModel().setSelection(node);
                // node.setSelected(true);
            }
        });
    }

    /**
     * ���ܶ�̬���ģ�ֻ������ʵ��ʱ��д�����������Ƿ���ʾ������
     * 
     * @return �Ƿ���ʾ�����������default��ʾ
     */
    protected boolean showFilter() {
        return true;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.yotc.opview.framework.client.commponent.module.AutoVerifyClientView#getWorkspace()
     */
    @Override
    protected JComponent createContentPane() {
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        JSplitPane jSplitPane = swingFactory.getSplitPane();
        double[][] table = new double[][] { { 3, XTableLayout.FILL }, { 20, 20, 5, XTableLayout.FILL } };
        JPanel treePanel = swingFactory.getPanel(swingFactory.getTableLayout(table));
        navigationTree = swingFactory.getXEditor(new TreeEditorDecorator(""));
        // ����tree����
        navigationTree.setToolTipText(null);
        navigationTree.setSelectableOnRightClick(true);
        navigationTree.setClearSelectionOnMarginClicked(true);
        // ��ѡ
        navigationTree.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
        navigationTree.setShowsRootHandles(true);
        navigationTree.setPopupMenuFactory(getPopupMenuFactory());
        navigationTree.setEnableTristateCheckBox(true);
        // navigationTree.setTTreeSelectionMode(TTree.CHECK_DESCENDANT_ANCESTOR_SELECTION);
        navigationTree.setCheckableFilter(new CheckableFilter() {
            @Override
            public boolean isCheckable(Element element) {
                return false;
            }
        });
        JScrollPane scroll = swingFactory.getScrollPane(navigationTree);
        filterField = swingFactory.getXEditor(new StringEditorDecorator(""));
        StringBuilder tip = new StringBuilder();
        tip.append(ClientCoreContext.getString("common.navigation.filter.tip"));
        for (AbstractFilter filter : filters) {
            tip.append("<li>");
            tip.append(filter.getDescription());
            tip.append("</li>");
        }
        tip.append("</ul></html>");
        filterField.setToolTipText(tip.toString());
        treeTitle = swingFactory.getLabel(new LabelDecorator("common.navigation.tree.title"));
        treePanel.add(treeTitle, "1,0,f,f");
        if (showFilter()) {
            treePanel.add(filterField, "1,1,f,f");
            treePanel.add(scroll, "1,3,f,f");
        } else {
            treePanel.add(scroll, "1,1,1,3");
        }
        treePanel.setPreferredSize(new Dimension(170, getHeight() - 35));

        tabPanel = swingFactory.getPanel(swingFactory.getBorderLayout());
        jSplitPane.add(treePanel, JSplitPane.LEFT);
        jSplitPane.add(tabPanel, JSplitPane.RIGHT);
        jSplitPane.setDividerLocation(230);
        jSplitPane.getTopComponent().setMinimumSize(new Dimension(200, 600));
        jSplitPane.setContinuousLayout(true);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setBorder(null);
        return jSplitPane;
    }

    /**
     * ��ӹ�������Ĭ�����һ�����ƹ�����
     * 
     * @param filterList
     */
    protected abstract List<AbstractFilter> getFilters();

    /**
     * (non-Javadoc)
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void update(Observable obs, Object obj) {
        if (obj == null) {
            return;
        }
        if (obj instanceof List) {
            System.out.println("add tree nodes: " + obj);
            navigationTree.getDataBox().clear();
            navigationTree.getDataBox().addElements((List) obj);
            navigationTree.expandAll();
        } else if (obj instanceof Node) {
            Node node = (Node) obj;
            System.out.println("add tree node: " + obj);
            navigationTree.getDataBox().addElement(node);
            navigationTree.expandAll();
            navigationByNodeName(node.getName());
        } else if (obj instanceof Map) {
            tabMap.clear();
            tabMap.putAll((Map<? extends String, ? extends List<AbstractTabController<?, ?, ?>>>) obj);
        }
    }

    /**
     * ɾ���������ϵ�һ���ڵ�
     * @param node Ҫɾ���Ľڵ�
     */
    public void removeNode(Node node) {
        System.out.println("remove tree node: " + node);
        navigationTree.getDataBox().removeElement(node);
        navigationTree.expandAll();
    }

    /**
     * ���ݵ������Ͻڵ������ɾ���ڵ�
     * @param nodeName �ڵ�����
     */
    public void removeNodeByName(String nodeName) {
        Element node = navigationTree.getDataBox().getElementByName(nodeName);
        if (node == null)
            return;
        System.out.println("remove tree node: " + node);
        navigationTree.getDataBox().removeElement(node);
        navigationTree.expandAll();
    }

    /**
     * ���ݵ������Ͻڵ��IDɾ���ڵ�
     * @param nodeId �ڵ�ID
     */
    public void removeNodeById(Object nodeId) {
        Element node = navigationTree.getDataBox().getElementByID(nodeId);
        if (node == null)
            return;
        System.out.println("remove tree node: " + node);
        navigationTree.getDataBox().removeElement(node);
        navigationTree.expandAll();
    }

    /**
     * ���һ���ڵ㵽��������
     * @param node Ҫ��ӵĽڵ�
     */
    public void addNode(Node node) {
        System.out.println("add tree node: " + node);
        navigationTree.getDataBox().addElement(node);
        navigationTree.expandAll();
        navigationByNodeName(node.getName());
    }

    /**
     * ���ݽڵ�����ֻ�ȡ��������һ���ڵ�
     * @param nodeName �ڵ�����
     * @return Element ����������Ľڵ�
     */
    public Element getNodeByName(String nodeName) {
        Element node = navigationTree.getDataBox().getElementByName(nodeName);
        return node;
    }

    /**
     * ���ݽڵ��ID��ȡ��������һ���ڵ�
     * @param nodeId �ڵ�ID
     * @return Element ��ID����Ľڵ�
     */
    public Element getNodeById(Object nodeId) {
        Element node = navigationTree.getDataBox().getElementByID(nodeId);
        return node;
    }

    /**
     * ���ݽڵ��ID��ȡ�ýڵ�ĸ��ڵ�
     * @param nodeId �ڵ�ID
     * @return Element ���ڵ�
     */
    public Element getParentNodeById(Object nodeId) {
        Element node = navigationTree.getDataBox().getElementByID(nodeId);
        return node.getParent();
    }

    /**
     * (non-Javadoc)
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(700, 550);
    }

    /**
     * ѡ�����ڵ�ʱ����
     * 
     * @param nodes
     *            ����ڵ�
     */
    protected abstract void onSelectMulti(List<Node> nodes);

    /**
     * ��ȡ��ǰѡ��Tabҳ��controller
     * @return <code>AbstractTabController</code> ��ǰѡ��Tabҳ��controller
     */
    public AbstractTabController getCurrentTabController() {
        Object userObject = selectedNode.getUserObject();
        List<AbstractTabController<?, ?, ?>> tabs = tabMap.get(userObject);
        if (CollectionUtils.isEmpty(tabs)) {
            return null;
        }
        if (tabs.size() == 1)
            return tabs.get(0);
        Component selectedComponent = tp.getSelectedComponent();
        int indexOfComponent = tp.indexOfComponent(selectedComponent);
        String title = tp.getTitleAt(indexOfComponent);
        String tabTitle = null;
        for (AbstractTabController<?, ?, ?> abstractTabController : tabs) {
            Object clientView = abstractTabController.getClientView();
            tabTitle = ClientCoreContext.getString(((AbstractTabView) clientView).getTabTitle());
            if (tabTitle.equals(title)) {
                return abstractTabController;
            }
        }
        return null;
    }

    /**
     * ��ȡ��ǰѡ�񵼺����ڵ�����й�����ҳ���Controller�б�
     * @return <code>List<AbstractTabController<?, ?, ?>></code> �ڵ�����й�����ҳ���Controller�б�
     */
    public List<AbstractTabController<?, ?, ?>> getCurrentNodeController() {
        Object userObject = selectedNode.getUserObject();
        List<AbstractTabController<?, ?, ?>> tabs = tabMap.get(userObject);
        if (CollectionUtils.isEmpty(tabs)) {
            return null;
        }
        return tabs;
    }

    /**
     * ѡ�񵥸��ڵ�ʱ����
     * 
     * @param node
     *            �����ڵ�
     */
    @SuppressWarnings("unchecked")
    protected void onSelectOne(Node node) {
        if (node == null) {
            List rootElements = navigationTree.getDataBox().getRootElements();
            if (CollectionUtils.isNotEmpty(rootElements)) {
                Object object = rootElements.get(0);
                if (object instanceof Node) {
                    this.selectedNode = (Node) object;
                }
            }
        } else {
            this.selectedNode = node;
        }
        messagePane.showMessage(new ValidateMessage());
        messagePane.removePorpertyChangeListener();
        tabPanel.removeAll();
        Object userObject=null;
        if(selectedNode!=null)
         userObject = selectedNode.getUserObject();
        if (userObject == null) {
            return;
        } else {
            List<AbstractTabController<?, ?, ?>> tabs = new ArrayList<AbstractTabController<?, ?, ?>>();
            if (MapUtils.isEmpty(tabMap)) {
                if (userObject instanceof List)
                    tabs = (List<AbstractTabController<?, ?, ?>>) userObject;
                else
                    tabs.add((AbstractTabController<?, ?, ?>) userObject);
            } else {
                tabs = tabMap.get(userObject);
            }
            if (CollectionUtils.isEmpty(tabs))
                return;
            if (tabs.size() == 1) {
                AbstractTabController<?, ?, ?> abstractTabController = tabs.get(0);
                abstractTabController.initView();
                tabPanel.add(abstractTabController.getClientView(), BorderLayout.CENTER);
                abstractTabController.setSelectNode(selectedNode);
                abstractTabController.initData(selectedNode.getBusinessObject());
                messagePane.setDescription(abstractTabController.getClientView().getDescription());
                messagePane.removePorpertyChangeListener();
                messagePane.initPropertyChangeListener(tabPanel);
            } else {
                tp = ClientCoreContext.getSwingFactory().getTabbedPane();
                final List<AbstractTabController<?, ?, ?>> tabList = new ArrayList<AbstractTabController<?, ?, ?>>(
                        tabs);
                tp.addChangeListener(new ChangeListener() {

                    @Override
                    public void stateChanged(ChangeEvent e) {
                        Component selectedComponent = tp.getSelectedComponent();
                        messagePane.showMessage(new ValidateMessage());
                        messagePane.removePorpertyChangeListener();
                        int indexOfComponent = tp.indexOfComponent(selectedComponent);
                        if (indexOfComponent >= 0) {
                            String title = tp.getTitleAt(indexOfComponent);
                            for (AbstractTabController<?, ?, ?> abstractTabController : tabList) {
                                Object view = abstractTabController.getClientView();
                                String tabTitle = ((AbstractTabView) view).getTabTitle();
                                if (ClientCoreContext.getString(tabTitle).equals(title)) {
                                    lastSelectedTabController = abstractTabController;
                                    abstractTabController.initView();
                                    abstractTabController.initData(selectedNode.getBusinessObject());
                                    messagePane.setDescription(abstractTabController.getClientView().getDescription());
                                }
                            }
                        }
                        messagePane.initPropertyChangeListener((JComponent) selectedComponent);
                        messagePane.setSensitiveButtonEnabled(false);
                    }

                });
                for (AbstractTabController<?, ?, ?> tab : tabs) {
                    String title = ClientCoreContext.getString(tab.getClientView().getTabTitle());
                    tp.add(title, tab.getClientView());
                    tab.setSelectNode(selectedNode);
                }
                tabPanel.add(tp, BorderLayout.CENTER);
            }
        }
        tabPanel.updateUI();
        tabPanel.getParent().validate();
//        messagePane.initPropertyChangeListener();
        messagePane.setSensitiveButtonEnabled(false);
    }

    /**
     * @return �������ĵ����˵�
     */
    protected abstract PopupMenuFactory getPopupMenuFactory();

    /**
     * aaron add. Ϊ�������������ıȽ���
     * 
     * @param comparator
     * @author aaron lee
     */
    public void setTreeSortComparator(Comparator<Node> comparator) {
        navigationTree.setSortComparator(comparator);
        navigationTree.updateUI();
    }

    /**
     * �жϽڵ�����Ƿ���ʾ
     * 
     * @param element
     *            ���ڵ�
     * @return �Ƿ���ʾ
     */
    private boolean isVisible(Element element) {
        System.out.println(element.getName() + ".attachment=" + element.containsAttachment("ensureVisible"));
        if (element.containsAttachment("ensureVisible")) {
            System.out.println("Ensure show " + element.getName());
            return true;
        }
        if (element.getBusinessObject() == null) {
            return false;
        }
        if (getFilterText() == null || getFilterText().length() == 0) {
            return true;
        }
        boolean isVisible = false;
        String filter = getFilterText().toLowerCase();
        try {
            isVisible = filter(element, filter);
        } catch (Exception ex) {
        }
        if (isVisible) {
            Element parent = element.getParent();
            while (parent != null) {
                parent.setVisible(true);
                if (!parent.containsAttachment("ensureVisible")) {
                    parent.addAttachment("ensureVisible");
                }
                parent = parent.getParent();
            }
        }
        return isVisible;
    }

    /**
     * �Ե��������й���
     * 
     * @param data
     *            ���ڵ�
     * @param value
     *            �����ַ���
     * @return �Ƿ���ʾ
     */
    private boolean filter(Element data, String value) {
        if (value == null || value.length() == 0) {
            return false;
        }
        for (AbstractFilter filter : filters) {
            if (filter.isFilter(value) && filter.filter(data, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * (non-Javadoc)
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#addButtonListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
     */
    @Override
    public <V,M> void addButtonListener(ClientController<T,V,M> controller) {
        if (showFilter()) {
            filterField.addKeyListener(new KeyAdapter() {
                @SuppressWarnings("unchecked")
                @Override
                public void keyReleased(KeyEvent event) {
                    List<Element> es = navigationTree.getDataBox().getAllElements();
                    for (Element e : es) {
                        e.removeAttachment("ensureVisible");
                    }
                    for (Element e : es) {
                        e.setVisible(isVisible(e));
                        System.out.println(e.getName() + "=" + e.isVisible());
                    }
                    navigationTree.updateTViewUI();
                    navigationTree.expandAll();
                }
            });
        }
        navigationTree.getDataBox().getSelectionModel().addDataBoxSelectionListener(
                new DataBoxSelectionListener() {
                    @SuppressWarnings("unchecked")
                    @Override
                    public void selectionChanged(DataBoxSelectionEvent event) {
                        try {
                            List<Node> selected = event.getBoxSelectionModel().getAllSelectedElement();
                            if (CollectionUtils.isEmpty(selected)) {
                                return;
                            }
                            if (selected.size() == 1) {
                                Node node = selected.get(0);
                                selectedNode = node;
                                Object userObject = node.getUserObject();
                                if (userObject == null) {
                                    return;
                                } else {
                                    List<AbstractTabController<?, ?, ?>> tabs = new ArrayList<AbstractTabController<?, ?, ?>>();
                                    if (MapUtils.isEmpty(tabMap)) {
                                        if (userObject instanceof List)
                                            tabs = (List<AbstractTabController<?, ?, ?>>) userObject;
                                        else {
                                            if (userObject instanceof AbstractTabController)
                                                tabs.add((AbstractTabController<?, ?, ?>) userObject);
                                            else
                                                return;
                                        }
                                    } else {
                                        tabs = tabMap.get(userObject);
                                    }
                                    if (!CollectionUtils.isEmpty(tabs)) {
                                        for (AbstractTabController<?, ?, ?> tab : tabs) {
                                            tab.setSelectNode(node);
                                            tab.initView();
                                        }
                                    }
                                }
                                onSelectOne(node);
                            } else {
                                // TODO ѡ�����ʱ����Ŀǰ��֧��
                                onSelectMulti(selected);
                            }
                            // updateVerifiers();
                        } catch (Exception ex) {
                            System.err.println(ExceptionUtils.getCommonExceptionInfo(ex));
                        }
                    }
                });
    }

    /**
     * ��ȡ��������������Ϣ
     * @return the navigationTree
     */
    public TTree getNavigationTree() {
        return navigationTree;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#getButtons()
     */
    @Override
    public JButton[] getButtons() {
        return new JButton[] { cancelButton };
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#collectData()
     */
    @Override
    public List<T> collectData() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getDefaultFocus()
     */
    @Override
    public JComponent getDefaultFocus() {
        return null;
    }

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getHelpId()
     */
    @Override
    public abstract String getHelpId();

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#getTitle()
     */
    @Override
    public abstract String getTitle();

    /**
     * (non-Javadoc)
     * 
     * @see com.ycignp.veapo.client.framework.module.mvc.validate.AbstractValidateView#getDefaultButton()
     */
    @Override
    public JButton getDefaultButton() {
        return null;
    }

    /**
     * ��ȡ�洢����Tabҳ��MAP
     * @return <code>Map<String, List<AbstractTabController<?, ?, ?>>></code> �洢����Tabҳ��MAP
     */
    public Map<String, List<AbstractTabController<?, ?, ?>>> getTabMap() {
        return tabMap;
    }

    /**
     * ��ȡ��ǰѡ��ĵ������ڵ�
     * @return <code>Node</code> ��ǰѡ��ĵ������ڵ�
     */
    public Node getSelectedNode() {
        return selectedNode;
    }
}
