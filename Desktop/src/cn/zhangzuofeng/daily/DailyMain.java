package cn.zhangzuofeng.daily;

import cn.zhangzuofeng.daily.ui.DailyDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Timestamp;
import javax.swing.UIManager;

public class DailyMain {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
        }
        DailyDialog dailyDialog = new DailyDialog(new Timestamp(System.currentTimeMillis()));
        dailyDialog.setTitle("DailyDialog");
        dailyDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        dailyDialog.pack();
        dailyDialog.setSize(600, 400);
        dailyDialog.setLocationRelativeTo(null);
        dailyDialog.setVisible(true);
    }
}
