package com.gokul.itunes


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFormFragmentTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchFormFragmentTest() {

        queryInputSearch("Bow wow")
        clickInputSearch()
    }

    @Test
    fun emptyQuery(){
        queryInputSearch("")
        clickInputSearch()
        onView(allOf(withId(R.id.btn_query))).check(matches(isDisplayed()))
    }

    @Test
    fun resultNoFound(){
        queryInputSearch("vvewvywevyuew")
        clickInputSearch()
        onView(allOf(withId(R.id.shimmer_view_container))).check(matches(isDisplayed()))
        // noFound()

    }

    fun queryInputSearch(value: String){
        val appCompatEditText = onView(
            allOf(
                withId(R.id.et_query),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(ViewActions.replaceText(value), ViewActions.closeSoftKeyboard())
    }

    fun clickInputSearch(){
        val appCompatButton = onView(
            allOf(
                withId(R.id.btn_query), withText("Search"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.RelativeLayout")),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())
    }

    fun noFound(){
        val imageView = onView(
            allOf(
                withId(R.id.iv_result_not_found),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.frag_host),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        imageView.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
