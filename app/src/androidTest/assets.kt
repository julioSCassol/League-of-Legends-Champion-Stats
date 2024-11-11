class assets {
    Feature: Randomizer Champion Flow

    Scenario: User navigates from the Home Screen to the Champion Randomizer, randomizes teams, and views an item description
    Given the user is on the Home Screen
    When the user clicks on the "Create Random Teams" button
    Then the user should be redirected to the Champion Randomizer screen
    When the user clicks on the "Randomize" button
    Then the system should display two teams with champions assigned to roles
    When the user clicks on a champion's icon
    Then the system should open a dialog showing a list of items for that champion
    When the user clicks on an item
    Then the system should display the item's description
}