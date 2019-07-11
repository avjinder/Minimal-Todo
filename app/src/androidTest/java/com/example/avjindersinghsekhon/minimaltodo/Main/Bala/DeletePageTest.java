package com.example.avjindersinghsekhon.minimaltodo.Main.Bala;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DeletePageTest {
    private static final String TITLE = "Test Title to Delete";
    private static final String DESCRIPTION = "Test Description";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        onView(withId(R.id.addToDoItemFAB)).perform(click());
        onView(withId(R.id.userToDoEditText)).perform(typeText(TITLE), closeSoftKeyboard());
        onView(withId(R.id.userToDoDescription)).perform(typeText(DESCRIPTION), closeSoftKeyboard());
        onView(withId(R.id.makeToDoFloatingActionButton)).perform(click());
    }
    @Test
    public void delete() {
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.toDoRecyclerView);
        int itemCount = recyclerView.getAdapter().getItemCount();
        System.out.println(itemCount);
        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.listItemLinearLayout),
                        childAtPosition(
                                allOf(withId(R.id.toDoRecyclerView),
                                        childAtPosition(
                                                withId(R.id.myCoordinatorLayout),
                                                1)),
                                0),
                        isDisplayed()));
        linearLayout.perform(swipeLeft());

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
