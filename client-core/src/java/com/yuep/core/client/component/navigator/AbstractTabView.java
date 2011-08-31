/*
 * $Id: AbstractTabView.java, 2009-3-16 上午10:55:45 Victor Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.navigator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import twaver.Node;

import com.yuep.core.client.component.validate.editor.XEditor;
import com.yuep.core.client.mvc.AbstractClientView;
import com.yuep.core.client.mvc.ClientController;

/**
 * <p>
 * Title: AbstractTabView
 * </p>
 * <p>
 * Description:导航控件中展示具体内容的Tab页View的抽象类
 * </p>
 * 
 * @author Victor,aaron
 * created 2009-3-16 上午10:55:45
 * @modified aaron lee 2010-3-30 将其实现<code>AbstractClientView<T></code>接口
 * check [who date description]
 */
public abstract class AbstractTabView<T> extends AbstractClientView<T> implements Observer, PropertyChangeListener {
	private static final long serialVersionUID = 8759339700075348959L;
	/**
	 * 所有的可校验控件列表
	 */
	private List<XEditor> editors = new ArrayList<XEditor>();
	/**
	 * 导航树选中的节点
	 */
	private Node selectNode = null;
	/**
	 * 是否已经修改过界面信息
	 */
	private boolean isModified = false;

	/**
	 * 获取该界面所有敏感按钮的信息，根据界面信息错误更新按钮状态
	 * 
	 * @return <code>JButton[]</code> 敏感按钮信息列表
	 */
	protected abstract JButton[] getSensitiveButtons();

	/**
	 * 被选中的导航树节点信息
	 * 
	 * @return <code>Node</code> 被选中的导航树节点
	 */
	public Node getSelectNode() {
		return selectNode;
	}

	/**
	 * 设置导航树被选中的节点
	 * 
	 * @param selectNode
	 *            the selectNode to set
	 */
	public void setSelectNode(Node selectNode) {
		this.selectNode = selectNode;
	}

	/**
	 * 获取tab标签名称，如果只有一个标签将不显示，可以为空，否则不能为空
	 * 
	 * @return Tab标签的名字
	 */
	public String getTabTitle() {
		return getTitle();
	}

	/**
	 * 添加按钮的listener
	 * 
	 * @param <V>
	 * @param <M>
	 * @param controller
	 */
	protected abstract <V, M> void addButtonListener(ClientController<T, V, M> controller);

