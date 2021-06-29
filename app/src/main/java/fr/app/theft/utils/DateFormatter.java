package fr.app.theft.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class DateFormatter {



    @RequiresApi(api = Build.VERSION_CODES.O)
    public static LocalDate stringToLocalDate(String date, String sep){

        String[] array = date.split(sep);

        return LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]), Integer.parseInt(array[2]));
    }
}
