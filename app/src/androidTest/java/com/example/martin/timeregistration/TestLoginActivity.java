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

@RunWith(AndroidJUnit4.class)
public class TestLoginActivity {

    private final String StrUsername = "martin";
    private final String StrPassword = "secret";

    private final String StrTask1Text = "Task 1:";

    private final String colorToTest = "Yellow";

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
    public void spinnerTest() {
        //Click the spinner and then click the text "Yellow"
        onView(withId(R.id.spinnerTest)).perform(click());
        onView(withText(colorToTest)).perform(click());

        //Click the "Show result" button
        onView(withId(R.id.buttonTest)).perform(click());

        onView(withId(R.id.textViewResult)).check(matches(withText(colorToTest)));

    }
}
