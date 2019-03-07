package com.example.avjindersinghsekhon.minimaltodo.Main;


import android.support.test.espresso.DataInteraction;
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

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class settingsTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void settingsTest() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       onView(
                allOf(withContentDescription("More options"),
                        isDisplayed())).check(matches(isDisplayed())).perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.title), withText("Settings"),
                        isDisplayed()));
        textView.check(matches(withText("Settings")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.title), withText("About"),
                        isDisplayed()));
        textView2.check(matches(withText("About")));

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.title), withText("Settings"),
                        isDisplayed()));
        appCompatTextView.perform(click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
                allOf(withText("Minimal"),
                        isDisplayed()));
        textView3.check(matches(withText("Minimal")));

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.summary), withText("Night mode is off"),
                        isDisplayed()));
        textView4.check(matches(withText("Night mode is off")));


///
/// Turn night mode on
///

        DataInteraction linearLayout = onData(anything())
                .inAdapterView(allOf(withId(android.R.id.list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(0);
        linearLayout.perform(click());


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
///
///Assert that night mode is on
///
        ViewInteraction textView5 = onView(
                allOf(withId(android.R.id.summary), withText("Night mode is on"),
                        isDisplayed()));
        textView5.check(matches(withText("Night mode is on")));

        ViewInteraction imageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        isDisplayed()));
        imageButton.perform(click());

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
