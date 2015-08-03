package cn.zhangzuofeng.ZAlarm;

import cn.zhangzuofeng.ZAlarm.regularBreaks.RegularBreaksTimer;
import cn.zhangzuofeng.ZAlarm.ui.AlarmSystemTray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;


public class AlarmMain {
    private static AlarmSystemTray alarmSystemTray;

    public static RegularBreaksTimer regularBreaksTimer = null;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.addAWTEventListener(new AWTEventListener() {
                public void eventDispatched(AWTEvent e) {
                    System.out.println(((KeyEvent) e).getKeyCode());
                    if (((KeyEvent) e).getKeyCode() == KeyEvent.VK_F1)
                        System.out.println("F1");
                    if (((KeyEvent) e).getKeyCode() == KeyEvent.VK_F2)
                        System.out.println("F2");
                }
            }, AWTEvent.KEY_EVENT_MASK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread() {
            @Override
            public void run() {
                alarmSystemTray = AlarmSystemTray.instance;
                alarmSystemTray.create();
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                regularBreaksTimer = RegularBreaksTimer.instance;
                regularBreaksTimer.readDataEntryFromConfigFile();
                regularBreaksTimer.begin();
            }
        }.start();

    }
}
