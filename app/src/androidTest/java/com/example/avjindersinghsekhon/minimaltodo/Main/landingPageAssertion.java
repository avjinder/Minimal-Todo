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
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class landingPageAssertion {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void landingPageAssertion() {
        ///////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////
        //This test is only asserting that all expected elements are on the page///
        ///////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(
                allOf(withText("Minimal"),
//                        childAtPosition(
//                                allOf(withId(R.id.toolbar),
//                                        childAtPosition(
//                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
//                                                0)),
//                                0),
/////////////////////////////////////////////////////////////////////
//  The code above was left in, but commented out because it's not //
//  necessary in this instance as this apps is pretty bare bones.  //
//  Child position is important though when dealing with multiple  //
//  elements within the same view in larger apps                   //
/////////////////////////////////////////////////////////////////////
                        isDisplayed())).check(matches(withText("Minimal")));

        onView(
                allOf(withContentDescription("More options"),
                        isDisplayed())).check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.toDoEmptyView),
                                childAtPosition(
                                        withId(R.id.myCoordinatorLayout),
                                        0)),
                        0),
                        isDisplayed()));
        imageView2.check(matches(isDisplayed()));

        onView(
                allOf(withText("You don't have any todos"),
                        isDisplayed())).check(matches(withText("You don't have any todos")));

        onView(
                allOf(withId(R.id.addToDoItemFAB),
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
