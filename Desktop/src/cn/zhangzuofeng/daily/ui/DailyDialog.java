package cn.zhangzuofeng.daily.ui;

import cn.zhangzuofeng.ZDateSelector.CalendarDatePanel;
import cn.zhangzuofeng.ZDateSelector.DataChangeActionListener;
import cn.zhangzuofeng.daily.dao.MissionScheduleManager;
import cn.zhangzuofeng.daily.entity.MissionEntity;
import cn.zhangzuofeng.daily.util.ZResource;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DailyDialog extends JDialog {

    private CalendarDatePanel calendarDatePanel;
    private JPanel dailyContentPanel;
    private MissionListPanel todayMissionListPanel;
    private MissionListPanel todayMemoListPanel;
    private MissionListPanel midPlanListPanel;
    private MissionListPanel someDayMissionListPanel;
    private MissionScheduleManager missionScheduleManager = new MissionScheduleManager();
    private JButton cancelButton;
    private JButton sureButton;
    private Date timestamp;
    private Checkbox showCompeletedCheckbox;
    private Checkbox showUnderwayCheckbox;
    private Checkbox showDeletedCheckbox;

    public DailyDialog(Date date) {
        this.timestamp = date;
        if (timestamp == null) {
            timestamp = new Date();
        }
        setIconImage(ZResource.getAppImage());
        initComponents();
        loadData();
        loadAction();
    }

    private void initComponents() {
        dailyContentPanel = new JPanel();
        todayMissionListPanel = new MissionListPanel(MissionEntity.TODAY_MISSION_TYPE, timestamp);
        todayMemoListPanel = new MissionListPanel(MissionEntity.TODAY_MEMO_TYPE, timestamp);
        midPlanListPanel = new MissionListPanel(MissionEntity.MIDDLE_PLAN_TYPE);
        someDayMissionListPanel = new MissionListPanel(MissionEntity.SOME_DAY_MISSION_TYPE);

        dailyContentPanel.setLayout(new BoxLayout(dailyContentPanel, BoxLayout.Y_AXIS));

        dailyContentPanel.add(midPlanListPanel);
        dailyContentPanel.add(todayMissionListPanel);
        dailyContentPanel.add(todayMemoListPanel);
        dailyContentPanel.add(someDayMissionListPanel);

        JPanel eastPanel = new JPanel();
        eastPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        eastPanel.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(dailyContentPanel);

        JPanel dailyPanel = new JPanel();
        dailyPanel.setLayout(new BorderLayout());
        JPanel dailyToolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        showCompeletedCheckbox = new Checkbox();
        showCompeletedCheckbox.setLabel(ZResource.getLabel("completed"));
        showUnderwayCheckbox = new Checkbox();
        showUnderwayCheckbox.setLabel(ZResource.getLabel("underway"));
        showDeletedCheckbox = new Checkbox();
        showDeletedCheckbox.setLabel(ZResource.getLabel("deleted"));

        dailyToolPanel.add(showCompeletedCheckbox);
        dailyToolPanel.add(showUnderwayCheckbox);
        dailyToolPanel.add(showDeletedCheckbox);

        dailyPanel.add(dailyToolPanel, BorderLayout.NORTH);
        dailyPanel.add(scrollPane, BorderLayout.CENTER);
        eastPanel.add(dailyPanel, BorderLayout.CENTER);

        JPanel westPanel = new JPanel();

        calendarDatePanel = new CalendarDatePanel(timestamp);
        calendarDatePanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        westPanel.add(calendarDatePanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        sureButton = new JButton();
        cancelButton = new JButton();

        sureButton.setText(ZResource.getLabel("sure"));
        cancelButton.setText(ZResource.getLabel("cancel"));
        buttonPanel.add(sureButton);
        buttonPanel.add(cancelButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(westPanel, BorderLayout.WEST);
        contentPanel.add(eastPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.setContentPane(contentPanel);
    }

    private void loadData() {
        boolean showCompletedMission = showCompeletedCheckbox.getState();
        missionScheduleManager.reloadDataFromDatabase();
        List<MissionEntity> todayMission = missionScheduleManager.getTodayMission();
        todayMissionListPanel.setMissionList(todayMission, timestamp, showCompletedMission);

        todayMission = missionScheduleManager.getTodayMemo();
        todayMemoListPanel.setMissionList(todayMission, timestamp, showCompletedMission);

        todayMission = missionScheduleManager.getMidPlan();
        midPlanListPanel.setMissionList(todayMission, timestamp, showCompletedMission);

        todayMission = missionScheduleManager.getSomeDayMissions();
        someDayMissionListPanel.setMissionList(todayMission, timestamp, showCompletedMission);
        dailyContentPanel.validate();
    }

    private void loadAction() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DailyDialog.this.dispose();
            }
        });
        sureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean todayMissionListPanelChanged = todayMissionListPanel.isChanged(missionScheduleManager.getTodayMission());
                boolean todayMemoListPanelChanged = todayMemoListPanel.isChanged(missionScheduleManager.getTodayMemo());
                boolean someDayMissionListPanelChanged = someDayMissionListPanel.isChanged(missionScheduleManager.getSomeDayMissions());
                boolean midPlanListPanelChanged = midPlanListPanel.isChanged(missionScheduleManager.getMidPlan());
                if (todayMemoListPanelChanged || todayMissionListPanelChanged || someDayMissionListPanelChanged || midPlanListPanelChanged) {
                    List<MissionEntity> todayMissionList = todayMissionListPanel.getChangedMissionList();
                    List<MissionEntity> todayMemoMissionList = todayMemoListPanel.getChangedMissionList();
                    List<MissionEntity> someDayMissionList = someDayMissionListPanel.getChangedMissionList();
                    List<MissionEntity> midPlanMissionList = midPlanListPanel.getChangedMissionList();
                    missionScheduleManager.setTodayMission(todayMissionList, timestamp);
                    missionScheduleManager.setTodayMemo(todayMemoMissionList, timestamp);
                    missionScheduleManager.setSomeDayMission(someDayMissionList);
                    missionScheduleManager.setMidPlan(midPlanMissionList);
                    missionScheduleManager.save();
                }
                DailyDialog.this.dispose();
            }
        });
        calendarDatePanel.addDataChangeActionListener(new DataChangeActionListener() {

            @Override
            public void actionPerformance() {
                Date date = calendarDatePanel.getDate();
                if (date == null) {
                    return;
                }
                String oldDateString = MissionScheduleManager.simpleDateFormat.format(timestamp);
                String newDateString = MissionScheduleManager.simpleDateFormat.format(date);
                if (oldDateString.equals(newDateString)) {
                    return;
                }
                timestamp = date;
                List<MissionEntity> todayMissionOfOneDay = missionScheduleManager.getTodayMissionOfOneDay(date);
                List<MissionEntity> todayMemoOfOneDay = missionScheduleManager.getTodayMemoOfOneDay(date);
                todayMissionListPanel.setMissionList(todayMissionOfOneDay, date);
                todayMemoListPanel.setMissionList(todayMemoOfOneDay, date);
                dailyContentPanel.validate();
            }
        });
        showCompeletedCheckbox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                Object source = e.getSource();
                if (source != null) {
                    Checkbox checkbox = (Checkbox) source;
                    //TODO
                    loadData();
                }
            }
        });
    }
}
