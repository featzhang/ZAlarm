package cn.zhangzuofeng.ZAlarm.regularBreaks;


import cn.zhangzuofeng.ZAlarm.ui.AlarmSystemTray;
import cn.zhangzuofeng.config.ZConfigManager;
import cn.zhangzuofeng.ZAlarm.util.ZResource;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

public enum RegularBreaksTimer implements Serializable {
    instance;
    private Timer timer;
    private RegularBreaksConfigEntry dataEntry;
    private final String configFilePath = ZConfigManager.getConfigFilePath(ZResource.getAppName(), "regularBreaksTimer.zsc");
    private Date currentAlarmTaskBeginTime;
    private Date currentAlarmTaskEndTime;
    private String nextAlarmTimeString;

    public String getNextAlarmTimeString() {
        return nextAlarmTimeString;
    }

    public Date getCurrentAlarmTaskEndTime() {
        return currentAlarmTaskEndTime;
    }

    public void begin() {
        if (dataEntry == null) {
            System.err.println("dataEntry == null");
            throw new RuntimeException("dataEntry == null");
        }
        System.out.println("=============\nAlarm begin ..." + new Date());
        if (timer != null) {
            timer.cancel();
        }

        timer = new Timer();

        int restTime = dataEntry.parseRelaxTime();
        long stepTime = dataEntry.parseStepTime();
        RegularBreaksTimeTask timerTask = new RegularBreaksTimeTask();
        timerTask.setRestTime(restTime);
        timer.schedule(timerTask, stepTime, stepTime);

        Calendar calendar = GregorianCalendar.getInstance();
        currentAlarmTaskBeginTime = calendar.getTime();
        calendar.add(Calendar.MILLISECOND, (int) stepTime);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date time = calendar.getTime();
        currentAlarmTaskEndTime = time;
        String dateFormatString = "h:m";
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString);
        nextAlarmTimeString = dateFormat.format(time);
        String nextAlarmTimeString2 = ZResource.getLabel("nextAlarmTime") + ":" + nextAlarmTimeString;
        String tip = ZResource.getAppName() + ":" + nextAlarmTimeString2;
        AlarmSystemTray.instance.getTrayIcon().setToolTip(tip);

    }


    public void setDataEntry(RegularBreaksConfigEntry dataEntry) {
        if (this.dataEntry != null) {
            if (timer != null && !this.dataEntry.equals(dataEntry)) {
                timer.cancel();
                timer.purge();
                this.dataEntry = dataEntry;
                begin();
            } else {
                this.dataEntry = dataEntry;
            }
        } else {
            this.dataEntry = dataEntry;
        }
    }

    public RegularBreaksConfigEntry getDataEntry() {
        return dataEntry;
    }

    public void readDataEntryFromConfigFile() {
        RegularBreaksConfigEntry regularBreaksConfigEntry;
        regularBreaksConfigEntry = (RegularBreaksConfigEntry) ZConfigManager.loadProperties(RegularBreaksConfigEntry.class, configFilePath);
        if (regularBreaksConfigEntry == null) {
            regularBreaksConfigEntry = new RegularBreaksConfigEntry();
        }
        this.dataEntry = regularBreaksConfigEntry;
    }

    public void saveDataEntryToConfigFile() {
        ZConfigManager.saveProperties(dataEntry, configFilePath, ZResource.getAppName());
    }
}
