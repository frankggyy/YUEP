package com.yuep.core.client.component.separator;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import com.yuep.core.client.ClientCoreContext;
import com.yuep.core.client.component.factory.decorator.label.LabelDecorator;
import com.yuep.core.client.component.layout.XTableLayout;

/**
 * 
 * <p>
 * Title: XTitledSeparator
 * </p>
 * <p>
 * Description:���б���ķָ���
 * </p>
 * 
 * @author aaron lee
 * created 2010-3-26 ����02:12:14
 * modified [who date description]
 * check [who date description]
 */
public class XTitledSeparator extends JPanel {

    private static final long serialVersionUID = 2015477187754778821L;

    /**
     * �ָ���
     */
    private JSeparator separator = ClientCoreContext.getSwingFactory().getSeparator();
    /**
     * ��ʾ�����Label
     */
    private JLabel titleLabel;

    /**
     * ���췽��
     * 
     * @param title
     *            ����
     */
    public XTitledSeparator(String title) {
        titleLabel = ClientCoreContext.getSwingFactory().getLabel(new LabelDecorator(title));
        String titleStr = ClientCoreContext.getString(title);
        Font font = UIManager.getFont("Label.font");
        FontMetrics fontMetrics = titleLabel.getFontMetrics(font);
        int stringWidth = fontMetrics.stringWidth(titleStr);
        LayoutManager tableLayout = ClientCoreContext.getSwingFactory().getTableLayout(
                new double[][] { { stringWidth + 10, XTableLayout.FILL }, { 18 } });
        setLayout(tableLayout);
        add(titleLabel, "0,0,f,c");
        add(separator, "1,0,f,c");
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.titleLabel.setEnabled(enabled);
        this.separator.setEnabled(enabled);
    }
}
