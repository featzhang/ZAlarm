package cn.zhangzuofeng.ZAlarm.ui;

import javax.swing.JButton;

public class IconButton extends JButton {

    public IconButton() {
        initConponents();
    }

    private void initConponents() {
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        this.setBorder(null);
    }

}
