package cn.zhangzuofeng.daily.ui;

import cn.zhangzuofeng.daily.entity.MissionEntity;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JPopupMenu;

public class MissionPanel extends javax.swing.JPanel {

    private javax.swing.JCheckBox selectedCheckBox;
    private javax.swing.JTextField titleTextField;
    private MissionEntity missionEntity;
    /**
     * 任务的状态 未开始 进行中 已完成 已删除
     */
    private JTextField statusLabel;
//    private JLabel ;
    private Color missionStatusColor = Color.RED;
    private JPopupMenu missionStatusPopupMenu;
    private Color missionStatusBkColor = Color.LIGHT_GRAY;

    public MissionPanel(MissionEntity missionEntity) {
        this.missionEntity = missionEntity;
        initComponents();
        createPopupMenu();
        loadData();
        loadAction();
    }

    public MissionEntity getChangedMissionEntity() {
        return missionEntity;
    }

    private void initComponents() {
        setMaximumSize(new Dimension(20000, 50));
        setMinimumSize(new Dimension(2000, 40));
//        setPreferredSize(new Dimension(300, 40));

        selectedCheckBox = new JCheckBox();

        titleTextField = new JTextField();
        titleTextField.setColumns(10);
        titleTextField.setEditable(false);
        titleTextField.setBorder(null);
        titleTextField.setOpaque(false);

        statusLabel = new JTextField();
        statusLabel.setHorizontalAlignment(JTextField.CENTER);
        statusLabel.setColumns(5);
        statusLabel.setEditable(false);
        statusLabel.setOpaque(false);
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        statusLabel.setForeground(missionStatusColor);
        statusLabel.setBackground(missionStatusBkColor);

        this.setLayout(new BorderLayout(0, 0));
        this.add(selectedCheckBox, BorderLayout.WEST);
        this.add(titleTextField, BorderLayout.CENTER);
        this.add(statusLabel, BorderLayout.EAST);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MissionEntity missionEntity1 = new MissionEntity();
        missionEntity1.setCreateTimestamp(new Date());
        missionEntity1.setDescription("Description Description Description Description Description Description");
        missionEntity1.setMissionType(MissionEntity.MIDDLE_PLAN_TYPE);
        missionEntity1.setStatus(MissionEntity.MissionStatus.UNDERWAY);
        missionEntity1.setTitle("Title Title Title Title Title Title Title Title Title Title Title ");
        MissionPanel missionPanel = new MissionPanel(missionEntity1);
        frame.add(missionPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean isChanged(MissionEntity missionEntity) {
        return this.missionEntity.equals(missionEntity);
    }

    private void reloadByEntity() {
        titleTextField.setText(missionEntity.getTitle());
        statusLabel.setText(missionEntity.getStatus().getChineseName());
    }

    public boolean isSelected() {
        return selectedCheckBox.isSelected();
    }

    private void loadAction() {
        statusLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                missionStatusPopupMenu.show(statusLabel, 0, statusLabel.getY());
            }
        });
    }

    public MissionEntity getMission() {
        return missionEntity;
    }

    private void loadData() {
        if (missionEntity != null) {
            titleTextField.setText(missionEntity.getTitle());
            titleTextField.setToolTipText(missionEntity.getDescription());
            MissionEntity.MissionStatus status = missionEntity.getStatus();
            if (status == null) {
                status = MissionEntity.MissionStatus.NOT_STARTED;
            }
            String statusChineseName = status.getChineseName();
            statusLabel.setText(statusChineseName);
            JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) missionStatusPopupMenu.getComponent(status.ordinal());
            checkBoxMenuItem.setSelected(true);
        }
    }

    private void createPopupMenu() {
        missionStatusPopupMenu = new JPopupMenu();
        MissionEntity.MissionStatus[] missionStatuses = MissionEntity.MissionStatus.values();
        ButtonGroup buttonGroup = new ButtonGroup();
        for (final MissionEntity.MissionStatus missionStatuse : missionStatuses) {
            String chineseName = missionStatuse.getChineseName();
            JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem(chineseName);
            missionStatusPopupMenu.add(checkBoxMenuItem);
            buttonGroup.add(checkBoxMenuItem);
            checkBoxMenuItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    statusLabel.setText(missionStatuse.getChineseName());
                    missionEntity.setStatus(missionStatuse);
                }
            });
        }
    }
}
