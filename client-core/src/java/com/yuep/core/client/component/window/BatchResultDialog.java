/*
 * $Id: BatchResultView.java, 2009-12-18 下午01:22:15 yangtao Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.yuep.base.exception.ExceptionUtils;
import com.yuep.base.exception.YuepBatchException;
import com.yuep.base.exception.YuepException;
import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.SwingFactory;
import com.yuep.core.client.component.factory.decorator.button.ButtonDecorator;
import com.yuep.core.client.component.table.XTable;
import com.yuep.core.client.util.XGuiUtils;

/**
 * <p>
 * Title: BatchResultView
 * </p>
 * <p>
 * Description:批量处理结果显示对话框
 * </p>
 * 
 * @author yangtao
 * created 2009-12-18 下午01:22:15
 * modified [who date description]
 * check [who date description]
 */
public class BatchResultDialog extends JDialog {
 
    private static final long serialVersionUID = -583866883205723336L;

    private final YuepBatchException yotcBatchException;
    private final XTable<ClientBatchResult> clientBatchResultsTable = ClientCoreContext.getSwingFactory()
            .getXTableEditor("", ClientBatchResult.class, null, "",
                    new String[] { "target", "result", "cause", "solution" });
    private JButton closeBtn;

    public BatchResultDialog(Window parentWindow, YuepBatchException yotcBatchException) {
        super(parentWindow);
        this.yotcBatchException = yotcBatchException;
        initGui();
        initAction();
        initValues();
    }

    private void initGui() {
        this.setSize(500, 300);
        this.setTitle(ClientCoreContext.getString("common.batch.result.title"));
        SwingFactory swingFactory = ClientCoreContext.getSwingFactory();
        this.setLayout(swingFactory.getBorderLayout());
        XGuiUtils.centerWindow(this);
        JPanel contentPanel = swingFactory.getPanel();
        this.add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(swingFactory.getBorderLayout());
        contentPanel.add(swingFactory.getScrollPane(clientBatchResultsTable), BorderLayout.CENTER);
        JPanel btnPanel = swingFactory.getPanel();
        contentPanel.add(btnPanel, BorderLayout.SOUTH);
        btnPanel.setLayout(swingFactory.getFlowLayout(FlowLayout.RIGHT));
        closeBtn = swingFactory.getButton(new ButtonDecorator("common.button.close",'C'));
        btnPanel.add(closeBtn);
    }

    private void initAction() {
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BatchResultDialog.this.dispose();
            }
        });
    }

    private void initValues() {
        clientBatchResultsTable.addRowDatas(getClientBatchResults(yotcBatchException));
    }

    private List<ClientBatchResult> getClientBatchResults(YuepBatchException yotcBatchException) {
        final List<ClientBatchResult> clientBatchResults = new ArrayList<ClientBatchResult>();
        final Map<Object, Throwable> throwables = yotcBatchException.getThrowables();
       
        for(Map.Entry<Object, Throwable> entry : throwables.entrySet()){
            ClientBatchResult clientBatchResult = new ClientBatchResult();
            Throwable throwable = entry.getValue();
            clientBatchResult.setResult(ClientCoreContext.getString("common.failed"));
            if (throwable instanceof YuepException) {
                YuepException yotcException = (YuepException) throwable;
                String message = XGuiUtils.getMessageByErrorCode(yotcException.getErrorCode(), yotcException
                        .getSource());
                String solution = XGuiUtils.getSolutionByErrorCode(yotcException.getErrorCode(), yotcException
                        .getSource());
                clientBatchResult.setCause(message);
                clientBatchResult.setSolution(solution);
            } else if (throwable instanceof Exception) {
                String message = ExceptionUtils.getExFirstStackInfo((Exception) throwable);
                clientBatchResult.setCause(message);
            } else {
                String message = throwable.getMessage();
                clientBatchResult.setCause(message);
            }
            clientBatchResults.add(clientBatchResult);
        }
        return clientBatchResults;

    }

}
