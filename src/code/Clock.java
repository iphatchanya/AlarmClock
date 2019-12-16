package code;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Clock {

    private StringBuilder timeFormat = new StringBuilder();
    private String hour;
    private String minute;
    private String alarmDate;
    private String format;

    private ArrayList<Clock> alarm = new ArrayList<>();

    public ArrayList<Clock> getAlarm() {
        return alarm;
    }

    public Clock(String hour, String minute, String alarmDate) {
        this.hour = hour;
        this.minute = minute;
        this.alarmDate = alarmDate;
        this.format = timeFormat.append(hour + ":" + minute).toString();
    }

    public Clock() {}

//    public Clock() throws Exception {
//        readFile();
//    }


//    public File getFile() {
//        try {
//            String jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
//            return new File(jarDir + "/Alarm.txt");
////            return new File("Alarm.txt");
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    private void readFile() throws Exception {
//        this.alarm.clear();
//        BufferedReader bufferedReader = new BufferedReader(new FileReader(getFile()));
//        String line = bufferedReader.readLine();
//        while (line != null && !line.isEmpty()) {
//            String[] str = line.split(".");
//            alarm.add(new Clock(str[0], str[1], str[2]));
//            line = bufferedReader.readLine();
//        }
//    }
//
//    private void saveFile() throws Exception {
//        StringBuilder allstr = new StringBuilder();
//        for (Clock c : alarm) {
//            allstr.append(c.getHour() + "." + c.getMinute() + "." + c.getAlarmDate());
//        }
//        try (PrintWriter printWriter = new PrintWriter(getFile())) {
//            printWriter.print(allstr.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public String getFormat() {
        return format;
    }

    public void addTime(Clock time) throws Exception {
        alarm.add(time);
//        saveFile();
    }

    public void deleteTime(Clock time) throws Exception {
        ArrayList<Clock> clocks = new ArrayList<>(alarm);
        alarm.clear();
        for (Clock c : clocks) {
            if(!c.getFormat().equals(time.getFormat()) || !c.getAlarmDate().equals(time.getAlarmDate())) {
                alarm.add(c);
            }
        }
//        saveFile();
    }

    public boolean alarmTime(String currentHour, String currentMinute, String currentSecond, String day) {
        for (Clock a : alarm) {
            if(a.getAlarmDate().equals("Everyday")) {
                if(a.getHour().equals(currentHour) && a.getMinute().equals(currentMinute) && currentSecond.equals("0")) {
                    return true;
                }
            }
            else if(!a.getAlarmDate().equals("Everyday")) {
                if(a.getHour().equals(currentHour) && a.getMinute().equals(currentMinute) && currentSecond.equals("0") && a.getAlarmDate().startsWith(day)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        String day = null;
        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            day = "Sunday";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            day = "Monday";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            day = "Tuesday";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            day = "Wednesday";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            day = "Thursday";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            day = "Friday";
        }
        else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            day = "Saturday";
        }
        return day;
    }


    public String data(String key, Date date) {
        return new SimpleDateFormat(key).format(date);
    }

    public String getCurrentDateFormat() {
        return getCurrentDay() + ", " + data("dd.MM.yyyy", new Date());
    }

    public String getCurrentTimeFormat() {
        return data("HH:mm:ss", new Date());
    }

    public String getCurrentHour() {
        return data("HH", new Date());
    }

    public String getCurrentMinute() {
        return data("mm", new Date());
    }

    public String getCurrentSecond() {
        return data("ss", new Date());
    }
}
