package cn.zhangzuofeng.daily.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class MissionSchedule implements Serializable {
    private static final long serialVersionUID = -4903107312403938616L;
    private Map<String, List<MissionEntity>> todayMissionsMap;
    private Map<String, List<MissionEntity>> todayMemosMap;
    private List<MissionEntity> someDayMissionsList;
    private List<MissionEntity> midPlansList;
    public static final String TODAY_MISSION_TAG = "todayMission";
    public static final String TODAY_MEMO_TAG = "todayMemo";
    public static final String SOME_DAY_MISSION_TAG = "someDayMission";
    public static final String MID_PLAN_TAG = "midPlan";

    public Map<String, List<MissionEntity>> getTodayMissionsMap() {
        return todayMissionsMap;
    }

    public void setTodayMissionsMap(Map<String, List<MissionEntity>> todayMissionsMap) {
        this.todayMissionsMap = todayMissionsMap;
    }

    public Map<String, List<MissionEntity>> getTodayMemosMap() {
        return todayMemosMap;
    }

    public void setTodayMemosMap(Map<String, List<MissionEntity>> todayMemosMap) {
        this.todayMemosMap = todayMemosMap;
    }

    public List<MissionEntity> getSomeDayMissionsList() {
        return someDayMissionsList;
    }

    public void setSomeDayMissionsList(List<MissionEntity> someDayMissionsList) {
        this.someDayMissionsList = someDayMissionsList;
    }

    public List<MissionEntity> getMidPlansList() {
        return midPlansList;
    }

    public void setMidPlansList(List<MissionEntity> midPlansList) {
        this.midPlansList = midPlansList;
    }

}
