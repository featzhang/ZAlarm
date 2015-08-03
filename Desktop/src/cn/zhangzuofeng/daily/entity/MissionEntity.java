package cn.zhangzuofeng.daily.entity;

import cn.zhangzuofeng.daily.util.ZResource;
import java.io.Serializable;
import java.util.Date;

public class MissionEntity implements Serializable {

    public static enum MissionStatus {

        /**
         * 已经完成
         */
        COMPLETED("completed"),
        /**
         * 已删除
         */
        DELETED("deleted"),
        /**
         * 进行中
         */
        UNDERWAY("underway"),
        /**
         * 未开始
         */
        NOT_STARTED("notStarted");
        private String chineseName;

        private MissionStatus(String chineseName) {
            this.chineseName = ZResource.getLabel(chineseName);
        }

        public String getChineseName() {
            return chineseName;
        }
    }
    public final static long serialVersionUID = -5020515046411352830l;
    /**
     * 今日任务
     */
    public static String TODAY_MISSION_TYPE = "todayMission";
    /**
     * 今日备忘
     */
    public static String TODAY_MEMO_TYPE = "todayMemo";
    /**
     * 未来
     */
    public static String SOME_DAY_MISSION_TYPE = "someDayMission";
    /**
     * 中期计划
     */
    public static String MIDDLE_PLAN_TYPE = "midPlan";

    /**
     * id
     *
     */
    private int id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 任务类型
     */
    private String missionType;
    /**
     * 提醒时间
     */
    private Date alertTime;
    /**
     * 任务创建时间
     */
    private Date createTimestamp;
    /**
     * 任务状态
     */
    private MissionStatus status = MissionStatus.NOT_STARTED;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(Date alertTime) {
        this.alertTime = alertTime;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public String getMissionType() {
        return missionType;
    }

    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 67 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 67 * hash + (this.missionType != null ? this.missionType.hashCode() : 0);
        hash = 67 * hash + (this.alertTime != null ? this.alertTime.hashCode() : 0);
        hash = 67 * hash + (this.createTimestamp != null ? this.createTimestamp.hashCode() : 0);
        hash = 67 * hash + (this.status != null ? this.status.hashCode() : 0);
        return hash;
    }

}
