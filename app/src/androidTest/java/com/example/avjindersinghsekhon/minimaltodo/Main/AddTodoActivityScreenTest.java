package com.example.avjindersinghsekhon.minimaltodo.Main;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.avjindersinghsekhon.minimaltodo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddTodoActivityScreenTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

//    @Test
    public void addTodoActivityScreenTest() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.addToDoItemFAB),
                        childAtPosition(
                                allOf(withId(R.id.myCoordinatorLayout),
                                        childAtPosition(
                                                withId(R.id.myParentLayout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.addToDoItemFAB),
                        childAtPosition(
                                allOf(withId(R.id.myCoordinatorLayout),
                                        childAtPosition(
                                                withId(R.id.myParentLayout),
                                                0)),
                                2),
                        isDisplayed()));
        floatingActionButton2.perform(click());

        ViewInteraction switchCompat = onView(
                allOf(withId(R.id.toDoHasDateSwitchCompat),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.todoReminderAndDateContainerLayout),
                                        0),
                                2),
                        isDisplayed()));
        switchCompat.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.newTodoDateEditText), withText("Today"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toDoEnterDateLinearLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction accessibleTextView = onView(
                allOf(withId(R.id.date_picker_year), withText("2019"),
                        childAtPosition(
                                allOf(withId(R.id.day_picker_selected_date_layout),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1)));
        accessibleTextView.perform(scrollTo(), click());

        DataInteraction textViewWithCircularIndicator = onData(anything())
                .inAdapterView(childAtPosition(
                        allOf(withId(R.id.animator), withContentDescription("Year list: 2019")),
                        1))
                .atPosition(122);
        textViewWithCircularIndicator.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.ok), withText("OK"),
                        childAtPosition(
                                allOf(withId(R.id.done_background),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                1)),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.newTodoDateEditText), withText("27 Sep, 2022"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.toDoEnterDateLinearLayout),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction view = onView(
                allOf(withContentDescription("08 August 2019"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        0),
                                7),
                        isDisplayed()));
        view.check(matches(isDisplayed()));

        ViewInteraction view2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                allOf(withId(R.id.animator), withContentDescription("Month grid of days: July 10")),
                                0),
                        1),
                        isDisplayed()));
        view2.check(matches(isDisplayed()));

        ViewInteraction view3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                allOf(withId(R.id.animator), withContentDescription("Month grid of days: July 10")),
                                0),
                        1),
                        isDisplayed()));
        view3.check(matches(isDisplayed()));

        ViewInteraction listView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.animator), withContentDescription("Month grid of days: July 10"),
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                        2)),
                        0),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        ViewInteraction view4 = onView(
                allOf(withContentDescription("11 October 2019"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        0),
                                10),
                        isDisplayed()));
        view4.check(matches(isDisplayed()));

        ViewInteraction view5 = onView(
                allOf(withContentDescription("11 October 2019"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.widget.ListView.class),
                                        0),
                                10),
                        isDisplayed()));
        view5.check(matches(isDisplayed()));
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
