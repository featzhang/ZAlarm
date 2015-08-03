package cn.zhangzuofeng.test;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class TestWindowListener extends JFrame {

    public TestWindowListener() {
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowDeactivated(WindowEvent e) {
                System.out.println("windowDeactivated");
                //当为激活状态出现，有两种可能: 1 关闭 2 最小化
                Window window = e.getWindow();
                Class<? extends Window> aClass = window.getClass();
                System.out.println("class name:" + aClass.getName());
                new Thread() {
                    @Override
                    public void run() {
                        TestWindowListener testWindowListener = new TestWindowListener();
                        testWindowListener.setSize(400, 400);
                        testWindowListener.setAlwaysOnTop(true);
                        testWindowListener.setTitle("" + (windowCount++));
                        testWindowListener.setVisible(true);
                    }
                }.start();
                TestWindowListener.this.setVisible(false);
            }
        });
    }
    private static int windowCount = 1;

    public static void main(String[] args) {
        TestWindowListener testWindowListener = new TestWindowListener();
        testWindowListener.setSize(400, 400);
//        testWindowListener.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testWindowListener.setVisible(true);
    }
}
