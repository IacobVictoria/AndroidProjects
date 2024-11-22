package eu.ase.ro.quiztema3.domain;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConverter {
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

    public static Date toDate(String date) {
        try {
            return FORMAT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toString(Date date) {
        if(date != null){
            return FORMAT.format(date);
        }
        return  null;
    }

}
