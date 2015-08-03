package cn.zhangzuofeng.ZAlarm.ui;

import cn.zhangzuofeng.ZAlarm.AlarmMain;
import cn.zhangzuofeng.ZAlarm.regularBreaks.RegularBreaksConfigEntry;
import cn.zhangzuofeng.ZAlarm.util.ZResource;
import cn.zhangzuofeng.daily.ui.DailyDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

public enum AlarmSystemTray implements Serializable {

    instance;

    private MenuItem exitMenuItem;
    private MenuItem detailSettingMenuItem;
    private TrayIcon trayIcon;
    private MenuItem dailyMenuItem;

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public void create() {
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu popupMenu = createPopupMenu();

        trayIcon = new TrayIcon(ZResource.getImage("systemTray"), ZResource.getAppName(), popupMenu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        loadAction();
    }

    private void loadAction() {
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        detailSettingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DetailSettingDialog dialog = new DetailSettingDialog();
                RegularBreaksConfigEntry dataEntry = AlarmMain.regularBreaksTimer.getDataEntry();
                dialog.setData(dataEntry);
                dialog.pack();
                dialog.setSize(400, 450);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        dailyMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DailyDialog dailyDialog = new DailyDialog(null);
                dailyDialog.setAlwaysOnTop(true);
                dailyDialog.setLocationRelativeTo(null);
                dailyDialog.pack();
                dailyDialog.setSize(600, 400);
                dailyDialog.setVisible(true);
            }
        });
    }

    private PopupMenu createPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(ZResource.getAppName());

        detailSettingMenuItem = new MenuItem(ZResource.getLabel("detailSetting"));
        popupMenu.add(detailSettingMenuItem);

        dailyMenuItem = new MenuItem(ZResource.getLabel("dailyMission"));
        popupMenu.add(dailyMenuItem);

        exitMenuItem = new MenuItem(ZResource.getLabel("exit"));
        popupMenu.add(exitMenuItem);

        return popupMenu;
    }
}
