package cn.zhangzuofeng.ZAlarm.ui;

import cn.zhangzuofeng.ZAlarm.AlarmMain;
import cn.zhangzuofeng.ZAlarm.regularBreaks.RegularBreaksTimer;
import cn.zhangzuofeng.ZAlarm.regularBreaks.RegularBreaksConfigEntry;
import cn.zhangzuofeng.ZAlarm.util.ZResource;

import javax.swing.*;
import java.awt.event.*;

public class DetailSettingDialog1 extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField stepHourTextField;
    private JTextField stepMinuteTextField;
    private JTextField relaxMinuteTextField;
    private JTextField nextAlarmTimeTextField;

    public DetailSettingDialog1() {
        setIconImage(ZResource.getAppImage());
        setTitle(ZResource.getLabel("detailSetting"));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        loadData();
    }

    private void loadData() {
        nextAlarmTimeTextField.setText(RegularBreaksTimer.instance.getNextAlarmTimeString());
    }

    private void onOK() {
        if (isModified(AlarmMain.regularBreaksTimer.getDataEntry())) {
            RegularBreaksConfigEntry regularBreaksConfigEntry = new RegularBreaksConfigEntry();
            getData(regularBreaksConfigEntry);
            AlarmMain.regularBreaksTimer.setDataEntry(regularBreaksConfigEntry);
            AlarmMain.regularBreaksTimer.saveDataEntryToConfigFile();
        }
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        DetailSettingDialog1 dialog = new DetailSettingDialog1();
        dialog.pack();
        dialog.setVisible(true);
//        System.exit(0);
    }

    private void createUIComponents() {
    }

    public void setData(RegularBreaksConfigEntry data) {
        stepHourTextField.setText(data.getStepHour());
        stepMinuteTextField.setText(data.getStepMinute());
        relaxMinuteTextField.setText(data.getRelaxMinute());
    }

    public void getData(RegularBreaksConfigEntry data) {
        data.setStepHour(stepHourTextField.getText());
        data.setStepMinute(stepMinuteTextField.getText());
        data.setRelaxMinute(relaxMinuteTextField.getText());
    }

    public boolean isModified(RegularBreaksConfigEntry data) {
        if (stepHourTextField.getText() != null ? !stepHourTextField.getText().equals(data.getStepHour()) : data.getStepHour() != null)
            return true;
        if (stepMinuteTextField.getText() != null ? !stepMinuteTextField.getText().equals(data.getStepMinute()) : data.getStepMinute() != null)
            return true;
        if (relaxMinuteTextField.getText() != null ? !relaxMinuteTextField.getText().equals(data.getRelaxMinute()) : data.getRelaxMinute() != null)
            return true;
        return false;
    }
}
