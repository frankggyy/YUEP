/*
 * $Id: XTableUtils.java, 2011-4-26 上午10:21:00 sufeng Exp $
 * 
 * Copyright (c) 2010 Wuhan Yangtze Communications Industry Group Co.,Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YCIG or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.table;

import java.awt.Window;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.table.renderer.XTableCellRenderer;
import com.yuep.core.client.component.window.WaitingDialog;
import com.yuep.core.client.util.DialogUtils;
import com.yuep.core.client.util.GuiConstants;

/**
 * <p>
 * Title: XTableUtils
 * </p>
 * <p>
 * Description:导出表格...
 * </p>
 * 
 * @author sufeng
 * created 2011-4-26 上午10:21:00
 * modified [who date description]
 * check [who date description]
 */
public class XTableUtils {

    /**
     * 字符串转换方法
     * 
     * @param name
     *            需要转换的字符串
     * @return String 转换后的字符串
     * @author aaron lee
     */
    public static String capitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
    /**
     * 导出XTable数据到CSV、TXT文件
     * 
     * @param table
     *            要导出的数据表
     * @param clazz
     *            要导出的数据模型
     * @param notAddFields
     *            不需要导出的字段集合
     * @param renders
     *            需要特殊处理的字段渲染器集合
     * @param owner
     *            父窗体
     */
    @SuppressWarnings("unchecked")
    public static void expertTable2File(final XTable table, final Class clazz,
            final List<String> notAddFields, final Map<String, XTableCellRenderer> renders,
            final Window owner) {
        final String LINE = "\r\n";
        final String SPT = ", ";

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(ClientCoreContext.getString("filedialog.save"));
        FileNameExtensionFilter fileFilter1 = new FileNameExtensionFilter("csv", "csv");
        FileNameExtensionFilter fileFilter2 = new FileNameExtensionFilter("txt", "txt");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(fileFilter2);
        fileChooser.setFileFilter(fileFilter1);

        int dlgresult = fileChooser.showSaveDialog(owner);
        if (dlgresult != JFileChooser.APPROVE_OPTION)
            return;

        String path = fileChooser.getSelectedFile().getAbsolutePath();
        String formatName = fileChooser.getFileFilter().getDescription();
        if (!(path.toLowerCase().endsWith(".txt") || path.toLowerCase().endsWith("csv")))
            path += "." + formatName;
        final File file = new File(path);
        if (file.exists()) {
            if (!(DialogUtils.showConfirmDialog(owner, ClientCoreContext.getString("filedialog.fileexist")) == GuiConstants.RetValues.YES_OPTION)) {
                return;
            }
        }

        final WaitingDialog waitingDialog = DialogUtils.showWaitingDialog(false, false, null, owner);
        new SwingWorker<StringBuilder, Void>() {
            @Override
            protected StringBuilder doInBackground() throws Exception {
                List datas = table.getAllDatas();
                if (datas == null)
                    return null;
                StringBuilder sb = new StringBuilder();
                Field[] fields = clazz.getDeclaredFields();

                List<Field> fieldList = new ArrayList<Field>();

                for (Field field : fields) {
                    String fieldName = field.getName();
                    int modifiers = field.getModifiers();
                    boolean isStatic = Modifier.isStatic(modifiers);
                    if (isStatic)
                        continue;
                    if (fieldName.equals("serialVersionUID"))
                        continue;
                    if (notAddFields != null && notAddFields.contains(fieldName))
                        continue;
                    fieldList.add(field);
                }

                int len = fieldList.size() - 1;
                int index = 0;
                for (Field field : fieldList) {
                    String fieldName = field.getName();
                    String className = clazz.getSimpleName();
                    String columnName = ClientCoreContext.getString(className + "." + fieldName);
                    sb.append(columnName);
                    if (index == len)
                        sb.append(LINE);
                    else
                        sb.append(SPT);
                    index++;
                }

                for (Object data : datas) {
                    index = 0;
                    for (Field field : fieldList) {
                        String fieldName = field.getName();
                        Object value = null;
                        try {
                            Method method = null;
                            try {
                                method = clazz.getMethod("get" + capitalize(fieldName));
                            } catch (Exception e) {
                                method = clazz.getMethod("is" + capitalize(fieldName));
                            }
                            value = method.invoke(data);
                            if (renders != null && renders.containsKey(fieldName)) {
                                String tmp = null;
                                tmp = renders.get(fieldName).getRenderVaule(value);
                                if (tmp != null)
                                    value = tmp;
                            }
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        if (value != null) {
                            String result = value.toString();
                            if (result.contains("\n") || result.contains("\t") || result.contains(",")) {
                                result = result.replaceAll("\n", "");
                                result = result.replaceAll("\t", "");
                                result = result.replaceAll(",", " ");
                            }
                            sb.append(result);
                        }
                        if (index == len)
                            sb.append(LINE);
                        else
                            sb.append(SPT);
                        index++;
                    }
                }

                return sb;
            }

            @Override
            protected void done() {
                super.done();
                waitingDialog.closeWaitingDialog();
                try {
                    StringBuilder sb = get();
                    if (sb == null)
                        return;
                    BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
                    fileWriter.write(sb.toString());
                    fileWriter.flush();
                    fileWriter.close();
                    DialogUtils.showInfoDialog(owner, ClientCoreContext.getString("filedialog.complete"));
                } catch (Exception ex) {
                    DialogUtils.showErrorDialog(owner, ex.getMessage());
                    ex.printStackTrace();
                }
            }

        }.execute();
    }
    
}
