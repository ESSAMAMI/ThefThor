package fr.app.theft.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.util.HashMap;

public class DateFormatter {



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate stringToLocalDate(String date, String sep){

        String[] array = date.split(sep);

        return LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
    }

    public static int maxDayPerMonth(int month){
        HashMap<Integer, Integer> maxDayPerMonth = new HashMap<Integer, Integer>();
        maxDayPerMonth.put(1,31);
        maxDayPerMonth.put(2,28);
        maxDayPerMonth.put(3,31);
        maxDayPerMonth.put(4,30);
        maxDayPerMonth.put(5,31);
        maxDayPerMonth.put(6,30);
        maxDayPerMonth.put(7,31);
        maxDayPerMonth.put(8,31);
        maxDayPerMonth.put(9,30);
        maxDayPerMonth.put(10,31);
        maxDayPerMonth.put(11,30);
        maxDayPerMonth.put(12,31);
        return maxDayPerMonth.get(month);
    }
    public static int getPreviousDateOfMonth(){

        return 0;
    }
}
