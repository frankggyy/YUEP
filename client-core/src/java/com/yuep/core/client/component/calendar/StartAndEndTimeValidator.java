/*
 * $Id: StartAndEndTimeValidator.java, 2009-7-9 ÏÂÎç04:19:31 aaron lee Exp $
 * 
 * Copyright (c) 2008 Wuhan Yangtze Optical Technology Co., Ltd 
 * All rights reserved.
 * 
 * This software is copyrighted and owned by YOTC or the copyright holder
 * specified, unless otherwise noted, and may not be reproduced or distributed
 * in whole or in part in any form or medium without express written permission.
 */
package com.yuep.core.client.component.calendar;

import java.awt.Window;
import java.util.Date;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.util.DialogUtils;

/**
 * <p>
 * Title: StartAndEndTimeValidator
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author aaron lee
 * created 2009-7-9 ÏÂÎç04:19:31
 * modified [who date description]
 * check [who date description]
 */
public class StartAndEndTimeValidator {

    private final JCalendar startCalendar;
    private final JCalendar endCalendar;
    private Window parent;

    public StartAndEndTimeValidator(Window parent, final JCalendar startCalendar, final JCalendar endCalendar) {
        this.startCalendar = startCalendar;
        this.endCalendar = endCalendar;

        endCalendar.txtDate.getDocument().addDocumentListener(new DocumentListener() {
            DocumentListener documentListener = this;

            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validateTimeRange()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            endCalendar.txtDate.getDocument().removeDocumentListener(documentListener);
                            endCalendar.clearTime();
                            endCalendar.txtDate.getDocument().addDocumentListener(documentListener);
                        }

                    });

                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }
        });

        startCalendar.txtDate.getDocument().addDocumentListener(new DocumentListener() {
            
            DocumentListener documentListener = this;
            
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!validateTimeRange()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            startCalendar.txtDate.getDocument().removeDocumentListener(documentListener);
                            startCalendar.clearTime();
                            startCalendar.txtDate.getDocument().addDocumentListener(documentListener);
                        }
                    });

                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

        });
    }

    public void setParent(Window parent) {
        this.parent = parent;
    }
    
    private boolean validateTimeRange() {
        Date startDate = startCalendar.getDate();
        Date endDate = endCalendar.getDate();
        if (startDate == null)
            return true;
        if (endDate == null)
            return true;
        if (startDate.after(endDate)) {
            DialogUtils.showErrorDialog(parent, ClientCoreContext.getString(
                    "end time must be later than start time"));
            return false;
        }
        return true;
    }
}
