package com.example.martin.timeregistration.Tools;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.view.View;
import android.widget.TimePicker;

import org.hamcrest.Matcher;

public class ViewActions {
    public static ViewAction setTimePicker(final int hours, final int minutes) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }

            @Override
            public String getDescription() {
                return "Sets the values of a TimePicker";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TimePicker timePicker = (TimePicker) view;
                timePicker.setCurrentHour(hours);
                timePicker.setCurrentMinute(minutes);
            }
        };
    }
}
