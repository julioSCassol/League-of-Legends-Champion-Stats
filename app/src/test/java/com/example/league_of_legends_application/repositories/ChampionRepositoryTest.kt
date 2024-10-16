package com.example.league_of_legends_application.repositories

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.league_of_legends_application.helpers.ChampionDatabaseHelper
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.model.Sprite
import com.example.league_of_legends_application.model.Stats
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChampionRepositoryTest {

    private lateinit var repository: ChampionRepository
    private lateinit var dbHelper: ChampionDatabaseHelper
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val testChampion = Champion(
        id = "1",
        key = "Aatrox",
        name = "Aatrox",
        title = "the Darkin Blade",
        tags = listOf("Fighter", "Tank"),
        icon = "icon_url",
        description = "Some description",
        sprite = Sprite(url = "sprite_url", x = 10, y = 20),
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

    @Before
    fun setup() {
        dbHelper = ChampionDatabaseHelper(context)
        repository = ChampionRepository(context)
        dbHelper.writableDatabase // Ensure database is created
    }

    @After
    fun teardown() {
        dbHelper.close()
        context.deleteDatabase(ChampionDatabaseHelper.DATABASE_NAME)
    }

    @Test
    fun testInsertChampion() {
        repository.insertChampion(testChampion)
        val retrievedChampion = repository.getChampionById("1")

        assertNotNull(retrievedChampion)
        assertEquals(testChampion.id, retrievedChampion?.id)
        assertEquals(testChampion.key, retrievedChampion?.key)
        assertEquals(testChampion.name, retrievedChampion?.name)
        assertEquals(testChampion.title, retrievedChampion?.title)
        assertEquals(testChampion.tags, retrievedChampion?.tags)
        assertEquals(testChampion.icon, retrievedChampion?.icon)
        assertEquals(testChampion.description, retrievedChampion?.description)
        assertEquals(testChampion.sprite, retrievedChampion?.sprite)
        assertEquals(testChampion.stats, retrievedChampion?.stats)
    }

    @Test
    fun testGetChampionById_notFound() {
        val champion = repository.getChampionById("unknown_id")
        assertNull(champion)
    }

    @Test
    fun testGetAllChampions_emptyInitially() {
        val champions = repository.getAllChampions()
        assertTrue(champions.isEmpty())
    }

    @Test
    fun testGetAllChampions_afterInsertions() {
        repository.insertChampion(testChampion)
        val champions = repository.getAllChampions()

        assertEquals(1, champions.size)
        val champion = champions[0]
        assertEquals(testChampion.id, champion.id)
        assertEquals(testChampion.key, champion.key)
        assertEquals(testChampion.name, champion.name)
        assertEquals(testChampion.title, champion.title)
        assertEquals(testChampion.tags, champion.tags)
        assertEquals(testChampion.icon, champion.icon)
        assertEquals(testChampion.description, champion.description)
        assertEquals(testChampion.sprite, champion.sprite)
        assertEquals(testChampion.stats, champion.stats)
    }

    @Test
    fun testUpdateChampions_cacheUpdate() {
        repository.insertChampion(testChampion)
        val updatedChampion = testChampion.copy(name = "Updated Aatrox")
        repository.updateChampions(listOf(updatedChampion))

        val retrievedChampion = repository.getChampionById("1")
        assertNotNull(retrievedChampion)
        assertEquals("Updated Aatrox", retrievedChampion?.name)
    }

    @Test
    fun testUpdateChampions_noChange() {
        repository.insertChampion(testChampion)
        repository.updateChampions(listOf(testChampion))

        val champions = repository.getAllChampions()
        assertEquals(1, champions.size)
        assertEquals(testChampion, champions[0])
    }

    @Test
    fun testClearCache() {
        repository.insertChampion(testChampion)
        assertTrue(repository.getAllChampions().isNotEmpty())

        repository.updateChampions(emptyList())
        val championsAfterClear = repository.getAllChampions()
        assertTrue(championsAfterClear.isEmpty())
    }
}
