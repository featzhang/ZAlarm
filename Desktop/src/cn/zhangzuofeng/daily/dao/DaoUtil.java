package cn.zhangzuofeng.daily.dao;

import cn.zhangzuofeng.daily.entity.MissionEntity;
import cn.zhangzuofeng.daily.entity.MissionSchedule;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DaoUtil {

    public static void compression(String fileName, MissionSchedule missionSchedule) {
        try {
            File file = new File(fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            missionToZipStream(missionSchedule.getTodayMissionsMap(), MissionSchedule.TODAY_MISSION_TAG, zipOutputStream);
            missionToZipStream(missionSchedule.getMidPlansList(), MissionSchedule.MID_PLAN_TAG, zipOutputStream);
            missionToZipStream(missionSchedule.getSomeDayMissionsList(), MissionSchedule.SOME_DAY_MISSION_TAG, zipOutputStream);
            missionToZipStream(missionSchedule.getTodayMemosMap(), MissionSchedule.TODAY_MEMO_TAG, zipOutputStream);
            zipOutputStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static MissionSchedule decompression(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        Map<String, List<MissionEntity>> todayMissions = new HashMap<String, List<MissionEntity>>();
        Map<String, List<MissionEntity>> todayMemos = new HashMap<String, List<MissionEntity>>();
        List<MissionEntity> midPlans = new ArrayList<MissionEntity>();
        List<MissionEntity> someDayMissionEntities = new ArrayList<MissionEntity>();
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipEntry;
            ObjectInputStream objectInputStream;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String name = zipEntry.getName();
                if (name.endsWith("/")) {
                    continue;
                } else if (name.contains(MissionSchedule.TODAY_MISSION_TAG)) {
                    int i = name.indexOf("/");
                    String subName = name.substring(i + 1);
                    objectInputStream = new ObjectInputStream(zipInputStream);
                    List<MissionEntity> todayMissionList = (List<MissionEntity>) objectInputStream.readObject();
                    todayMissions.put(subName, todayMissionList);
                } else if (name.contains(MissionSchedule.TODAY_MEMO_TAG)) {
                    int i = name.indexOf("/");
                    String subName = name.substring(i + 1);
                    objectInputStream = new ObjectInputStream(zipInputStream);
                    List<MissionEntity> todayMissionList = (List<MissionEntity>) objectInputStream.readObject();
                    todayMemos.put(subName, todayMissionList);
                } else if (name.contains(MissionSchedule.MID_PLAN_TAG)) {
                    objectInputStream = new ObjectInputStream(zipInputStream);
                    midPlans = (List<MissionEntity>) objectInputStream.readObject();
                } else if (name.contains(MissionSchedule.SOME_DAY_MISSION_TAG)) {
                    objectInputStream = new ObjectInputStream(zipInputStream);
                    someDayMissionEntities = (List<MissionEntity>) objectInputStream.readObject();
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        MissionSchedule missionSchedule = new MissionSchedule();
        missionSchedule.setTodayMissionsMap(todayMissions.size() == 0 ? null : todayMissions);
        missionSchedule.setTodayMemosMap(todayMemos.size() == 0 ? null : todayMemos);
        missionSchedule.setMidPlansList((midPlans == null || midPlans.size() == 0) ? null : midPlans);
        missionSchedule.setSomeDayMissionsList((someDayMissionEntities == null || someDayMissionEntities.size() == 0) ? null : someDayMissionEntities);

        return missionSchedule;
    }

    public static void main(String[] args) {
        MissionSchedule missionSchedule = new MissionSchedule();
        Map<String, List<MissionEntity>> map = new HashMap();
        MissionEntity missionEntity = new MissionEntity();
        missionEntity.setTitle("sjflsklfjsdlfjl;f;sfsl;fj;sfjksl;jfioejfiosj");
        List<MissionEntity> missionEntitys = new ArrayList();
        missionEntitys.add(missionEntity);
        map.put("2015-", missionEntitys);
        missionSchedule.setTodayMissionsMap(map);
        DaoUtil.compression("D:\\abc.zip", missionSchedule);
        DaoUtil.decompression("D:\\abc.zip");
    }

    private static void missionToZipStream(Map<String, List<MissionEntity>> missionsMap, String base, ZipOutputStream zipOutputStream) {
        if (missionsMap == null) {
            return;
        }
        Set<String> keySet = missionsMap.keySet();
        ObjectOutputStream objectOutputStream;
        for (String key : keySet) {
            try {
                List<MissionEntity> missionEntitys = missionsMap.get(key);
                zipOutputStream.putNextEntry(new ZipEntry(base + "/" + key));
                objectOutputStream = new ObjectOutputStream(zipOutputStream);
                objectOutputStream.writeUnshared(missionEntitys);
                zipOutputStream.closeEntry();
            } catch (IOException ex) {
            }
        }
    }

    private static void missionToZipStream(List<MissionEntity> missionEntities, String base, ZipOutputStream zipOutputStream) {
        try {
            ObjectOutputStream objectOutputStream;
            zipOutputStream.putNextEntry(new ZipEntry(base));
            objectOutputStream = new ObjectOutputStream(zipOutputStream);
            objectOutputStream.writeUnshared(missionEntities);
            zipOutputStream.closeEntry();
        } catch (IOException ex) {
        }
    }
}
