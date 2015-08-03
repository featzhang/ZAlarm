package cn.zhangzuofeng.ZDateSelector;

import java.util.Calendar;


public class TestCalender {
    public static void main(String[] args) {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.DAY_OF_YEAR);
        System.out.println(i);
    }
}
