package cn.zhangzuofeng.ZDateSelector;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class CalendarDateToolBar extends JPanel implements SyncCalendar {

    private Calendar calendar;
    private JTextField messageLabel;
    private JComboBox yearComboBox;
    private JComboBox monthComboBox;
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JLabel todayLabel;

    public CalendarDateToolBar() {
        calendar = Calendar.getInstance();
        initComponents();
        loadData();
    }

    private void loadData() {
        for (int i = 1970; i < 2050; i++) {
            yearComboBox.addItem("" + i);
        }
        for (int i = 1; i < 13; i++) {
            monthComboBox.addItem("" + i);
        }
        leftLabel.setText("L");
        rightLabel.setText("R");

        int year = calendar.get(Calendar.YEAR);
        yearComboBox.setSelectedIndex(year - 1969);

        int month = calendar.get(Calendar.MONTH);
        monthComboBox.setSelectedIndex(month);

        Date date = calendar.getTime();
        messageLabel.setText(date.toString());

        todayLabel.setText("T");
    }

    private void initComponents() {
        messageLabel = new JTextField();
        yearComboBox = new JComboBox();
        monthComboBox = new JComboBox();
        leftLabel = new JLabel();
        rightLabel = new JLabel();
        messageLabel.setBorder(null);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setColumns(4);
        this.add(leftLabel);
        add(yearComboBox);
        add(monthComboBox);
        add(messageLabel);
        add(todayLabel);
        add(rightLabel);
    }

    @Override
    public void goToCalendar(Calendar calendar) {
    }

    @Override
    public Date getDate() {
        return calendar.getTime();
    }
}
