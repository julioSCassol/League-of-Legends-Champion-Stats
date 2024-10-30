package com.example.league_of_legends_application.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.viewmodel.ChampionViewModel
import com.example.league_of_legends_application.viewmodel.ItemViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionRandomizerScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testChampions = List(10) { index ->
        Champion(
            id = "$index",
            key = "Champion$index",
            name = "Champion $index",
            title = "Title $index",
            tags = listOf("Tag1", "Tag2"),
            icon = "",
            description = "Description $index",
            sprite = null,
            stats = null
        )
    }

    @Test
    fun testTeamRandomizationUpdatesUI() {
        val mockViewModel = mockk<ChampionViewModel>()
        val mockItemViewModel = mockk<ItemViewModel>()
        coEvery { mockViewModel.champions } returns MutableStateFlow(testChampions)

        composeTestRule.setContent {
            ChampionRandomizerScreen(viewModel = mockViewModel, itemViewModel = mockItemViewModel, onBackClick = {})
        }

        // Click the randomize button
        composeTestRule.onNodeWithText("Randomizar").performClick()

        // Check if team names are displayed in UI
        composeTestRule.onNodeWithText("Equipe 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Equipe 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Champion 0").assertIsDisplayed()
        composeTestRule.onNodeWithText("Champion 1").assertIsDisplayed()
    }
}
