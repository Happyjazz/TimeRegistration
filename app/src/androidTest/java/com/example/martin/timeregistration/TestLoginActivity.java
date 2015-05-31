package com.example.martin.timeregistration;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.martin.timeregistration.Tools.ViewActions.setTimePicker;

@RunWith(AndroidJUnit4.class)
public class TestLoginActivity {

    private final String StrUsername = "martin";
    private final String StrPassword = "secret";

    private final String StrTask1Text = "Task 1:";

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void login_Once() {

        //Type in username and password, then press login button
        onView(withId(R.id.editTextUserName))
                .perform(typeText(StrUsername), closeSoftKeyboard());
        onView(withId(R.id.editTextPassword))
                .perform(typeText(StrPassword), closeSoftKeyboard());
        onView(withId(R.id.buttonLogin))
                .perform(click());

        //Check that the text on textViewTask1 matches with StrTask1Text
        onView(withId(R.id.textViewTask1)).check(matches(withText(StrTask1Text)));
    }

    @Test
    public void timePickerSet() {
        onView(withId(R.id.timePickerTest)).perform(setTimePicker(10, 25));
    }
}
