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

        // Navegar para a tela de criação de equipes
        val randomizerButton = device.findObject(UiSelector().text("Create Random Teams"))
        randomizerButton.click()

        // Clicar no botão para randomizar equipes
        val randomizeButton = device.findObject(UiSelector().text("Randomize"))
        randomizeButton.click()

        randomizeButton.click()

        device.wait(Until.findObject(By.textContains("Champion Details")), 5000)

        // Variável para indicar se o dialog do campeão foi aberto
        var dialogOpened = false
        val maxAttempts = 3
        var attempts = 0

        // Loop para tentar clicar em um campeão até que o dialogo seja aberto
        while (!dialogOpened && attempts < maxAttempts) {
            val championTextView = device.findObject(
                UiSelector().className("android.widget.TextView").textMatches(".*\\S.*")
            )
            assertTrue("Nenhum campeão encontrado para clique", championTextView.exists())
            championTextView.click()

            // Verificar se o diálogo do campeão foi aberto
            val championDialog = device.findObject(UiSelector().textContains("Champion Details"))
            device.wait(Until.hasObject(By.text("Champion Details")), 3000)
            dialogOpened = championDialog.exists()
            attempts++
        }

        val itemImage = device.findObject(UiSelector().className("android.widget.ImageView"))
        assertTrue("Nenhuma imagem de item encontrada para clique", itemImage.exists())
        itemImage.click()
    }
}
