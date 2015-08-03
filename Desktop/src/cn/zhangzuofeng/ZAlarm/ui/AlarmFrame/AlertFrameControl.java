package cn.zhangzuofeng.ZAlarm.ui.AlarmFrame;

import cn.zhangzuofeng.ZAlarm.ui.AlarmFrame.AlertFrame;
import java.awt.*;

public class AlertFrameControl {
    private static boolean fullOnScreen = true;

    public static void showAlertFrameWithRestTime(int restTime) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        for (GraphicsDevice screenDevice : screenDevices) {
            Rectangle rectangle = screenDevice.getDefaultConfiguration().getBounds();
            int screenSizeWidth = rectangle.width;
            int screenSizeHeight = rectangle.height;
            double x = screenSizeWidth / 4.0 + rectangle.x;
            double y = screenSizeHeight / 4.0 + rectangle.y;
            AlertFrame alertFrame = new AlertFrame(screenDevice.getDefaultConfiguration());
            alertFrame.setSize(screenSizeWidth / 2, screenSizeHeight / 2);
            alertFrame.setLocation((int) x, (int) y);
            alertFrame.beginRestTimer(restTime);
            /*if (fullOnScreen) {
                if (!screenDevice.isFullScreenSupported()) {
                    System.err.println("full screen not supported!");
                } else {
                    screenDevice.setFullScreenWindow(alertFrame);
                }
            }*/
        }
    }

}
