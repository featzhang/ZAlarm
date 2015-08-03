package cn.zhangzuofeng.ZAlarm.ui.AlarmFrame;

import cn.zhangzuofeng.ZAlarm.util.ZResource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.TimerTask;

public class AlertFrame extends JFrame {

    private String msg = null;
    private int restTime;

    public AlertFrame(GraphicsConfiguration defaultConfiguration) {
        super(defaultConfiguration);
        initComponents();
        loadAction();
    }

    private void loadAction() {
        moveMouseToCenter();
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                System.err.println("lost focus!");
                AlertFrame.this.requestFocus();
                moveMouseToCenter();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                moveMouseToCenter();
            }
        });
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
                /*
                 GraphicsConfiguration graphicsConfiguration = AlertFrame.this.getGraphicsConfiguration();
                 Point location = AlertFrame.this.getLocation();
                 Dimension size = AlertFrame.this.getSize();
                 AlertFrame alertFrame = new AlertFrame(graphicsConfiguration);
                 alertFrame.setSize(size);
                 alertFrame.setLocation(location);
                 alertFrame.beginRestTimer(restTime);
                 AlertFrame.this.setVisible(false);
                 */
            }

        });
//        java.util.Timer timer = new java.util.Timer();
//        timer.schedule(new java.util.TimerTask() {
//
//            @Override
//            public void run() {
//                boolean focusableWindowState = AlertFrame.this.getFocusableWindowState();
//                System.out.println("focusableWindowState:" + focusableWindowState);
//                if (!focusableWindowState) {
//                    AlertFrame.this.setFocusableWindowState(true);
//                }
//                int state = AlertFrame.this.getExtendedState();
//                System.out.println("window state:" + state);
//                if (state == JFrame.ICONIFIED) {
//                    System.err.println("已经最小化");
//                    AlertFrame.this.setExtendedState(JFrame.NORMAL);
//                }
//            }
//        }, 1000);

    }

    private void initComponents() {
        setIconImage(ZResource.getAppImage());
        setAlwaysOnTop(true);
        toFront();
        setFocusable(true);
        setUndecorated(true);
    }

    public void beginRestTimer(int restTime) {
        this.restTime = restTime;
        java.util.Timer restTimer = new java.util.Timer();
        System.out.println("rest begin...." + new Date());
        restTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("rest over ..." + new Date());
                AlertFrame.this.setVisible(false);
            }
        }, restTime);
        this.setVisible(true);
    }

    private void moveMouseToCenter() {
        try {
            Robot robot = new Robot();
            int centerX = (int) (this.getX() + this.getWidth() / 2.0);
            int centerY = (int) (this.getY() + this.getHeight() / 2.0);
            robot.mouseMove(centerX, centerY);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
    }
}
