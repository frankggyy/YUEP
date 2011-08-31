/*
 * $Id: EnumComboBoxTableCellEditor.java, 2009-12-7 上午11:00:27 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table.editor;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.component.table.renderer.EnumComboBoxRenderer;

/**
 * 表格中枚举下拉框的editor
 * 
 * @author jimsu
 * 
 */
public class EnumComboBoxTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private static final long serialVersionUID = 7779029290458245881L;

    private JComboBox comboBox = new JComboBox();
    private Class<? extends Enum<?>> enumClazz;

    public EnumComboBoxTableCellEditor(Class<? extends Enum<?>> enumClazz) {
        this.enumClazz = enumClazz;

        EnumComboBoxRenderer renderer = new EnumComboBoxRenderer();
        comboBox.setRenderer(renderer);
    }

    /**
     * (non-Javadoc)
     * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        comboBox.removeAllItems();
        try {
            Enum<?>[] enums = (Enum<?>[]) enumClazz.getMethod("values").invoke(null);
            for (int i = 0; i < enums.length; i++) {
                comboBox.addItem(enums[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!(table instanceof XTable)){
            System.out.println("table is not XTable");
            return comboBox;
        }
        XTable xTable = (XTable) table;
        Object valueAt = xTable.getValueAt(row, column);
        if (valueAt != null && valueAt instanceof Enum) {
            Enum data = (Enum) valueAt;
            comboBox.setSelectedItem(data);
        } else {
            comboBox.setSelectedItem(null);
        }
        comboBox.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        comboBox.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        return comboBox;
    }
    /**
     * (non-Javadoc)
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    @Override
    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }

}
