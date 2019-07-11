package com.example.avjindersinghsekhon.minimaltodo;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.avjindersinghsekhon.minimaltodo.Main.MainActivity;
import com.example.avjindersinghsekhon.minimaltodo.Main.MainFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MainActivityTest {

    Context mContext;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp(){
        mContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        boolean deleted = mActivityTestRule.getActivity().deleteFile(MainFragment.FILENAME);
        Log.d("TEST", "1st deleted: " + deleted);

        mContext.deleteFile(MainFragment.FILENAME);

        Log.d("TEST", "2sd deleted: " + deleted);
        File dir = mContext.getFilesDir();
        File file = new File(dir, MainFragment.FILENAME);
        deleted = file.delete();

        Log.d("TEST", "deleted: " + deleted);
    }

    private void deleteTempFolder(String dir) {
        File myDir = new File(Environment.getExternalStorageDirectory() + "/"+dir);
        if (myDir.isDirectory()) {
            String[] children = myDir.list();
            for (int i = 0; i < children.length; i++) {
                new File(myDir, children[i]).delete();
            }
        }
    }

    @Test
    public void testEmptyStateViewsDisplayed() {   // Looking for floating action button in main screen.
        onView(withId(R.id.addToDoItemFAB))            // withId(R.id.my_view) is a ViewMatcher
                .check(matches(isDisplayed())); // matches(isDisplayed()) is a ViewAssertion

//        onView(withText("You don't have any todos"))
//                .check(matches(isDisplayed()));
//
//        // check that ImageView for empty list in visible to user
//        onView(withId(R.id.empty_todo_imageView))
//                .check(matches(isDisplayed()));
    }

}
