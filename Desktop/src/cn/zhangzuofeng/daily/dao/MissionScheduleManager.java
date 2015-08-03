package cn.zhangzuofeng.daily.dao;

import cn.zhangzuofeng.daily.entity.MissionEntity;
import cn.zhangzuofeng.daily.entity.MissionSchedule;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissionScheduleManager {

    public MissionScheduleManager() {

    }

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String databaseFileName = System.getProperty("user.dir") + File.separator + "daily.zyz";
    private MissionSchedule missionSchedule;

    public void setDatabaseFileName(String databaseFileName) {
        this.databaseFileName = databaseFileName;
    }

    public List<MissionEntity> getTodayMissionOfOneDay(Date date) {
        if (date == null) {
            return null;
        }
        if (missionSchedule == null) {
            return null;
        }
        String todayString = simpleDateFormat.format(date);
        Map<String, List<MissionEntity>> todayMissionsMap = missionSchedule.getTodayMissionsMap();
        if (todayMissionsMap != null && todayMissionsMap.size() > 0) {
            List<MissionEntity> todayMissionList = todayMissionsMap.get(todayString);
            if (todayMissionList != null && todayMissionList.size() > 0) {
                return todayMissionList;
            }
        }
        return null;
    }

    public List<MissionEntity> getTodayMission() {
        return getTodayMissionOfOneDay(new Date());
    }

    public List<MissionEntity> getTodayMemoOfOneDay(Date date) {
        if (date == null) {
            return null;
        }
        if (missionSchedule == null) {
            return null;
        }

        String todayString = simpleDateFormat.format(date);
        Map<String, List<MissionEntity>> todayMissionsMap = missionSchedule.getTodayMemosMap();
        if (todayMissionsMap != null && todayMissionsMap.size() > 0) {
            List<MissionEntity> todayMissionList = todayMissionsMap.get(todayString);
            if (todayMissionList != null && todayMissionList.size() > 0) {
                return todayMissionList;
            }
        }
        return null;
    }

    public List<MissionEntity> getTodayMemo() {
        return getTodayMemoOfOneDay(new Date());
    }

    public List<MissionEntity> getMidPlan() {
        if (missionSchedule == null) {
            return null;
        }
        List<MissionEntity> todayMissionList = missionSchedule.getMidPlansList();
        if (todayMissionList != null && todayMissionList.size() > 0) {
            return todayMissionList;
        }
        return null;
    }

    public List<MissionEntity> getSomeDayMissions() {
        if (missionSchedule == null) {
            return null;
        }

        List<MissionEntity> todayMissionList = missionSchedule.getSomeDayMissionsList();
        if (todayMissionList != null && todayMissionList.size() > 0) {
            return todayMissionList;
        }
        return null;
    }

    public void save() {
        DaoUtil.compression(databaseFileName, missionSchedule);
    }

    public void reloadDataFromDatabase() {
        missionSchedule = DaoUtil.decompression(databaseFileName);
    }

    public void setTodayMission(List<MissionEntity> todayMissionList, Date timestamp) {
        if (missionSchedule == null) {
            missionSchedule = new MissionSchedule();
        }
        Map<String, List<MissionEntity>> todayMissionsMap = missionSchedule.getTodayMissionsMap();
        if (todayMissionsMap == null) {
            todayMissionsMap = new HashMap<String, List<MissionEntity>>();
        }
        String dateString = simpleDateFormat.format(timestamp);
        todayMissionsMap.put(dateString, todayMissionList);
        missionSchedule.setTodayMissionsMap(todayMissionsMap);
    }

    public void setTodayMemo(List<MissionEntity> todayMemoList, Date timestamp) {
        if (missionSchedule == null) {
            missionSchedule = new MissionSchedule();
        }
        Map<String, List<MissionEntity>> todayMemosMap = missionSchedule.getTodayMemosMap();
        if (todayMemosMap == null) {
            todayMemosMap = new HashMap<String, List<MissionEntity>>();
        }
        String dateString = simpleDateFormat.format(timestamp);
        todayMemosMap.put(dateString, todayMemoList);
        missionSchedule.setTodayMemosMap(todayMemosMap);
    }

    public void setSomeDayMission(List<MissionEntity> someDayMissionList) {
        if (missionSchedule == null) {
            missionSchedule = new MissionSchedule();
        }
        missionSchedule.setSomeDayMissionsList(someDayMissionList);
    }

    public void setMidPlan(List<MissionEntity> midPlanMissionList) {
        if (missionSchedule == null) {
            missionSchedule = new MissionSchedule();
        }
        missionSchedule.setMidPlansList(midPlanMissionList);
    }
}
