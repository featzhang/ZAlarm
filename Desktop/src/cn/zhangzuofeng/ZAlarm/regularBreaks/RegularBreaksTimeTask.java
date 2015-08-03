package cn.zhangzuofeng.ZAlarm.regularBreaks;

import cn.zhangzuofeng.ZAlarm.ui.AlarmFrame.AlertFrame;
import cn.zhangzuofeng.ZAlarm.ui.AlarmFrame.AlertFrameControl;

import java.awt.*;
import java.awt.image.ColorModel;
import java.util.Date;
import java.util.TimerTask;

public class RegularBreaksTimeTask extends TimerTask {

//    private AlertFrame alertFrame;
    private int restTime;

    @Override
    public void run() {
        System.out.println("alert run...."+new Date());
        AlertFrameControl.showAlertFrameWithRestTime(restTime);
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getRestTime() {
        return restTime;
    }
}
