package cn.zhangzuofeng.ZDateSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

public class DateCellPanel extends JPanel {
    private JTextField dayLabel;
    private JTextField lunaLabel;
    private MonthMode monthMode;
    private Date date;
    private Font dayLabelFont = new Font("", Font.PLAIN, 20);
    private Font lunaLabelFont = getFont();
    private Color currentDayLabelColor = Color.white;
    private Color currentDaySelectedBackground = Color.RED;
    private Color notCurrentDayBackground = Color.WHITE;
    private Color currentDayUnselectedBackground = Color.lightGray;
    private Color weekendDayTextColor = Color.red;
    private Color festivalLunaLabelForeground = Color.blue;
    private Color selectedBorderColor = Color.RED;
    private Color unselectedBorderColor = Color.WHITE;

    private boolean isCurrent = false;
    private boolean isWeekend = false;
    private boolean isSelected = false;
    private boolean isFestival = false;
    private Color notCurrentMonthLabelForeground = Color.gray;
    private Color notCurrentDayLabelColor = Color.BLACK;
    private Color notFestivalNotCurrentLunaLabelForeground = Color.black;

    public Color getNotCurrentMonthLabelForeground() {
        return notCurrentMonthLabelForeground;
    }

    public void setNotCurrentMonthLabelForeground(Color notCurrentMonthLabelForeground) {
        this.notCurrentMonthLabelForeground = notCurrentMonthLabelForeground;
    }

    public Color getNotCurrentDayLabelColor() {
        return notCurrentDayLabelColor;
    }

    public void setNotCurrentDayLabelColor(Color notCurrentDayLabelColor) {
        this.notCurrentDayLabelColor = notCurrentDayLabelColor;
    }

    public Color getNotFestivalNotCurrentLunaLabelForeground() {
        return notFestivalNotCurrentLunaLabelForeground;
    }

    public void setNotFestivalNotCurrentLunaLabelForeground(Color notFestivalNotCurrentLunaLabelForeground) {
        this.notFestivalNotCurrentLunaLabelForeground = notFestivalNotCurrentLunaLabelForeground;
    }

    public DateCellPanel() {
        initComponents();
        resetColor();
    }

    public Color getNotCurrentDayBackground() {
        return notCurrentDayBackground;
    }

    public void setNotCurrentDayBackground(Color notCurrentDayBackground) {
        this.notCurrentDayBackground = notCurrentDayBackground;
        resetColor();
    }

    public Color getSelectedBorderColor() {
        return selectedBorderColor;
    }

    public void setSelectedBorderColor(Color selectedBorderColor) {
        this.selectedBorderColor = selectedBorderColor;
        resetColor();

    }

    public Color getUnselectedBorderColor() {
        return unselectedBorderColor;
    }

    public void setUnselectedBorderColor(Color unselectedBorderColor) {
        this.unselectedBorderColor = unselectedBorderColor;
        resetColor();

    }

    public Color getCurrentDayLabelColor() {
        return currentDayLabelColor;
    }

    public void setCurrentDayLabelColor(Color currentDayLabelColor) {
        this.currentDayLabelColor = currentDayLabelColor;
        resetColor();

    }

    public Color getCurrentDaySelectedBackground() {
        return currentDaySelectedBackground;
    }

    public void setCurrentDaySelectedBackground(Color currentDaySelectedBackground) {
        this.currentDaySelectedBackground = currentDaySelectedBackground;
        resetColor();

    }

    public Color getCurrentDayUnselectedBackground() {
        return currentDayUnselectedBackground;
    }

    public void setCurrentDayUnselectedBackground(Color currentDayUnselectedBackground) {
        this.currentDayUnselectedBackground = currentDayUnselectedBackground;
        resetColor();

    }

    public Color getWeekendDayTextColor() {
        return weekendDayTextColor;
    }

    public void setWeekendDayTextColor(Color weekendDayTextColor) {
        this.weekendDayTextColor = weekendDayTextColor;
        resetColor();

    }

    public Color getFestivalLunaLabelForeground() {
        return festivalLunaLabelForeground;
    }

    public void setFestivalLunaLabelForeground(Color festivalLunaLabelForeground) {
        this.festivalLunaLabelForeground = festivalLunaLabelForeground;
        resetColor();

    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
        resetColor();

    }

