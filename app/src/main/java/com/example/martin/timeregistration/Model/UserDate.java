package com.example.martin.timeregistration.Model;

import android.text.format.DateFormat;

import java.util.Calendar;

/**
 * Created by Martin on 09-03-2015.
 */
public class UserDate {
    private final User user;
    private final Calendar date;

    public UserDate(final User user, final Calendar date) {
        this.user = user;
        this.date = date;
    }

    public Calendar getDate() {
        return date;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        final CharSequence dateString = DateFormat.format("EEE, d MMM yyyy",
                date);
        return user + " " + dateString;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserDate))
            return false;
        final UserDate other = (UserDate) obj;
        return this.user.equals(other.user) && sameDay(this.date, other.date);
        // date.equals(date) takes time (hours, seconds, etc) into account, not
        // only the date
    }

    private static boolean sameDay(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
                && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                && date1.get(Calendar.DATE) == date1.get(Calendar.DATE);
    }

    @Override
    public int hashCode() {
        return user.hashCode() + date.get(Calendar.YEAR)
                + date.get(Calendar.MONTH) + date.get(Calendar.DATE);
    }
}
