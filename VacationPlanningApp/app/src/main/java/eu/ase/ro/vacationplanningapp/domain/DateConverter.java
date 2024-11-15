package eu.ase.ro.vacationplanningapp.domain;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    public static SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    public static Date toDate(String date)  {
        try {
            return FORMAT.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String toString(Date date){
        if (date == null) {
            return null;
        }
        return FORMAT.format(date);
    }
}
