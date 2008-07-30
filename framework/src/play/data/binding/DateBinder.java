package play.data.binding;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 * a Date binder
 * 
 */
public class DateBinder implements SupportedType<Date> {

    // as SimpleDateFormat are not thread safe, we wrap it in a ThreadLocal
    static ThreadLocal<AlternativeDateFormat> dateformat = new ThreadLocal<AlternativeDateFormat>();
    static {
        dateformat.set(new AlternativeDateFormat(Locale.US,
                "yyyy-MM-dd'T'hh:mm:ss'Z'", // ISO8601 + timezone
                "yyyy-MM-dd'T'hh:mm:ss", // ISO8601
                "yyyy-MM-dd",
                "yyyyMMdd'T'hhmmss",
                "yyyyMMddhhmmss",
                "dd/MM/yyyy",
                "dd-MM-yyyy",
                "ddMMyyyy"));
    }

    public Date bind(String value) {
        try {
            return dateformat.get().parse(value);
        } catch (ParseException ex) {
            play.Logger.warn("failed to parse date (%s)", value);
        }
        return null;
    }
}