    public boolean isWeekend() {
        return isWeekend;
    }

    public void setWeekend(boolean isWeekend) {
        this.isWeekend = isWeekend;
        resetColor();

    }

    public boolean isSelected() {
        return isSelected;

    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        resetColor();

    }

    public boolean isFestival() {
        return isFestival;
    }

    public void setFestival(boolean isFestival) {
        this.isFestival = isFestival;
        resetColor();

    }

    public Font getLunaLabelFont() {
        return lunaLabelFont;
    }

    public void setLunaLabelFont(Font lunaLabelFont) {
        this.lunaLabelFont = lunaLabelFont;
        resetLabelSize();
    }

    public Font getDayLabelFont() {

        return dayLabelFont;
    }

    public void setDayLabelFont(Font dayLabelFont) {
        this.dayLabelFont = dayLabelFont;
        resetLabelSize();
    }

    public MonthMode getMonthMode() {
        return monthMode;
    }

    public void setMonthMode(MonthMode monthMode) {
        this.monthMode = monthMode;
    }

    private void initComponents() {
//        this.setOpaque(false);
        dayLabel = new CellContentTextField();
        lunaLabel = new CellContentTextField();
        setLayout(new BorderLayout());
        add(dayLabel, BorderLayout.NORTH);
        add(lunaLabel, BorderLayout.SOUTH);
        setBorder(BorderFactory.createLineBorder(Color.blue));
        resetLabelSize();
        setBackground(Color.cyan);
        setForeground(Color.magenta);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        resetText();
    }

    private void resetLabelSize() {
        dayLabel.setColumns(2);
        lunaLabel.setColumns(4);
        dayLabel.setFont(dayLabelFont);
        lunaLabel.setFont(lunaLabelFont);
    }

    private void resetText() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_MONTH);
        dayLabel.setText(dayOfWeek + "");
        lunaLabel.setText(dayOfWeek + "");
    }

    private void resetColor() {
        if (isSelected) {
            this.setBorder(BorderFactory.createLineBorder(selectedBorderColor));
        } else {
            this.setBorder(BorderFactory.createLineBorder(unselectedBorderColor));
        }
        if (isCurrent) {
            if (isSelected) {
                this.setBackground(currentDaySelectedBackground);
            } else {
                this.setBackground(currentDayUnselectedBackground);
                this.setBorder(BorderFactory.createLineBorder(currentDayUnselectedBackground));
            }
            this.dayLabel.setForeground(currentDayLabelColor);
            this.lunaLabel.setForeground(currentDayLabelColor);
        } else {
            this.setBackground(notCurrentDayBackground);
            this.dayLabel.setForeground(notCurrentDayLabelColor);
            this.lunaLabel.setForeground(notCurrentDayLabelColor);
        }

        if (isFestival) {
            lunaLabel.setForeground(festivalLunaLabelForeground);
        } else {
            if (isCurrent) {
                lunaLabel.setForeground(currentDayLabelColor);
            } else {
                lunaLabel.setForeground(notFestivalNotCurrentLunaLabelForeground);
            }
        }
        if (isWeekend) {
            if (!isCurrent) {
                dayLabel.setForeground(weekendDayTextColor);
            }
        }
        if (monthMode == MonthMode.LAST_MONTH || monthMode == MonthMode.NEXT_MONTH) {
            dayLabel.setForeground(notCurrentMonthLabelForeground);
            lunaLabel.setForeground(notCurrentMonthLabelForeground);
        }
    }

    class CellContentTextField extends JTextField {
        public CellContentTextField() {
            setBorder(null);
            setEditable(false);
            setEditable(false);
            setHorizontalAlignment(JTextField.CENTER);
            setOpaque(false);
            setFocusable(false);
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    mouseEventDispatch(e);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    mouseEventDispatch(e);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    mouseEventDispatch(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    mouseEventDispatch(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    mouseEventDispatch(e);
                }

                private void mouseEventDispatch(MouseEvent e) {
                    CellContentTextField source = (CellContentTextField) e.getSource();
                    DateCellPanel parent = (DateCellPanel) source.getParent();
                    MouseEvent e1 = SwingUtilities.convertMouseEvent(source, e, parent);
                    parent.dispatchEvent(e1);
                }
            });
        }
    }
}

