package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoFragment.formatDate;

public class AddTodoItemScreenTest {

    @Rule
    public ActivityTestRule<AddToDoActivity> activityTestRule =
            new ActivityTestRule<AddToDoActivity>(
                    AddToDoActivity.class,
                    true,
                    true /*lazy launch activity*/){
                @Override
                protected Intent getActivityIntent() {
                    /*added predefined intent data*/
                    Intent newTodo = new Intent();
                    ToDoItem item = new ToDoItem(
                            "",
                            "",
                            false,
                            null);
                    int color = ColorGenerator.MATERIAL.getRandomColor();
                    item.setTodoColor(color);
                    //noinspection ResourceType
                    newTodo.putExtra(MainFragment.TODOITEM, item);
                    return newTodo;
                }
            };


    @Before
    public void setUp(){
        // note instead of null, an intent object can be passed
//        activityTestRule.launchActivity(null);
        Espresso.closeSoftKeyboard();
    }

    @Test
    public void testSwitchingOnReminder() {

        onView(withId(R.id.toDoHasDateSwitchCompat))
                .check(matches(isDisplayed()))
                // switch ON reminder
                .perform(click());

        onView(withId(R.id.toDoEnterDateLinearLayout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check date and time fields is displayed to the user
        onView(withId(R.id.newTodoDateEditText))
                .check(matches(isDisplayed()));

        // check "@" text is displayed to the user
        onView(withText(R.string.at_for_time))
                .check(matches(isDisplayed()));

        // check time fields is displayed to the user
        onView(withId(R.id.newTodoTimeEditText))
                .check(matches(isDisplayed()));

//        onView(allOf(withId(R.id.toDoHasDateSwitchCompat),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(R.id.todoReminderAndDateContainerLayout),
//                                        0),
//                                2),
//                        isDisplayed()))
//                .perform(click());
    }

    @Test
    public void testDefaultDateAndTimeIsNextHour() {

        onView(withId(R.id.toDoHasDateSwitchCompat))
                .check(matches(isDisplayed()))
                // switch ON reminder
                .perform(click());

        onView(withId(R.id.toDoEnterDateLinearLayout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check date is displayed to the user
        onView(withId(R.id.newTodoDateEditText))
                // check date field shows default "Today" date
                .check(matches(withText(R.string.date_reminder_default)))
                .check(matches(isDisplayed()));

        // check "@" text is displayed to the user
        onView(withText(R.string.at_for_time))
                .check(matches(isDisplayed()));

        // check time fields is displayed to the user
        onView(withId(R.id.newTodoTimeEditText))
                // check time field shows default time (an hour in the dot from the current time)
                // for instance if current time is 12:42 PM then time field should show 1 PM
                .check(matches(withText(getDateTime())))
                .check(matches(isDisplayed()));
    }


//    private void setDateAndTimeEditText() {

//        if (mUserToDoItem.hasReminder() && mUserReminderDate != null) {
//            String userDate = formatDate("d MMM, yyyy", mUserReminderDate);
//            String formatToUse;
//            if (DateFormat.is24HourFormat(ApplicationProvider.getApplicationContext())) {
//                formatToUse = "k:mm";
//            } else {
//                formatToUse = "h:mm a";
//
//            }
//            String userTime = formatDate(formatToUse, mUserReminderDate);
//            mTimeEditText.setText(userTime);
//            mDateEditText.setText(userDate);
//
//        } else {
//            mDateEditText.setText(getString(R.string.date_reminder_default));
//            mUserReminderDate = new Date();
//            boolean time24 = DateFormat.is24HourFormat(ApplicationProvider.getApplicationContext());
//            Calendar cal = Calendar.getInstance();
//            if (time24) {
//                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
//            } else {
//                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
//            }
//            cal.set(Calendar.MINUTE, 0);
//            mUserReminderDate = cal.getTime();
//            Log.d("OskarSchindler", "Imagined Date: " + mUserReminderDate);
//            String timeString;
//            if (time24) {
//                timeString = formatDate("k:mm", mUserReminderDate);
//            } else {
//                timeString = formatDate("h:mm a", mUserReminderDate);
//            }
//        }
//    }

    private String getDateTime() {
        boolean time24 = DateFormat.is24HourFormat(ApplicationProvider.getApplicationContext());
        Calendar cal = Calendar.getInstance();
        if (time24) {
            cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
        } else {
            cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
        }
        cal.set(Calendar.MINUTE, 0);
        Date userReminderDate = cal.getTime();
        Log.d("OskarSchindler", "Imagined Date: " + userReminderDate);
        String formatString = time24 ? "k:mm" : "h:mm a";
        return formatDate(formatString, userReminderDate);
    }
}
