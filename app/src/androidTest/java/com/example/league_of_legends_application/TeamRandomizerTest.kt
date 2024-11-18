package com.example.league_of_legends_application

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamRandomizerTest {

    @JvmField
    @Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    @Throws(Exception::class)
    fun testTeamRandomizationAndDialogNavigation() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        val randomizerButton = device.findObject(UiSelector().text("Create Random Teams"))
        randomizerButton.click()

        val randomizeButton = device.findObject(UiSelector().text("Randomize"))
        randomizeButton.click()

        randomizeButton.click()

        val whatsapp = device.findObject(UiSelector().description("Compartilhar no WhatsApp"))
        assertTrue("Botão do WhatsApp não apareceu", whatsapp.exists())
        whatsapp.click()

        val championTextView = device.findObject(
            UiSelector().className("android.widget.TextView").textMatches(".*\\S.*")
        )

        assertTrue("Nenhum campeão encontrado", championTextView.exists())
        championTextView.click()

        device.wait(Until.hasObject(By.text("Champion Details")), 5000)
    }
}
