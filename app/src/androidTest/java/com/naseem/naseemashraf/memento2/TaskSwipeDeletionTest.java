package com.naseem.naseemashraf.memento2;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.naseem.naseemashraf.memento2.activity.TaskActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class TaskSwipeDeletionTest {

  @Rule
  public ActivityTestRule<TaskActivity> mActivityTestRule =
      new ActivityTestRule<>(TaskActivity.class);

    @After
    public void cleanUp() {
        mActivityTestRule.getActivity().deleteDatabase("tasks_table");
    }

  @Test
  public void taskSwipeDeleteTest() {
    ViewInteraction textView =
        onView(
            allOf(
                withId(R.id.tvTask),
                withText("Your Task Here."),
                childAtPosition(childAtPosition(withId(R.id.tasks_recycler_view), 0), 1),
                isDisplayed()));
    textView.check(matches(isDisplayed()));

    textView.perform(swipeRight());
    textView.check(doesNotExist());
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
        return parent instanceof ViewGroup
            && parentMatcher.matches(parent)
            && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }
}
