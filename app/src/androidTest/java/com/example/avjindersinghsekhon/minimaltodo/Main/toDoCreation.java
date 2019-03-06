package com.example.avjindersinghsekhon.minimaltodo.Main;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.avjindersinghsekhon.minimaltodo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class toDoCreation {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void toDoCreation() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(
                allOf(withId(R.id.addToDoItemFAB),
                        isDisplayed())).perform(click());

       ///Adding sleep statement to allow content to load on next screen
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(
                allOf(withId(R.id.userToDoEditText),
                        isDisplayed())).perform(replaceText("Hi. This is a reminder."), closeSoftKeyboard());

        onView(
                allOf(withId(R.id.userToDoDescription),
                        isDisplayed())).perform(replaceText("Don't forget me"), closeSoftKeyboard());

         onView(
                allOf(withId(R.id.toDoHasDateSwitchCompat),
                        isDisplayed())).perform(click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(
                allOf(withId(R.id.newToDoDateTimeReminderTextView),
                        isDisplayed())).check(matches(isDisplayed()));

        onView(
                allOf(withId(R.id.userToDoDescription), withText("Don't forget me"),
                        isDisplayed())).check(matches(isDisplayed()));

        onView(
                allOf(withId(R.id.toDoHasDateSwitchCompat),
                        isDisplayed())).check(matches(isDisplayed()));

        onView(
                allOf(withId(R.id.copyclipboard),
                        isDisplayed())).check(matches(isDisplayed()));

        onView(
                allOf(withId(R.id.makeToDoFloatingActionButton),
                        isDisplayed())).check(matches(isDisplayed()));

        onView(
                allOf(withId(R.id.makeToDoFloatingActionButton),
                        isDisplayed())).perform(click());

        // Adding sleep statement before moving back to previous screen

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       onView(
                allOf(withId(R.id.toDoListItemTextview), withText("Hi. This is a reminder."),
                        isDisplayed())).check(matches(withText("Hi. This is a reminder.")));

        onView(
                allOf(withId(R.id.todoListItemTimeTextView),
                        isDisplayed())).check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
