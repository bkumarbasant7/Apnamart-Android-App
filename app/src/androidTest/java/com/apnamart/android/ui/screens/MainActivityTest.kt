package com.apnamart.android.ui.screens

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.apnamart.android.R
import org.junit.Before
import org.junit.Test


class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario.moveToState(Lifecycle.State.STARTED)

    }
    @Test
    fun testRepositoriesView(){
        onView(withId(R.id.trending_container))
    }
}