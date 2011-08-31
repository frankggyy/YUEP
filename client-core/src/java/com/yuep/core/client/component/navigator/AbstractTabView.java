/*
 * $Id: AbstractTabView.java, 2009-3-16 ����10:55:45 Victor Exp $
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
 * Description:�����ؼ���չʾ�������ݵ�TabҳView�ĳ�����
 * </p>
 * 
 * @author Victor,aaron
 * created 2009-3-16 ����10:55:45
 * @modified aaron lee 2010-3-30 ����ʵ��<code>AbstractClientView<T></code>�ӿ�
 * check [who date description]
 */
public abstract class AbstractTabView<T> extends AbstractClientView<T> implements Observer, PropertyChangeListener {
	private static final long serialVersionUID = 8759339700075348959L;
	/**
	 * ���еĿ�У��ؼ��б�
	 */
	private List<XEditor> editors = new ArrayList<XEditor>();
	/**
	 * ������ѡ�еĽڵ�
	 */
	private Node selectNode = null;
	/**
	 * �Ƿ��Ѿ��޸Ĺ�������Ϣ
	 */
	private boolean isModified = false;

	/**
	 * ��ȡ�ý����������а�ť����Ϣ�����ݽ�����Ϣ������°�ť״̬
	 * 
	 * @return <code>JButton[]</code> ���а�ť��Ϣ�б�
	 */
	protected abstract JButton[] getSensitiveButtons();

	/**
	 * ��ѡ�еĵ������ڵ���Ϣ
	 * 
	 * @return <code>Node</code> ��ѡ�еĵ������ڵ�
	 */
	public Node getSelectNode() {
		return selectNode;
	}

	/**
	 * ���õ�������ѡ�еĽڵ�
	 * 
	 * @param selectNode
	 *            the selectNode to set
	 */
	public void setSelectNode(Node selectNode) {
		this.selectNode = selectNode;
	}

	/**
	 * ��ȡtab��ǩ���ƣ����ֻ��һ����ǩ������ʾ������Ϊ�գ�������Ϊ��
	 * 
	 * @return Tab��ǩ������
	 */
	public String getTabTitle() {
		return getTitle();
	}

	/**
	 * ��Ӱ�ť��listener
	 * 
	 * @param <V>
	 * @param <M>
	 * @param controller
	 */
	protected abstract <V, M> void addButtonListener(ClientController<T, V, M> controller);

	/**
	 * ��ʼ����������
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
		// �����saveButton��Ϊ������
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
	 * ��ȡҳ�������Ϣ�ı���
	 * 
	 * @return <code>String</code> ������Ϣ�ı���
	 */
	protected String getHeader() {
		return getTitle();
	}

	/**
	 * ��ȡҳ�������Ϣ������
	 * 
	 * @return <code>String</code> ������Ϣ������
	 */
	protected abstract String getDescription();

	/**
	 * ������Ϣ��ͼƬ��
	 * 
	 * @return <code>String</code> ������Ϣ��ͼƬ��
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
	 * �����������״̬����Ҫ����һ��checkbox����һЩ����Ƿ���õĳ���
	 * </p>
	 * <code>
     *      updateEnabled(checkbox1.isEnabled(), checkbox2, label1, textfeild1);
     * </code>
	 * 
	 * @param enabled
	 *            ����������õ�ʹ��״̬
	 * @param components
	 *            ���
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
	 * ��ȡ<code>cs</code>�����п��Զ�У��ؼ�
	 * 
	 * @param cs
	 *            �ؼ�����
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
	 * ����<code>cs</code>�����п��Զ�У��ؼ�
	 * 
	 * @param cs
	 *            �ؼ�����
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
	 * ������д����������Ը���У����Ϣ�仯��ť״̬��,ÿ��У�鶼�����
	 * 
	 * @param enabled
	 *            У����������Ľ��,���пؼ�У��ͨ������true������Ϊfalse
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
	 * �������а�ť��ʹ��״̬
	 * 
	 * @param enabled
	 *            ʹ��״̬
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
	 * ��ʼ���Զ�У��ؼ���PropertyChangeListener
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
	 * ɾ���Զ�У��ؼ���PropertyChangeListener
	 */
	public void removePorpertyChangeListener() {
		for (XEditor editor : editors) {
			editor.removePropertyChangeListener(editor.getPropertyName(), this);
		}
	}

	/**
	 * ���ý�����Ϣ�Ƿ񱻱༭��
	 * 
	 * @param isModified
	 *            �Ƿ񱻱༭��
	 */
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}

	/**
	 * ��ȡ������Ϣ�Ƿ񱻱༭��
	 * 
	 * @return �༭������<code>true</code>�����򷵻�<code>false</code>
	 */
	public boolean isModified() {
		return isModified;
	}

	/**
	 * ���û�����Panel�ڵ��������
	 * 
	 * @param panel
	 * @param isEnable
	 * @param exceptList
	 *            ������б�
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
