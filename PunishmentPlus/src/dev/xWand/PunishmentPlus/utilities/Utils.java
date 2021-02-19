package dev.xWand.PunishmentPlus.utilities;

import java.util.Calendar;

public class Utils {

    public String millisToDate(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH) + 1;
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        int mHour = calendar.get(Calendar.HOUR_OF_DAY);
        int mMins = calendar.get(Calendar.MINUTE);
        int mSecs = calendar.get(Calendar.SECOND);

        String day = String.valueOf(mDay);
        String month = String.valueOf(mMonth);
        String hour = String.valueOf(mHour);
        String min = String.valueOf(mMins);
        String secs = String.valueOf(mSecs);

        if (mDay < 10) {
            day = "0" + mDay;
        }
        if (mMonth < 10) {
            month = "0" + mMonth;
        }
        if (mHour < 10) {
            hour = "0" + mHour;
        }
        if (mMins < 10) {
            min = "0" + mMins;
        }
        if (mSecs < 10) {
            secs = "0" + mSecs;
        }



        String date = day + "/" + month + "/" + mYear;
        String time = hour + ":" + min + ":" + secs;

        return date + " @ " + time;
    }

    public static String formatTime(int seconds) {
        /**
         * You have been banned from the server!
         * By: DeveloperB
         * Reason: You suck
         * Time: 9 days 3 hours 27 minutes 34 seconds
         */
        String day = "";
        String hour = "";
        String min = "";
        String sec = "";

        int days = seconds / (60 * 60 * 24);
        if (days == 1) {
            day = "1 day";
        } else if (days > 1) {
            day = days+" days";
        }
        seconds -= days * (60 * 60 * 24);

        int hours = seconds / (60 * 60);
        if (hours == 1) {
            hour = "1 hour";
        } else if (hours > 1) {
            hour = hours+" hours";
        }
        seconds -= hours * (60 * 60);

        int minutes = seconds / 60;
        if (minutes == 1) {
            min = "1 minute";
        } else if (minutes > 1) {
            min = minutes+" minutes";
        }
        seconds -= minutes * 60;

        if (seconds == 1) {
            sec = "1 second";
        } else if (seconds > 1) {
            sec = seconds+" seconds";
        }
        String fin = day+" "+hour+" "+min+" "+sec;

        if (hour.equals("")) {
            fin = day+" "+min+" "+sec;
            if (min.equals("")) {
                fin = day+" "+sec;
            }
        } else if (min.equals("")) {
            fin = day+" "+hour+" "+sec;
        }

        return fin.trim();
    }
}
