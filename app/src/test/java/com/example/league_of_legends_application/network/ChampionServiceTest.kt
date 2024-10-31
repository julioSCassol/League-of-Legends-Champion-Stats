package com.example.league_of_legends_application.network

import com.example.league_of_legends_application.model.Sprite
import com.example.league_of_legends_application.model.Stats
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.json.JSONArray
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], manifest = Config.NONE)
class ChampionServiceTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        ChampionService.baseUrl = mockWebServer.url("/").toString()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testFetchChampions_success() = runTest {
        val jsonResponse = """
            [
                {
                    "id": "1",
                    "key": "Aatrox",
                    "name": "Aatrox",
                    "title": "the Darkin Blade",
                    "tags": ["Fighter", "Tank"],
                    "icon": "icon_url",
                    "description": "Some description",
                    "sprite": {"url": "sprite_url", "x": 10, "y": 20},
                    "stats": {
                        "hp": 580,
                        "hpperlevel": 90,
                        "mp": 0,
                        "mpperlevel": 0,
                        "movespeed": 345,
                        "armor": 38.0,
                        "armorperlevel": 3.25,
                        "spellblock": 32.1,
                        "spellblockperlevel": 1.25,
                        "attackrange": 175,
                        "hpregen": 3.0,
                        "hpregenperlevel": 0.75,
                        "mpregen": 0,
                        "mpregenperlevel": 0,
                        "crit": 0,
                        "critperlevel": 0,
                        "attackdamage": 60,
                        "attackdamageperlevel": 5,
                        "attackspeedperlevel": 2.5,
                        "attackspeed": 0.651
                    }
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        val champions = ChampionService.fetchChampions()
        assertNotNull("Champions list should not be null", champions)
        assertEquals("Expected 1 champion", 1, champions.size)

        val champion = champions[0]
        assertEquals("1", champion.id)
        assertEquals("Aatrox", champion.key)
        assertEquals("Aatrox", champion.name)
        assertEquals("the Darkin Blade", champion.title)
        assertEquals("icon_url", champion.icon)
        assertEquals("Some description", champion.description)

        val tags = listOf("Fighter", "Tank")
        assertEquals(tags, champion.tags)

        val sprite = Sprite("sprite_url", 10, 20)
        assertEquals(sprite, champion.sprite)

        val stats = Stats(
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
        assertEquals(stats, champion.stats)
    }

    @Test
    fun testFetchChampions_failure() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val champions = ChampionService.fetchChampions()
        assertNotNull("Champions list should not be null", champions)
        assertTrue("Champions list should be empty on failure", champions.isEmpty())
    }

    @Test
    fun testParseChampions_invalidData() = runTest {
        val invalidJsonResponse = """
            [
                {
                    "id": "1",
                    "key": "Aatrox",
                    "name": "Aatrox",
                    "title": "the Darkin Blade",
                    "tags": ["Fighter", "Tank"],
                    "icon": "icon_url",
                    "description": "Some description"
                    // Missing sprite and stats objects
                }
            ]
        """.trimIndent()

        val jsonArray = JSONArray(invalidJsonResponse)
        val champions = ChampionService.parseChampions(jsonArray)
        assertTrue("Champions list should be empty due to invalid data", champions.isEmpty())
    }
}
