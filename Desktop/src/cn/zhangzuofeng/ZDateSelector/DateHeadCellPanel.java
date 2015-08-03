package cn.zhangzuofeng.ZDateSelector;

import javax.swing.*;
import java.awt.*;

public class DateHeadCellPanel extends JPanel {
    private Font textFont;

    public Font getTextFont() {
        return textFont;
    }

    public void setTextFont(Font textFont) {
        this.textFont = textFont;
    }

    public DateHeadCellPanel() {
        initComponents();
    }

    private void initComponents() {
        this.setOpaque(false);
        textField.setBorder(null);
//        textField.setOpaque(false);
        textField.setEditable(false);
        textField.setFocusable(false);
        textField.setFont(textFont);
        textField.setColumns(4);
        textField.setHorizontalAlignment(JTextField.CENTER);
        add(textField);
    }

    private JTextField textField = new JTextField();

    public void setText(String s) {
        this.textField.setText(s);
    }
}
