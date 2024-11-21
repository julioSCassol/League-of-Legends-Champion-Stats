package com.example.league_of_legends_application

import com.example.league_of_legends_application.model.Price
import com.example.league_of_legends_application.network.ItemService
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
@Config(sdk = [29], manifest = Config.NONE)
class ItemServiceTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        ItemService.baseUrl = mockWebServer.url("/").toString()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testFetchItems_success() = runTest {
        val jsonResponse = """
            [
                {
                    "name": "Health Potion",
                    "description": "Restores 150 health over 15 seconds.",
                    "price": {
                        "base": 50,
                        "total": 50,
                        "sell": 20
                    },
                    "purchasable": true,
                    "icon": "health_potion_icon_url"
                },
                {
                    "name": "Mana Potion",
                    "description": "Restores 100 mana over 15 seconds.",
                    "price": {
                        "base": 40,
                        "total": 40,
                        "sell": 15
                    },
                    "purchasable": true,
                    "icon": "mana_potion_icon_url"
                }
            ]
        """.trimIndent()

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonResponse))

        val items = ItemService.fetchItems(size = 2, page = 1)
        assertNotNull("Items list should not be null", items)
        assertEquals("Expected 2 items", 2, items.size)

        val firstItem = items[0]
        assertEquals("Health Potion", firstItem.name)
        assertEquals("Restores 150 health over 15 seconds.", firstItem.description)
        assertEquals(Price(base = 50, total = 50, sell = 20), firstItem.price)
        assertTrue(firstItem.purchasable)
        assertEquals("health_potion_icon_url", firstItem.icon)

        val secondItem = items[1]
        assertEquals("Mana Potion", secondItem.name)
        assertEquals("Restores 100 mana over 15 seconds.", secondItem.description)
        assertEquals(Price(base = 40, total = 40, sell = 15), secondItem.price)
        assertTrue(secondItem.purchasable)
        assertEquals("mana_potion_icon_url", secondItem.icon)
    }

    @Test
    fun testFetchItems_failure() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        val items = ItemService.fetchItems()
        assertNotNull("Items list should not be null", items)
        assertTrue("Items list should be empty on failure", items.isEmpty())
    }

    @Test
    fun testParseItems_invalidData() = runTest {
        val invalidJsonResponse = """
            [
                {
                    "name": "Invalid Item",
                    "description": "Invalid description"
                    // Missing "price", "purchasable", and "icon"
                }
            ]
        """.trimIndent()

        val jsonArray = JSONArray(invalidJsonResponse)
        val items = ItemService.parseItems(jsonArray)

        assertNotNull("Parsed items list should not be null", items)
        assertTrue("Parsed items list should be empty due to invalid data", items.isEmpty())
    }
}
