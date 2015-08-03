package cn.zhangzuofeng.ZAlarm.regularBreaks;


public class RegularBreaksConfigEntry {
    private String stepHour = "1";
    private String stepMinute = "";
    private String relaxMinute = "10";

    public String getStepHour() {
        return stepHour;
    }

    public void setStepHour(String stepHour) {
        this.stepHour = stepHour;
    }

    public String getStepMinute() {
        return stepMinute;
    }

    public void setStepMinute(String stepMinute) {
        this.stepMinute = stepMinute;
    }

    public String getRelaxMinute() {
        return relaxMinute;
    }

    public void setRelaxMinute(String relaxMinute) {
        this.relaxMinute = relaxMinute;
    }

    public int parseRelaxTime() {
        int restTime;
        if (stepMinute == null || stepMinute.trim().length() == 0) {
            restTime = 10 * 60 * 1000;
        } else {
            try {
                restTime = Integer.parseInt(relaxMinute) * 60 * 1000;
            } catch (Exception e) {
                restTime = 10 * 60 * 1000;
            }
        }
        return restTime;
    }

    public long parseStepTime() {
        int sHour = 0;
        if (stepHour == null || stepHour.trim().length() == 0) {
            sHour = 0;
        } else {
            try {
                sHour = Integer.parseInt(stepHour);
            } catch (Exception e) {
                sHour = 0;
            }
        }
        int sMinute = 0;
        if (stepMinute == null || stepHour.trim().length() == 0) {
            sMinute = 0;
        } else {
            try {
                sMinute = Integer.parseInt(stepMinute);
            } catch (NumberFormatException e) {
                sMinute = 0;
            }
        }
        long stepTime = 0;
        stepTime = (sHour * 60 + sMinute) * 60 * 1000;
        if (stepTime == 0) {
            stepTime = 60 * 60 * 1000;
        }
        return stepTime;
    }
}
