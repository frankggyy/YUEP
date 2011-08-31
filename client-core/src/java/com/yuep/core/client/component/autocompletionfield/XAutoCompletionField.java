/*
 * getXTextAreaEditor.java
 *
 * Created on 2007-6-21, 22:03:00
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yuep.core.client.component.autocompletionfield;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * �Զ�����ı��򣬷���ֵ�ͷ���ֵ�Զ���ʾ����������
 * 
 * @author William Chen
 */
public class XAutoCompletionField extends JTextField {

    private static final long serialVersionUID = -5472870294934567415L;
    private ListPopup popup;
    private int preferredHeight = 136;
    private int pageStep = 6;
    private transient CompletionFilter filter;

    private int minValue;
    private int maxValue;
    private int maxLength = 255;

    /**
     * ����������޶����뷶Χ
     * 
     * @param min
     * @param max
     */
    public XAutoCompletionField(int minValue, int maxValue) {
        if (minValue >= maxValue)
            throw new IllegalArgumentException("Arguments Exception: minValue >= maxValue");
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.setDocument(new AutoFieldDocument(Integer.class));
        init();
    }

    /**
     * �ַ���������޶���󳤶�255
     * 
     * @param maxLength
     */
    public XAutoCompletionField(int maxLength) {
        this.maxLength = (maxLength > 255 ? 255 : maxLength);
        this.setDocument(new AutoFieldDocument(String.class));
        init();
    }

