package com.example.league_of_legends_application

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.league_of_legends_application.helpers.ChampionDatabaseHelper
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.model.Sprite
import com.example.league_of_legends_application.model.Stats
import com.example.league_of_legends_application.repositories.ChampionRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], manifest = Config.NONE)
class ChampionRepositoryTest {

    private lateinit var repository: ChampionRepository
    private lateinit var dbHelper: ChampionDatabaseHelper

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        dbHelper = ChampionDatabaseHelper(context)
        repository = ChampionRepository(context)
    }

    @After
    fun tearDown() {
        dbHelper.writableDatabase.close()
        dbHelper.close()
    }

    @Test
    fun testInsertAndRetrieveChampion() {
        val champion = createSampleChampion()
        repository.insertChampion(champion)

        val retrievedChampion = repository.getChampionById(champion.id)
        assertNotNull("Champion should be retrieved", retrievedChampion)
        assertEquals(champion, retrievedChampion)
    }

    @Test
    fun testGetAllChampions() {
        val champions = listOf(createSampleChampion("1"), createSampleChampion("2"))
        champions.forEach { repository.insertChampion(it) }

        val retrievedChampions = repository.getAllChampions()
        assertEquals("All champions should be retrieved", champions.size, retrievedChampions.size)
        assertTrue("Retrieved champions should match inserted champions", retrievedChampions.containsAll(champions))
    }

    @Test
    fun testUpdateChampions() {
        val initialChampions = listOf(createSampleChampion("1"), createSampleChampion("2"))
        initialChampions.forEach { repository.insertChampion(it) }

        val updatedChampions = listOf(createSampleChampion("1", "Updated Name"), createSampleChampion("3"))
        repository.updateChampions(updatedChampions)

        val retrievedChampions = repository.getAllChampions()
        assertEquals("Cache should be updated with new champions", updatedChampions.size, retrievedChampions.size)
        assertTrue("Retrieved champions should match updated champions", retrievedChampions.containsAll(updatedChampions))
    }

    @Test
    fun testClearCache() {
        val champions = listOf(createSampleChampion("1"), createSampleChampion("2"))
        champions.forEach { repository.insertChampion(it) }

        repository.updateChampions(emptyList())

        val retrievedChampions = repository.getAllChampions()
        assertTrue("Cache should be empty after clearing", retrievedChampions.isEmpty())
    }

    private fun createSampleChampion(
        id: String = "1",
        name: String = "Aatrox"
    ): Champion {
        return Champion(
            id = id,
            name = name,
            title = "The Darkin Blade",
            tags = listOf("Fighter", "Tank"),
            icon = "icon_url",
            sprite = Sprite(url = "sprite_url", x = 10, y = 20),
            description = "A mighty champion",
            stats = Stats(
                hp = 580,
                hpperlevel = 90,
                mp = 0,
                mpperlevel = 0,
                movespeed = 345,
                armor = 38.0,
                armorperlevel = 3.25,
                spellblock = 32.1,
                spellblockperlevel = 1.25,
                attackrange = 175,
                hpregen = 3.0,
                hpregenperlevel = 0.75,
                mpregen = 0,
                mpregenperlevel = 0,
                crit = 0,
                critperlevel = 0,
                attackdamage = 60,
                attackdamageperlevel = 5,
                attackspeedperlevel = 2.5,
                attackspeed = 0.651
            )
        )
    }
}