	/**
	 * 初始化界面数据
	 * 
	 * @param data
	 */
	protected void initializeData(List<T> data) {

	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.ycignp.veapo.client.framework.module.mvc.ClientView#addListener(com.ycignp.veapo.client.framework.module.mvc.ClientController)
	 */
	@Override
	public final <V, M> void addListener(ClientController<T, V, M> controller) {
		addButtonListener(controller);
		// 保存后saveButton置为不可用
		JButton[] sensitiveButtons = getSensitiveButtons();
		if (ArrayUtils.isEmpty(sensitiveButtons))
			return;
		for (final JButton sensitiveButton : sensitiveButtons) {
			sensitiveButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (sensitiveButton.isEnabled())
						sensitiveButton.setEnabled(false);
				}
			});
		}
	}

	/**
	 * 获取页面介绍信息的标题
	 * 
	 * @return <code>String</code> 介绍信息的标题
	 */
	protected String getHeader() {
		return getTitle();
	}

	/**
	 * 获取页面介绍信息的正文
	 * 
	 * @return <code>String</code> 介绍信息的正文
	 */
	protected abstract String getDescription();

	/**
	 * 介绍信息的图片名
	 * 
	 * @return <code>String</code> 介绍信息的图片名
	 */
	protected String getMessageLogoName() {
		return "";
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see com.yotc.opview.framework.client.component.module.ClientView#initData(java.util.List)
	 */
	@Override
	public final void initData(List<T> boList) {
		JButton[] sensitiveButtons = getSensitiveButtons();
		if (sensitiveButtons != null) {
			for (JButton button : sensitiveButtons) {
				button.setEnabled(false);
			}
		}
		initializeData(boList);
	}

	/**
	 * <p>
	 * 更新组件可用状态，主要用于一个checkbox控制一些组件是否可用的场景
	 * </p>
	 * <code>
     *      updateEnabled(checkbox1.isEnabled(), checkbox2, label1, textfeild1);
     * </code>
	 * 
	 * @param enabled
	 *            后面组件设置的使能状态
	 * @param components
	 *            组件
	 */
	protected void updateEnabled(boolean enabled, JComponent... components) {
		if (components == null || components.length == 0) {
			return;
		}
		for (JComponent jc : components) {
			if (jc != null) {
				jc.setEnabled(enabled);
			}
		}
	}

	/**
	 * 获取<code>cs</code>内所有可自动校验控件
	 * 
	 * @param cs
	 *            控件容器
	 */
	private void getAllEditors(Component[] cs) {
		for (Component c : cs) {
			// logger.debug("" + c);
			if (c instanceof XEditor && !editors.contains(c)) {
				editors.add((XEditor) c);
				continue;
			} else if (c instanceof JComponent) {
				JComponent jp = (JComponent) c;
				getAllEditors(jp.getComponents());
			} else {
				System.out.println("Unknown Components:" + c.getClass());
			}
		}
	}

	/**
	 * 重置<code>cs</code>内所有可自动校验控件
	 * 
	 * @param cs
	 *            控件容器
	 */
	public void resetAllEditors(Component[] cs) {
		for (Component c : cs) {
			// logger.debug("" + c);
			if (c instanceof XEditor && !editors.contains(c)) {
				XEditor editor = (XEditor) c;
				editor.setInitialized(false);
				editor.initValidateRequire();
				continue;
			} else if (c instanceof JComponent) {
				JComponent jp = (JComponent) c;
				resetAllEditors(jp.getComponents());
			} else {
				System.out.println("Unknown Components:" + c.getClass());
			}
		}
	}

	/**
	 * 子类重写这个方法可以根据校验信息变化按钮状态等,每次校验都会调用
	 * 
	 * @param enabled
	 *            校验整个界面的结果,所有控件校验通过返回true，否则为false
	 */
	protected void validated(boolean enabled) {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setSensitiveButtonEnabled(isCommited(evt.getPropertyName()));
	}

	/**
	 * 设置敏感按钮的使能状态
	 * 
	 * @param enabled
	 *            使能状态
	 */
	public void setSensitiveButtonEnabled(boolean enabled) {
		JButton[] sensitiveButtons = getSensitiveButtons();
		if (ArrayUtils.isEmpty(sensitiveButtons))
			return;
		for (JButton sensitiveButton : sensitiveButtons) {
			sensitiveButton.setEnabled(enabled);
		}
	}

	private boolean isCommited(String propertyName) {
		if (isJudge(propertyName)) {
			List<XEditor> editors = new ArrayList<XEditor>();
			Set<Boolean> set = new HashSet<Boolean>();
			for (XEditor editor : this.editors) {
				if (editor.isModified()) {
					editors.add(editor);
				}
			}
			for (XEditor editor : editors) {
				set.add(editor.isCommited());
			}
			if (!CollectionUtils.isEmpty(set)) {
				if (set.size() == 2)
					return false;
				else
					return set.iterator().next();
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private boolean isJudge(String propertyName) {
		for (XEditor editor : editors) {
			if (editor.getPropertyName().equals(propertyName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化自动校验控件的PropertyChangeListener
	 */
	public void initPropertyChangeListener() {
		Component[] components = this.getComponents();
		editors.clear();
		getAllEditors(components);
		for (XEditor editor : editors) {
			editor.addPropertyChangeListener(editor.getPropertyName(), this);
		}
	}

	/**
	 * 删除自动校验控件的PropertyChangeListener
	 */
	public void removePorpertyChangeListener() {
		for (XEditor editor : editors) {
			editor.removePropertyChangeListener(editor.getPropertyName(), this);
		}
	}

	/**
	 * 设置界面信息是否被编辑过
	 * 
	 * @param isModified
	 *            是否被编辑过
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	/**
	 * 获取界面信息是否被编辑过
	 * 
	 * @return 编辑过返回<code>true</code>，否则返回<code>false</code>
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * 禁用或启用Panel内的所有组件
	 * 
	 * @param panel
	 * @param isEnable
	 * @param exceptList
	 *            列外的列表
	 */
	public void setEditabled(JPanel panel, boolean isEnable, List<JComponent> exceptList) {
		if (exceptList == null)
			exceptList = new ArrayList<JComponent>();
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JPanel) {
				setEditabled((JPanel) comp, isEnable, exceptList);
			} else if (!exceptList.contains(comp)) {
				comp.setEnabled(isEnable);
			}
		}
	}
}
