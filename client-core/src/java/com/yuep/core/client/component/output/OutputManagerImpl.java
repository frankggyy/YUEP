/*
 * $Id: OutputManagerImpl.java, 2009-2-26 下午03:38:30 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.output;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

import org.apache.commons.lang.StringUtils;

import com.yuep.core.client.ClientCoreContext;

/**
 * <p>
 * Title: OutputManagerImpl
 * </p>
 * <p>
 * Description:主界面最底下的输出域
 * </p>
 * 
 * @author aaron lee
 * created 2009-2-26 下午03:38:30
 * modified [who date description]
 * check [who date description]
 */
public class OutputManagerImpl implements OutputManager {

    private JPanel outputPane;
    private JTextArea textArea;
    private JPopupMenu popupMenu;
    private JScrollPane scrollPane;
    private boolean autoScroll = true;
    private int maxLines;
    private String lastMsg;

    public OutputManagerImpl() {
        initConfig();
        constructUI();
    }

    public void initConfig() {
        autoScroll = true;
        String strMaxLine = "100000000";
        if (StringUtils.isNumeric(strMaxLine) && strMaxLine.trim().length() > 0) {
            maxLines = Integer.valueOf(strMaxLine);
        } else {
            maxLines = 10000;
        }
    }

    protected void constructUI() {
        outputPane = new JPanel();
        outputPane.setLayout(new BorderLayout());
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        scrollPane.getViewport().add(textArea);
        outputPane.add(scrollPane, BorderLayout.CENTER);
        textArea.setMinimumSize(new Dimension(0, 0));
        outputPane.setMinimumSize(new Dimension(0, 0));
        textArea.setEditable(false);
        Color color = new Color(252, 254, 171);
        textArea.setBackground(color);
        textArea.setOpaque(true);
        textArea.setDoubleBuffered(true);
        popupMenu = new JPopupMenu();
        popupMenu.add(new ClearMessageAction());
        if (isAutoScroll()) {

        } else {

        }
        textArea.setComponentPopupMenu(popupMenu);
    }
    
    class ClearMessageAction extends AbstractAction{
        
        private static final long serialVersionUID = -628157462133540487L;

        public ClearMessageAction(){
            putValue(NAME, ClientCoreContext.getString("common.output.clearmessage"));
        }
        /**
         * (non-Javadoc)
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.setText("");
        }
        
    }

    public JComponent getView() {
        return outputPane;
    }

    public void addSystemMessage(Object msg) {
        addLine("[" + ClientCoreContext.getString("common.output.system.message") + "]", String.valueOf(msg));
    }

    public void addOperationMessage(Object operation) {
        addLine("[" + ClientCoreContext.getString("common.output.operation.message") + "]", String.valueOf(operation));
    }

    private void addLine(final String prefix, final String msg) {
        final int caretPosition = textArea.getCaretPosition();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                String newMsg = new StringBuffer().append(prefix).append(" ").append(
                    " ").append(msg).toString();
                if (newMsg.equals(lastMsg)) {
                    return;
                }
                //Rectangle rect = textArea.getVisibleRect();
                lastMsg = newMsg;
                if (textArea.getText().length() > 0) {
                    textArea.append(System.getProperty("line.separator"));
                }

                if (isAutoScroll() && textArea.getText().length() != 0
                    && textArea.getText().length() == textArea.getCaretPosition()) {
                }
                textArea.append(newMsg);
                int fitLength = fitLineCount();
                int newCaretPosition = caretPosition - fitLength;
                if (isAutoScroll()) {
                    textArea.select(textArea.getText().length(), textArea.getText().length());
                } else {
                    textArea.select(newCaretPosition, newCaretPosition);
                }
            }

        });
    }

    private int fitLineCount() {
        if (textArea == null) {
            return 0;
        }
        int iCount = textArea.getLineCount();
        if (iCount > maxLines) {
            try {
                Rectangle rect = textArea.getVisibleRect();
                int length = textArea.getLineStartOffset(iCount - maxLines);
                textArea.replaceRange("", 0, length);
                return length;
            } catch (BadLocationException e) {
                System.err.println(e.getMessage());
            }
        }
        return 0;
    }

    public void clearSelected() {
        textArea.replaceSelection("");
    }

    public void clear() {
        textArea.setText("");
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
        this.textArea.setAutoscrolls(autoScroll);
    }

    public void setMaxLines(int maxLines) {
        if (this.maxLines != maxLines) {
            this.maxLines = maxLines;
            fitLineCount();
        }
    }

    public int getMaxLines() {
        return maxLines;
    }

    public void setHeight(int height) {

    }
}
