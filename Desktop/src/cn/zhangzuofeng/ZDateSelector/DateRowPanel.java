package cn.zhangzuofeng.ZDateSelector;

import javax.swing.*;

public class DateRowPanel extends JPanel {
    public DateRowPanel() {
        initComponents();
    }

    private void initComponents() {
//        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);
    }
}