    private void init() {
        popup = new ListPopup();

        // ʵʱ����
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                textChanged();
            }
        });

        // ˫����ʾ�����˵�
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1 && !popup.isVisible())
                    textChanged();
            }
        });

        // ����ѡ��һ��ʱ���رյ����˵�
        popup.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList list = (JList) e.getSource();
                String text = list.getSelectedValue().toString();
                setText(text);
                popup.setVisible(false);
            }
        });

        // �رղ˵�
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (popup.isVisible()) {
                    Object o = popup.getSelectedValue();
                    if (o != null)
                        setText(o.toString());
                    popup.setVisible(false);
                }
                XAutoCompletionField.this.selectAll();
                XAutoCompletionField.this.requestFocus();
            }
        });

        // ʵ�ּ������룺Up��Down��PageUp��PageDown
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (popup.isVisible()) {
                        if (!popup.isSelected())
                            popup.setSelectedIndex(0);
                        else
                            popup.setSelectedIndex(popup.getSelectedIndex() + 1);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (popup.isVisible()) {
                        if (!popup.isSelected())
                            popup.setLastOneSelected();
                        else
                            popup.setSelectedIndex(popup.getSelectedIndex() - 1);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                    if (popup.isVisible()) {
                        if (!popup.isSelected())
                            popup.setSelectedIndex(0);
                        else
                            popup.setSelectedIndex(popup.getSelectedIndex() + pageStep);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                    if (popup.isVisible()) {
                        if (!popup.isSelected())
                            popup.setLastOneSelected();
                        else
                            popup.setSelectedIndex(popup.getSelectedIndex() - pageStep);
                    }
                }
            }
        });
    }

    /**
     * ���ù��˶��󣬿ɸ�����Ҫ�Զ���ʵ��
     * 
     * @param filter
     */
    public void setFilter(CompletionFilter filter) {
        this.filter = filter;
    }

    /**
     * ���ú�ѡ�����б�
     * 
     * @param objList
     */
    public void setFilterElements(List objList) {
        filter = new DefaultCompletionFilter(objList);
    }

    private boolean isListChange(List array) {
        if (array.size() != popup.getItemCount()) {
            return true;
        }
        for (int i = 0; i < array.size(); i++) {
            if (!array.get(i).equals(popup.getItem(i))) {
                return true;
            }
        }
        return false;
    }

    private void textChanged() {
        if (!popup.isVisible()) {
            showPopup();
            requestFocus();
        }
        if (filter != null) {
            changeList(filter.filter(getText()));
        }
    }

    /**
     * �������֮ǰ�����ı������ݻ���ֵ����˵���λ�쳣 filter Ϊnullʱ���������˵�
     */
    private void showPopup() {
        if (filter == null)
            return;
        popup.setPopupSize(getWidth(), preferredHeight);
        try {
            popup.show(this, 0, getHeight());
        } catch (Exception e) {
            // do nothing
        }
    }

    private void changeList(List array) {
        if (array.size() == 0) {
            if (popup.isVisible()) {
                popup.setVisible(false);
            }
        } else {
            if (!popup.isVisible()) {
                showPopup();
            }
        }
        if (isListChange(array) && array.size() != 0) {
            popup.setList(array);
        }
    }

    /**
     * ����Ƿ�����Ϊ��
     * 
     * @return
     */
    private boolean isEmpty() {
        return getText().length() == 0;
    }

    private int getInt() {
        if (isEmpty())
            throw new NumberFormatException("FormatException: NumberTextField is empty");
        return Integer.parseInt(getText());
    }

    /**
     * String����
     */
    class AutoFieldDocument extends PlainDocument {
        private static final long serialVersionUID = -4676837994985065453L;

        boolean isDigitOnly = false;
        XAutoCompletionField tf = XAutoCompletionField.this;

        private AutoFieldDocument(Class<?> clazz) {
            isDigitOnly = (clazz == Integer.class);
        }

        /**
         * (non-Javadoc)
         * 
         * @see javax.swing.text.PlainDocument#insertString(int,
         *      java.lang.String, javax.swing.text.AttributeSet)
         */
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (isDigitOnly)
                insertDigit(offs, str, a);
            else
                insertStr(offs, str, a);
        }

        /**
         * �����ַ���
         * 
         * @param offs
         * @param str
         * @param a
         * @throws BadLocationException
         */
        private void insertStr(int offs, String str, AttributeSet a) throws BadLocationException {
            int thisLen = tf.getText().length();
            if (str == null || thisLen >= tf.maxLength)
                return;

            if (thisLen + str.length() > maxLength)
                str = str.substring(0, maxLength - thisLen);
            super.insertString(offs, str, a);

        }

        private void beep() {
            Toolkit.getDefaultToolkit().beep();
        }

        /**
         * �������֣��������⣺����ı������ݲ�Ϊ�գ�����λ���븺�Ų岻��
         * 
         * @param offs
         * @param str
         * @param a
         * @throws BadLocationException
         */
        private void insertDigit(int offs, String str, AttributeSet a) throws BadLocationException {
            String oldStr = getText(0, getLength());
            String newStr = null;

            boolean hasSign = oldStr.length() > 0;

            char[] source = str.toCharArray();
            char[] result = new char[source.length];
            int i = 0;

            for (char c : source) {
                if (!hasSign && source[0] == '-' || Character.isDigit(c)) {
                    hasSign = true;
                    result[i++] = c;
                } else {
                    break;
                }
            }

            newStr = new String(result, 0, i);

            /**
             * ������Χǿ��Ҫ�����븺��
             */
            if (tf.maxValue < 0 && oldStr.length() == 0 && source[0] != '-') {
                beep();
                return;
            }

            /**
             * �Ǹ�����Χ��ֹ���븺��
             */
            try {
                if (tf.minValue >= 0 && (source[0] == '-' || Integer.parseInt(oldStr + newStr) > tf.maxValue)) {
                    beep();
                    return;
                }
            } catch (Exception e) {
                beep();
                return;
            }

            // ���⣺ͨ��ճ�����Կ�����Ƿ�ֵ
            if (!tf.isEmpty()) {
                // TODO �ײ��������1��0
                if (newStr.startsWith("0") && tf.getInt() == 0) {
                    beep();
                    return;
                }
                // �����롾32λ������ֵ�����ȶ���Χ�����ֹ���룬�д�����
                // �� parseLong �õ���Ӧֵ
                long value = Long.parseLong(oldStr + newStr);
                if (tf.maxValue > 0 && value > tf.maxValue || tf.minValue < 0 && value < tf.minValue) {
                    beep();
                    return;
                }
            }
            super.insertString(offs, newStr, a);
        }
    }
}
