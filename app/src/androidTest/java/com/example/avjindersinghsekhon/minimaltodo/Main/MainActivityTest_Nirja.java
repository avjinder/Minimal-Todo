
package com.example.avjindersinghsekhon.minimaltodo.Main;


import android.content.Context;
import android.provider.Contacts;
import android.provider.ContactsContract;

import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.runner.AndroidJUnitRunner;

import com.example.avjindersinghsekhon.minimaltodo.R;


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
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)


public class MainActivityTest_Nirja {
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void activityLaunch() {
        onView(withId(R.id.addToDoItemFAB)).check(matches(isDisplayed()));
        // to check whether the plus button is visible to the user or not
        onView(withId(R.id.addToDoItemFAB)).perform(click());
        // to check whether the user able to click the button or not
        // then check whether after clicking the plus button First screen switches to second screen
        onView(withId(R.id.userToDoEditText)).check(matches(isDisplayed()));
        // Is editing text is visible or not
        onView(withId(R.id.userToDoDescription)).check(matches(isDisplayed()));
        //is description text is visible or not
        onView(withId(R.id.userToDoRemindMeTextView)).check(matches(isDisplayed()));
        // Remind me text is visible or not
        onView(withId(R.id.copyclipboard)).check(matches(isDisplayed()));
        // matches whether copy to clipboard button is present or not
        onView(withId(R.id.makeToDoFloatingActionButton)).check(matches(isDisplayed()));
        //matches whether arrow button is present or not
        onView(withId(R.id.toDoHasDateSwitchCompat)).check(matches(isDisplayed()));
        //matches whether slider is present or not

    }

    @Test
    public void textInputOutput(){
        onView(withId(R.id.addToDoItemFAB)).perform(click());
        // click the add (+) button, to add a new todo list
        onView(withId(R.id.userToDoEditText)).perform(
                typeText("This is a test."));
        //enter the task to be performed
        onView(withId(R.id.userToDoDescription)).perform(
                typeText("This is also a test."));
        //describe the task to be performed
        onView(withId(R.id.makeToDoFloatingActionButton)).perform(click());
        //click the floating button to save the task
        onView(withId(R.id.toDoRecyclerView)).perform(RecyclerViewActions.scrollToPosition(6));
        // Scroll to view item at a particular location
        onView(withId(R.id.toDoRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(6,click()));
        // click the item to view the task
    }
    //@Test
   // public void Failing_test(){
       // onView(withId(R.id.addToDoItemFAB)).perform(click());
        //click the add (+) button, to add a new todo list
       // onView(withId(R.id.userToDoEditText)).perform(
               // typeText("This is a test."));
        //onView(withId(R.id.userToDoEditText)).check(matches(withText("This is a failing test")));


}












