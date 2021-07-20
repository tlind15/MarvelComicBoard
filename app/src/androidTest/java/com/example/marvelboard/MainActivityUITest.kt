package com.example.marvelboard

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.marvelboard.databinding.ActivityMainBinding
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    val comicFake = Comic("Test title", "Test description", "Test Url")

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @BindValue
    @JvmField
    val mockController = mockk<ComicController>().apply {
        coEvery { fetchComic(any()) } returns MutableStateFlow(comicFake)
    }


    @Test
    fun verifyTitleText() {
        onView(withId(R.id.comicTitle)).check(matches(withText(comicFake.title)))
    }

    @Test
    fun verifyDescriptionText() {
        onView(withId(R.id.comicDescription)).check(matches(withText(comicFake.description)))
    }
}