package com.example.league_of_legends_application.tests

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.league_of_legends_application.ui.screens.HomeScreen
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import org.junit.Rule
import org.junit.Test

class ChampionRandomizerSteps {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Given("^the user is on the Home Screen$")
    fun givenUserIsOnHomeScreen() {
        composeTestRule.setContent {
            HomeScreen(onStartClick = {}, onRandomizerClick = {})
        }
    }

    @When("^the user clicks on the \"Create Random Teams\" button$")
    fun whenUserClicksCreateRandomTeams() {
        composeTestRule.onNodeWithText("Create Random Teams")
            .performClick()
    }

    @Then("^the user should be redirected to the Champion Randomizer screen$")
    fun thenUserIsRedirectedToChampionRandomizer() {
        composeTestRule.onNodeWithText("Create Teams").assertIsDisplayed()
    }

    @When("^the user clicks on the \"Randomize\" button$")
    fun whenUserClicksRandomize() {
        composeTestRule.onNodeWithText("Randomize").performClick()
    }

    @Then("^the system should display two teams with champions assigned to roles$")
    fun thenSystemDisplaysTeamsWithChampions() {
        composeTestRule.onNodeWithText("Top").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jungle").assertIsDisplayed()
        composeTestRule.onNodeWithText("Mid").assertIsDisplayed()
        composeTestRule.onNodeWithText("ADC").assertIsDisplayed()
        composeTestRule.onNodeWithText("Support").assertIsDisplayed()
    }

    @When("^the user clicks on a champion's icon$")
    fun whenUserClicksChampionIcon() {
        composeTestRule.onNodeWithContentDescription("Champion Icon")
            .performClick()
    }

    @Then("^the system should open a dialog showing a list of items for that champion$")
    fun thenSystemShowsItemsDialog() {
        composeTestRule.onNodeWithText("Itens para")
            .assertIsDisplayed()
    }

    @When("^the user clicks on an item$")
    fun whenUserClicksItem() {
        composeTestRule.onNodeWithText("Item Name") // Replace with actual item name
            .performClick()
    }

    @Then("^the system should display the item's description$")
    fun thenSystemDisplaysItemDescription() {
        composeTestRule.onNodeWithText("Item Description") // Replace with actual description
            .assertIsDisplayed()
    }
}
