package com.example.avjindersinghsekhon.minimaltodo.Main;


import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.avjindersinghsekhon.minimaltodo.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)


public class MainActivityTest_Nirja {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);

    @Before
    public void setUp() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addToDoItemFAB))
                // to check whether the plus button is visible to the user or not
                .check(matches(isDisplayed()))
                .perform(click());
    }

    //Neeraja what's this test for? I don't think we need to test the constext of the App.
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//
//        assertEquals("com.example.avjindersinghsekhon.minimaltodo",
//                appContext.getPackageName());
//    }


    @Test
    public void activityLaunch() {
        // to check whether the user able to click the button or not
        // then check whether after clicking the plus button First screen switches to second screen
        onView(withId(R.id.userToDoEditText)).check(matches(isDisplayed()));
        // Is editing text is visible or not
        onView(withId(R.id.userToDoDescription)).check(matches(isDisplayed()));
        //is description text is visible or not
        onView(withId(R.id.userToDoRemindMeTextView)).check(matches(isDisplayed()));
        // Remind me text is visible or not
        onView(withId(R.id.copyclipboard)).check(matches(isDisplayed()));
        // copy to clipboard
        onView(withId(R.id.makeToDoFloatingActionButton)).check(matches(isDisplayed()));
        //arrow button
        onView(withId(R.id.toDoHasDateSwitchCompat)).check(matches(isDisplayed()));
        //slider
    }

    @Test
    public void textInputOutput() {
        onView(withId(R.id.userToDoEditText)).perform(
                typeText("This is a test."));
        onView(withId(R.id.userToDoDescription)).perform(
                typeText("This is also a test."));

        onView(withId(R.id.makeToDoFloatingActionButton)).perform(click());

        // This is will cause the test to fail because
        // this is not a reliable way to test data in a RecyclerView
        //We need to use the Espresso API for a RecyclerView in order to check the data on RecyclerView
        onView(withId(R.id.toDoListItemTextview)).check(
                matches(withText("This is a test.")));
    }

}


