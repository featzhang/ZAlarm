package cn.zhangzuofeng.daily.ui;

import cn.zhangzuofeng.CollapsiblePanel.CollapsiblePanel;
import cn.zhangzuofeng.daily.entity.MissionEntity;
import cn.zhangzuofeng.daily.util.ZResource;
import java.awt.Color;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MissionListPanel extends CollapsiblePanel {

    private Date timestamp;
    private boolean showCompletedMission = false;

    public MissionListPanel(String MissionType) {
        this(MissionType, new Timestamp(System.currentTimeMillis()));
    }

    private List<MissionEntity> missionList;
    private String MissionType;

    public MissionListPanel(String missionType, Date timestamp) {
        this.MissionType = missionType;
        this.timestamp = timestamp;
        setTitle(ZResource.getLabel(missionType));
        setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public void setMissionList(List<MissionEntity> missionList, Date timestamp, boolean showCompletedMission) {
        this.showCompletedMission = showCompletedMission;
        this.missionList = missionList;
        this.timestamp = timestamp;
        reloadPanelByMissionList();
        loadAction();
    }

    public void setMissionList(List<MissionEntity> missionList, Date timestamp) {
        setMissionList(missionList, timestamp, false);
    }

    private void reloadPanelByMissionList() {
        if (missionList == null) {
            missionList = new ArrayList<MissionEntity>();
        }
        getContentPanel().removeAll();
        for (MissionEntity missionEntity : missionList) {
            if (!showCompletedMission) {
                if (missionEntity.getStatus() == null) {

                } else if (missionEntity.getStatus().equals(MissionEntity.MissionStatus.COMPLETED)) {
                    continue;
                }
            }
            MissionPanel missionPanel = new MissionPanel(missionEntity);
//            missionPanel.setBorder(BorderFactory.createLineBorder(Color.yellow));
            addContentPanel(missionPanel);
        }
        validate();
    }

    public boolean isChanged(List<MissionEntity> missionList) {
        JPanel contentPanel = getContentPanel();
        int componentCount = contentPanel.getComponentCount();
        if (missionList == null || missionList.isEmpty()) {
            return componentCount != 0;
        }
        if (missionList.size() != componentCount) {
            return false;
        }
        Component[] components = contentPanel.getComponents();
        for (int i = 0; i < componentCount; i++) {
            MissionPanel missionPanel = (MissionPanel) components[i];
            MissionEntity missionEntity = missionList.get(i);
            boolean b = missionPanel.isChanged(missionEntity);
            if (b) {
                return true;
            }
        }
        return true;
    }

    private void loadAction() {

        addAddActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = MissionListPanel.this.getParent();
                while (!((parent) instanceof JDialog)) {
                    parent = parent.getParent();
                }
                JDialog missionDialog = (JDialog) parent;
                MissionEntity missionEntity = new MissionEntity();
                missionEntity.setCreateTimestamp(timestamp);
                missionEntity.setMissionType(MissionType);
                MissionEditorDialog missionEditorDialog;
                missionEditorDialog = new MissionEditorDialog(missionDialog, true);
                missionEditorDialog.setMissionEntity(missionEntity);
                missionEditorDialog.setLocationRelativeTo(missionDialog);
                missionEditorDialog.setVisible(true);
                boolean b;
                b = missionEditorDialog.getResultType();
                if (b) {
                    MissionEntity missionEntity1 = missionEditorDialog.getMissionEntity();
                    if (missionList == null) {
                        missionList = new ArrayList<MissionEntity>();
                    }
                    missionList.add(missionEntity1);
                    reloadPanelByMissionList();
                }
            }
        });
        addDeleteActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel contentPanel = getContentPanel();
                Component[] components = contentPanel.getComponents();
                boolean hasSelected = false;
                List<MissionPanel> selectedMissionPanels = new ArrayList<MissionPanel>();
                for (Component component : components) {
                    MissionPanel missionPanel = (MissionPanel) component;
                    if (missionPanel.isSelected()) {
                        hasSelected = true;
                        selectedMissionPanels.add(missionPanel);
                    }
                }
                if (!hasSelected) {
                    JOptionPane.showMessageDialog(MissionListPanel.this, "have not select any mission! ");
                    return;
                }
                String message = "<html>";
                message += "Do you make sure to delete the following mission(s)?<br>";
                for (int i = 0; i < selectedMissionPanels.size(); i++) {
                    MissionPanel missionPanel = selectedMissionPanels.get(i);
                    MissionEntity missionEntity = missionPanel.getChangedMissionEntity();
                    String title = missionEntity.getTitle();
                    message += "" + (i + 1) + "\t" + title + "<br>";
                }
                message += "</html>";
                int showConfirmDialog = JOptionPane.showConfirmDialog(MissionListPanel.this, message);
                if (showConfirmDialog == JOptionPane.YES_OPTION) {
                    for (MissionPanel missionPanel : selectedMissionPanels) {
                        contentPanel.remove(missionPanel);
                    }
                    MissionListPanel.this.validate();
                } else {
                }
            }
        });
    }

    public List<MissionEntity> getChangedMissionList() {
        List<MissionEntity> missionEntities = new ArrayList<MissionEntity>();
        JPanel contentPanel = getContentPanel();
        int componentCount = contentPanel.getComponentCount();
        for (int i = 0; i < componentCount; i++) {
            MissionPanel missionPanel = (MissionPanel) contentPanel.getComponent(i);
            MissionEntity missionEntity = missionPanel.getChangedMissionEntity();
            missionEntities.add(missionEntity);
        }
        return missionEntities;
    }

}
