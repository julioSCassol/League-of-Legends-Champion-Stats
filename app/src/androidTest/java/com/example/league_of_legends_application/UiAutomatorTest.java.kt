package com.example.league_of_legends_application

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionListTest {
    @JvmField
    @Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testChampionListNavigation() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        val exploreChampionsButton = device.findObject(
            UiSelector().text("Explore Champions")
        )
        exploreChampionsButton.click()


        val filterButton = device.findObject(UiSelector().className("android.widget.Button").instance(1)) //foi por indice pq de outro jeito n da
        filterButton.click()


        val championListItem = device.findObject(UiSelector().textContains("Aatrox"))
        assertTrue("Champion list did not display", championListItem.exists())

        // clica no primeiro champs da lista
        championListItem.click()

        // acha pelo nome do champs
        val championDetailName = device.findObject(UiSelector().textContains("Aatrox"))
        assertTrue("Champion detail screen did not open", championDetailName.exists())
    }
}
