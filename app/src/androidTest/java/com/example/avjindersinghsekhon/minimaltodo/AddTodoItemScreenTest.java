package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoActivity;
import com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoFragment;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;
import com.example.avjindersinghsekhon.minimaltodo.Utility.ToDoItem;
import com.wdullaer.materialdatetimepicker.date.YearPickerView;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.avjindersinghsekhon.minimaltodo.AddToDo.AddToDoFragment.formatDate;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

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

    Calendar mCalendar = Calendar.getInstance();
    final private String AM = "AM";
    final private String PM = "PM";


    @Before
    public void setUp(){
        // note instead of null, an intent object can be passed
//        activityTestRule.launchActivity(null);
        Espresso.closeSoftKeyboard();
        mCalendar = Calendar.getInstance();
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

        Date date = AddToDoFragment.getAdjustedDate(ApplicationProvider.getApplicationContext());
        String finalString = AddToDoFragment.getFormattedDate(
                date,
                ApplicationProvider.getApplicationContext()
        );

        // check "Reminder set for ..." is displayed to the user
        onView(withId(R.id.newToDoDateTimeReminderTextView))
                .check(matches(withText(finalString)))
                .check(matches(isDisplayed()));
    }


    @Test
    public void tesDatePickerShowCorrectDate() {

        onView(withId(R.id.toDoHasDateSwitchCompat))
                .check(matches(isDisplayed()))
                // switch ON reminder
                .perform(click());

        onView(withId(R.id.toDoEnterDateLinearLayout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check date is displayed to the user
        onView(withId(R.id.newTodoDateEditText))
                // check date field shows default "Today" as date
                .check(matches(withText(R.string.date_reminder_default)))
                .check(matches(isDisplayed()))
                .perform(click());

        Calendar cal = Calendar.getInstance();
        int day_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        Log.d("TEST", "DAY_OF_WEEK: " + DAY_OF_WEEK[day_week]);

        // check date_picker_header is displayed to the user
        onView(withId(R.id.date_picker_header))
                // check day of week match the today's day of week
                .check(matches(withText(DAY_OF_WEEK[day_week])))
                .check(matches(isDisplayed()));

        // check date_picker_month_and_day is displayed to the user
        onView(withId(R.id.date_picker_month_and_day))
                .check(matches(isDisplayed()));

        int month = cal.get(Calendar.MONTH);
        Log.d("TEST", "MONTH: " + MONTH[month]);
        // check selected date_picker_month is displayed to the user
        onView(withId(R.id.date_picker_month))
                // check month match the current month
                .check(matches(withText(MONTH[month])))
                .check(matches(isDisplayed()));

        int day = cal.get(Calendar.DAY_OF_MONTH);
        Log.d("TEST", "DAY: " + day);
        // if number < 10, then add '0' in the front of the number
        String day_of_month = day < 10 ? "0" + day : "" + day;
        Log.d("TEST", "DAY_OF_MONTH: " + day_of_month);

        // check selected date_picker_day is displayed to the user
        onView(withId(R.id.date_picker_day))
                .check(matches(withText(day_of_month)))
                .check(matches(isDisplayed()));

        String year = String.valueOf(cal.get(Calendar.YEAR));
        Log.d("TEST", "YEAR: " + year);
        // check date_picker_year is displayed to the user
        onView(withId(R.id.date_picker_year))
                .check(matches(withText(year)))
                .check(matches(isDisplayed()));
    }


    @Test
    public void testTimePickerShowCorrectTime() {

        onView(withId(R.id.toDoHasDateSwitchCompat))
                .check(matches(isDisplayed()))
                // switch ON reminder
                .perform(click());

        onView(withId(R.id.toDoEnterDateLinearLayout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check time fields is displayed to the user
        onView(withId(R.id.newTodoTimeEditText))
                // check time field shows default time (an hour in the dot from the current time)
                // for instance if current time is 12:42 PM then time field should show 1 PM
                .check(matches(withText(getDateTime())))
                .check(matches(isDisplayed()))
                .perform(click());

        boolean time24 = DateFormat.is24HourFormat(ApplicationProvider.getApplicationContext());
        Log.d("TEST", "time24: " +time24);
        int hour = mCalendar.get(time24 ? Calendar.HOUR_OF_DAY : Calendar.HOUR);

        if (!time24 && hour == 0) { // transform `0` into 12 AM/PM
            hour = 12;
        }
        Log.d("TEST", "HOUR: " + mCalendar.get(Calendar.HOUR));
        Log.d("TEST", "HOUR_OF_DAY: " + mCalendar.get(Calendar.HOUR_OF_DAY));

        // check selected hour is displayed to the user
        onView(withId(R.id.hours))
                // check hour match the current hour
                .check(matches(withText(String.valueOf(hour))))
                .check(matches(isDisplayed()));

        int min = mCalendar.get(Calendar.MINUTE);
        Log.d("TEST", "MINUTE: " + min);
        // if number < 10, then add '0' in the front of the number
        String minutes = min < 10 ? "0" + min : String.valueOf(min);

        // check selected hour is displayed to the user
        onView(withId(R.id.minutes))
                // check minutes match the current minutes
                .check(matches(withText(minutes)))
                .check(matches(isDisplayed()));

        String am_pm = mCalendar.get(Calendar.AM_PM) == Calendar.AM ? AM : PM;
        Log.d("TEST", "AM_PM: " + am_pm);
        // check selected Time label (AM or PM) is displayed to the user
        onView(withId(R.id.ampm_label))
                // check minutes match the current minutes
                .check(matches(withText(am_pm)))
                .check(matches(isDisplayed()));

        // check CANCEL button is displayed to the user
        onView(allOf(withId(R.id.cancel), isAssignableFrom(Button.class)))
                // check text is "CANCEL"
                .check(matches(withText("CANCEL")))
                .check(matches(isDisplayed()))
                .perform(click());
    }


    @Test
    public void testSettingFutureDate() {

        onView(withId(R.id.toDoHasDateSwitchCompat))
                .check(matches(isDisplayed()))
                // switch ON reminder
                .perform(click());

        onView(withId(R.id.toDoEnterDateLinearLayout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check date is displayed to the user
        onView(withId(R.id.newTodoDateEditText))
                // check date field shows default "Today" as date
                .check(matches(withText(R.string.date_reminder_default)))
                .check(matches(isDisplayed()))
                .perform(click());

        Calendar cal = Calendar.getInstance();
        int day_week = cal.get(Calendar.DAY_OF_WEEK);
        Log.d("TEST", "DAY_OF_WEEK: " + DAY_OF_WEEK[day_week-1]);

        // check date_picker_header is displayed to the user
        onView(withId(R.id.date_picker_header))
                // check day of week match the today's day of week
                .check(matches(withText(DAY_OF_WEEK[day_week-1])))
                .check(matches(isDisplayed()));

        // check date_picker_month_and_day is displayed to the user
        onView(withId(R.id.date_picker_month_and_day))
                .check(matches(isDisplayed()));

        int month = cal.get(Calendar.MONTH);
        Log.d("TEST", "MONTH: " + MONTH[month]);
        // check selected date_picker_month is displayed to the user
        onView(withId(R.id.date_picker_month))
                // check month match the current month
                .check(matches(withText(MONTH[month])))
                .check(matches(isDisplayed()));

        int day = cal.get(Calendar.DAY_OF_MONTH);
        Log.d("TEST", "DAY: " + day);
        // if number < 10, then add '0' in the front of the number
        String day_of_month = day < 10 ? "0" + day : "" + day;
        Log.d("TEST", "DAY_OF_MONTH: " + day_of_month);

        // check selected date_picker_day is displayed to the user
        onView(withId(R.id.date_picker_day))
                .check(matches(withText(day_of_month)))
                .check(matches(isDisplayed()));

        int year = cal.get(Calendar.YEAR);
        String yearString = String.valueOf(year);
        Log.d("TEST", "YEAR: " + yearString);
        // check date_picker_year is displayed to the user
        onView(withId(R.id.date_picker_year))
                .check(matches(withText(yearString)))
                .check(matches(isDisplayed()))
                .perform(click());


//        onView(allOf(withId(R.id.month_text_view), withText("2019")))
//                .perform(click());

        int mininumYear = 1900; // 1900 is the minimum year displayed in the YearPicker
        int pos = year - mininumYear;
        int yearsOffset = 2;
        pos += yearsOffset; // scroll to 2 years ahead of the current year

        Log.d("TEST", "Selected YEAR: " + (year + yearsOffset));
        cal.set(Calendar.YEAR, year + yearsOffset);
        Date dateSelected = cal.getTime();

        onData(instanceOf(String.class))
                .inAdapterView(Matchers.<View>instanceOf(YearPickerView.class))
                .atPosition(pos)
//                .onChildView(withText("2021"))
                .perform(click());

        // pick the selected date
        onView(allOf(withId(R.id.ok), isAssignableFrom(Button.class)))
                // check button text is "Ok"
                .check(matches(withText("OK")))
                // check OK button is displayed to the user
                .check(matches(isDisplayed()))
                .perform(click());

        Log.d("TEST", "Selected Date: " + getFormatDate(dateSelected));

        // check date is displayed to the user
        onView(withId(R.id.newTodoDateEditText))
                // check date field shows the selected date
                .check(matches(withText(getFormatDate(dateSelected))))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testPickTimeInPast() {

        onView(withId(R.id.toDoHasDateSwitchCompat))
                .check(matches(isDisplayed()))
                // switch ON reminder
                .perform(click());

        onView(withId(R.id.toDoEnterDateLinearLayout))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        // check time fields is displayed to the user
        onView(withId(R.id.newTodoTimeEditText))
                // check time field shows default time (an hour in the dot from the current time)
                // for instance if current time is 12:42 PM then time field should show 1 PM
                .check(matches(withText(getDateTime())))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.ok), isAssignableFrom(Button.class)))
                // check minutes match the current minutes
                .check(matches(withText("OK")))
                // check OK button is displayed to the user
                .check(matches(isDisplayed()))
                .perform(click());

        // check "The date you entered is in the past." is displayed to the user
        onView(withId(R.id.newToDoDateTimeReminderTextView))
                .check(matches(withText(R.string.date_error_check_again)))
                .check(matches(isDisplayed()));
    }

    private String getDateTime() {
        boolean time24 = DateFormat.is24HourFormat(ApplicationProvider.getApplicationContext());
        Date date = AddToDoFragment.getAdjustedDate(ApplicationProvider.getApplicationContext());
        return getFormatTime(date, time24);
    }

    private String getFormatTime(Date userReminderDate, boolean time24) {
        Log.d("TEST", "Imagined Date: " + userReminderDate);
        String formatString = time24 ? "k:mm" : "h:mm a";
        return formatDate(formatString, userReminderDate);
    }

    private String getFormatDate(Date userReminderDate) {
        Log.d("TEST", "Imagined Date: " + userReminderDate);
        String formatString = "d MMM, yyyy";
        return formatDate(AddToDoFragment.DATE_FORMAT, userReminderDate);
    }

    final private String[] DAY_OF_WEEK = {
            "SUNDAY",
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY"
    };

    final private String[] MONTH = {
            "JAN",
            "FEB",
            "MAR",
            "APR",
            "MAY",
            "JUN",
            "JUL",
            "AUG",
            "SEP",
            "OCT",
            "NOV",
            "DEC"
    };
}
