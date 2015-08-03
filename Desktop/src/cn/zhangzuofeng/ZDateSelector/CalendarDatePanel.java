package cn.zhangzuofeng.ZDateSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CalendarDatePanel extends JPanel implements SyncCalendar {

    private Calendar calendar;
    private DateCellPanel[] dateCellPanels;
    private WeekStartMode weekStartMode;
    private int lastCalenderCellNumber;
    private Calendar firstDayOfCellPanelsCalendar;
    private Calendar firstDayOfNextMonth;
    private Calendar firstDayOfThisMonthCalendar;
    private int dayNumberOfThisMonth;
    private Calendar endDayOfThisMonth;
    private DateCellPanel lastSelectedDateCellPanel = null;
    private JPanel cellContainer = new JPanel();
    private JPanel rowHeadContainer = new JPanel();

    public CalendarDatePanel(Calendar calendar) {
        this.calendar = calendar;
        if (calendar == null) {
            this.calendar = Calendar.getInstance();
        }
        this.setLayout(new BorderLayout());
        this.add(cellContainer, BorderLayout.SOUTH);
        doCalculate();
        doLoadHead();
        doLoadCellComponents();
        loadAction();
    }

    public CalendarDatePanel(Date date) {
        if (calendar == null) {
            this.calendar = Calendar.getInstance();
        }
        calendar.setTime(date);
        this.setLayout(new BorderLayout());
        this.add(cellContainer, BorderLayout.SOUTH);
        doCalculate();
        doLoadHead();
        doLoadCellComponents();
        loadAction();
    }

    private void doLoadHead() {
        rowHeadContainer.setLayout(new BoxLayout(rowHeadContainer, BoxLayout.X_AXIS));
        String[] weekNames = new String[]{"Sun", "Mon", "Tues", "Wed", "Thu", "Fri", "Sat"};
        DateHeadCellPanel[] dateHeadCellPanels = new DateHeadCellPanel[7];
        if (WeekStartMode.MONDAY == weekStartMode) {
            for (int i = 0; i < dateHeadCellPanels.length; i++) {
                dateHeadCellPanels[i] = new DateHeadCellPanel();
                if (i < dateHeadCellPanels.length) {
                    dateHeadCellPanels[i].setText(weekNames[i + 1]);
                } else {
                    dateHeadCellPanels[i].setText(weekNames[0]);
                }
                rowHeadContainer.add(dateHeadCellPanels[i]);
            }
        } else {
            for (int i = 0; i < dateHeadCellPanels.length; i++) {
                dateHeadCellPanels[i] = new DateHeadCellPanel();
                dateHeadCellPanels[i].setText(weekNames[i]);
                rowHeadContainer.add(dateHeadCellPanels[i]);
            }
        }
        this.add(rowHeadContainer);
    }

    public static void main(String[] args) {
        CalendarDatePanel calendarDatePanel = new CalendarDatePanel(Calendar.getInstance());
        calendarDatePanel.setVisible(true);
        JFrame jFrame = new JFrame();
        jFrame.add(calendarDatePanel);
        jFrame.pack();
        jFrame.setAlwaysOnTop(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public WeekStartMode getWeekStartMode() {
        return weekStartMode;
    }

    public void setWeekStartMode(WeekStartMode weekStartMode) {
        this.weekStartMode = weekStartMode;
    }

    private void loadAction() {
        if (dateCellPanels == null || dateCellPanels.length == 0) {
            return;
        }
        for (int i = 0; i < dateCellPanels.length; i++) {
            DateCellPanel dateCellPanel = dateCellPanels[i];
            dateCellPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    dateCellPanelMouseClickAction(e);
                }
            });
            dateCellPanel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int keyCode = e.getKeyCode();
                    switch (keyCode) {
                        case KeyEvent.VK_UP:
                            calendar.add(Calendar.DATE, -7);
                            goToCalendar(calendar);
                            if (dataChangeActionListener != null) {
                                dataChangeActionListener.actionPerformance();
                            }
                            break;
                        case KeyEvent.VK_DOWN:
                            calendar.add(Calendar.DATE, 7);
                            goToCalendar(calendar);
                            if (dataChangeActionListener != null) {
                                dataChangeActionListener.actionPerformance();
                            }
                            break;
                        case KeyEvent.VK_LEFT:
                            calendar.add(Calendar.DATE, -1);
                            goToCalendar(calendar);
                            if (dataChangeActionListener != null) {
                                dataChangeActionListener.actionPerformance();
                            }
                            break;
                        case KeyEvent.VK_RIGHT:
                            calendar.add(Calendar.DATE, 1);
                            goToCalendar(calendar);
                            if (dataChangeActionListener != null) {
                                dataChangeActionListener.actionPerformance();
                            }
                            break;
                    }
                }
            });
        }

    }

    private Map<String, DateCellPanel> cellPanelMap = null;

    private void doLoadCellComponents() {
        cellContainer.setLayout(new BoxLayout(cellContainer, BoxLayout.Y_AXIS));
        Calendar todayCalendar = Calendar.getInstance();
        int todayDayOfYear = todayCalendar.get(Calendar.DAY_OF_YEAR);
        JPanel rowPanel = null;
        cellPanelMap = new HashMap<String, DateCellPanel>();
        for (int i = 0; i < lastCalenderCellNumber; i++) {
            DateCellPanel dateCellPanel = new DateCellPanel();
            dateCellPanels[i] = dateCellPanel;

            Calendar calendar1 = (Calendar) firstDayOfCellPanelsCalendar.clone();
            calendar1.add(Calendar.DATE, i);
            int dayOfYear = calendar1.get(Calendar.DAY_OF_YEAR);
            cellPanelMap.put(dayOfYear + "", dateCellPanel);
            Date date = calendar1.getTime();
            dateCellPanel.setDate(date);
            if (calendar1.before(firstDayOfThisMonthCalendar)) {
                dateCellPanel.setMonthMode(MonthMode.LAST_MONTH);
            } else if (calendar1.after(endDayOfThisMonth)) {
                dateCellPanel.setMonthMode(MonthMode.NEXT_MONTH);
            } else {
                dateCellPanel.setMonthMode(MonthMode.THIS_MONTH);
            }
            // whether it is today
            if (todayDayOfYear == dayOfYear) {
                dateCellPanel.setCurrent(true);
                lastSelectedDateCellPanel = dateCellPanel;
            } else {
                dateCellPanel.setCurrent(false);
            }
            //whether it is selected
            if (dayOfYear == calendar.get(Calendar.DAY_OF_YEAR)) {
                dateCellPanel.setSelected(true);
                dateCellPanel.requestFocus();
                lastSelectedDateCellPanel = dateCellPanel;
            } else {
                dateCellPanel.setSelected(false);
            }
            // whether it is weekend
            int dayOfWeek1 = calendar1.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek1 == 6 || dayOfWeek1 == 7) {
                dateCellPanel.setWeekend(true);
            }
            // whether it is festival
            // add to row panel
            if (i % 7 == 0) {
                rowPanel = new DateRowPanel();
                cellContainer.add(rowPanel);
            }
            rowPanel.add(dateCellPanel);
        }
    }

    private void doCalculate() {
        Calendar tempCalendar;
        if (calendar != null) {
            tempCalendar = (Calendar) calendar.clone();
        } else {
            tempCalendar = Calendar.getInstance();
        }
        tempCalendar.set(Calendar.HOUR, 8);
        tempCalendar.set(Calendar.MINUTE, 0);
        tempCalendar.set(Calendar.SECOND, 0);
        tempCalendar.set(Calendar.MILLISECOND, 0);
        //calculate the 1st day of this month
        int dayOfMonth = tempCalendar.get(Calendar.DAY_OF_MONTH);
        tempCalendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
        firstDayOfThisMonthCalendar = (Calendar) tempCalendar.clone();
        // day of week of the dayOfMonth
        int dayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK);
        if (weekStartMode == WeekStartMode.MONDAY) {
            dayOfWeek--;
        }
        tempCalendar.add(Calendar.DAY_OF_YEAR, -(dayOfWeek));
        firstDayOfCellPanelsCalendar = (Calendar) tempCalendar.clone();
        //calculate need cell number
        tempCalendar = (Calendar) firstDayOfThisMonthCalendar.clone();
        tempCalendar.add(Calendar.MONTH, 1);
        firstDayOfNextMonth = (Calendar) tempCalendar.clone();
        tempCalendar.add(Calendar.DAY_OF_YEAR, -1);
        dayNumberOfThisMonth = tempCalendar.get(Calendar.DAY_OF_MONTH);
        endDayOfThisMonth = (Calendar) tempCalendar.clone();
        int dayCount = dayNumberOfThisMonth + dayOfWeek;
        int rowCount = dayCount / 7;
        if (dayCount % 7 != 0) {
            rowCount++;
        }
        if (rowCount * 7 != lastCalenderCellNumber) {
            dateCellPanels = new DateCellPanel[rowCount * 7];
        }
        lastCalenderCellNumber = rowCount * 7;
    }

    private void dateCellPanelMouseClickAction(MouseEvent e) {
        DateCellPanel selectedDateCellPanel = (DateCellPanel) e.getSource();
        Date date = selectedDateCellPanel.getDate();
        Calendar selectCalendar = Calendar.getInstance();
        selectCalendar.setTime(date);
        goToCalendar(selectCalendar);
        if (dataChangeActionListener != null) {
            dataChangeActionListener.actionPerformance();
        }
    }

    public void goToCalendar(Calendar calendar) {
        if (calendar.before(firstDayOfThisMonthCalendar) || calendar.after(endDayOfThisMonth)) {
            //非本月
            this.calendar = calendar;
            this.lastSelectedDateCellPanel = null;
            cellContainer.removeAll();
            doCalculate();
            doLoadCellComponents();
            loadAction();
            cellContainer.revalidate();
            lastSelectedDateCellPanel.requestFocus();
        } else {
            //本月
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            DateCellPanel dateCellPanel = cellPanelMap.get(dayOfYear + "");
            dateCellPanel.setSelected(true);
            dateCellPanel.requestFocus();
            if (lastSelectedDateCellPanel != null && !lastSelectedDateCellPanel.equals(dateCellPanel)) {
                lastSelectedDateCellPanel.setSelected(false);
            }
            lastSelectedDateCellPanel = dateCellPanel;
            this.calendar = calendar;
        }
    }

    @Override
    public Date getDate() {
        return calendar.getTime();
    }
    private DataChangeActionListener dataChangeActionListener;

    public void addDataChangeActionListener(DataChangeActionListener dataChangeActionListener) {
        this.dataChangeActionListener = dataChangeActionListener;
        loadAction();
    }
}
