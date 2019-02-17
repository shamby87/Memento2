package com.naseem.naseemashraf.memento2;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.TextView;

import com.naseem.naseemashraf.memento2.activity.TaskActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnitRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class TaskActivityTests extends AndroidJUnitRunner {

    @Rule
    public ActivityTestRule<TaskActivity> mTaskActivityActivityTestRule =
            new ActivityTestRule<TaskActivity>(TaskActivity.class);

    @Before
    public void setUp() {

    }

    @After
    public void cleanUp() {
        mTaskActivityActivityTestRule.getActivity().deleteDatabase("tasks_table");
    }

    @Test
    public void checkTaskRecyclerFirstItem() {
        String taskTitle = "Your Task Here.";
        onView(withText(taskTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void checkTaskRecyclerFirstItem_onEdit() {
        String taskTitle = "Your Task Here.";
        onView(withText(taskTitle)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.tasks_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.EditTextTitle)).check(matches(isDisplayed()));

        onView(withId(R.id.EditTextTitle)).check(matches(isEditTextValueEqualTo(taskTitle)));
    }

    @Test
    public void clickAddTaskFAB_opensAddTaskBottomSheetDialog() {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.bottom_sheet))
                .check(matches(isDisplayed()));
    }

    @Test
    public void addTask_saveTask_checkRecycler() {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.EditTextTitle))
                .perform(typeText("Test Task Title."))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.ButtonSave))
                .perform(click());

        String taskTitle = "Test Task Title.";
        onView(allOf(
                withId(R.id.tvTask),
                withText(taskTitle)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void addTask_saveTask_editTask() {
        onView(withId(R.id.fab))
                .perform(click());

        onView(withId(R.id.EditTextTitle))
                .perform(typeText("Test Task Title."))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.ButtonSave))
                .perform(click());

        String taskTitle = "Test Task Title.";
        onView(withText(taskTitle)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.tasks_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1,click()));

        onView(withId(R.id.EditTextTitle))
                .perform(ViewActions.clearText(),typeText("Test Task Title Edited."))
                .perform(ViewActions.closeSoftKeyboard());

        onView(withId(R.id.ButtonSave))
                .perform(click());

        taskTitle = "Test Task Title Edited.";
        onView(withText(taskTitle)).check(matches(isDisplayed()));
    }

    Matcher<View> isEditTextValueEqualTo(final String content) {

        return new TypeSafeMatcher<View>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("Match Edit Text Value with View ID Value : :  " + content);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof TextView) && !(view instanceof EditText)) {
                    return false;
                }
                if (view != null) {
                    String text;
                    if (view instanceof TextView) {
                        text =((TextView) view).getText().toString();
                    } else {
                        text =((EditText) view).getText().toString();
                        Log.e("WAW","View Value is : "+text);
                    }

                    return (text.equalsIgnoreCase(content));
                }
                return false;
            }
        };
    }
}
