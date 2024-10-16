package com.example.league_of_legends_application.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.example.league_of_legends_application.model.Champion
import com.example.league_of_legends_application.model.Sprite
import com.example.league_of_legends_application.model.Stats
import com.example.league_of_legends_application.network.ChampionService
import com.example.league_of_legends_application.repositories.ChampionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class ChampionViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: ChampionRepository
    private lateinit var viewModel: ChampionViewModel

    private val testDispatcher = StandardTestDispatcher()

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
        Dispatchers.setMain(testDispatcher)
        repository = mock()
        viewModel = ChampionViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchChampions_fromCache() = runTest {
        val cachedChampions = listOf(testChampion)
        whenever(repository.getAllChampions()).thenReturn(cachedChampions)
        whenever(ChampionService.fetchChampions()).thenReturn(emptyList())

        viewModel.champions.test {
            Assert.assertEquals(emptyList<Champion>(), awaitItem()) // Initial empty list
            Assert.assertEquals(cachedChampions, awaitItem()) // Cached champions

            verify(repository).getAllChampions()
            verifyNoMoreInteractions(repository)
        }

        viewModel.isLoading.test {
            Assert.assertEquals(true, awaitItem()) // Loading starts
            Assert.assertEquals(false, awaitItem()) // Loading ends
        }
    }

    @Test
    fun testFetchChampions_fromNetworkAndCacheUpdate() = runTest {
        val cachedChampions = listOf(testChampion)
        val newChampions = listOf(testChampion.copy(name = "Updated Aatrox"))
        whenever(repository.getAllChampions()).thenReturn(emptyList())
        whenever(ChampionService.fetchChampions()).thenReturn(newChampions)

        viewModel.champions.test {
            Assert.assertEquals(emptyList<Champion>(), awaitItem()) // Initial empty list
            Assert.assertEquals(newChampions, awaitItem()) // Fetched from network

            verify(repository).getAllChampions()
            verify(repository).updateChampions(newChampions)
            verifyNoMoreInteractions(repository)
        }

        viewModel.isLoading.test {
            Assert.assertEquals(true, awaitItem()) // Loading starts
            Assert.assertEquals(false, awaitItem()) // Loading ends
        }
    }

    @Test
    fun testFetchChampions_networkError() = runTest {
        whenever(repository.getAllChampions()).thenReturn(emptyList())
        whenever(ChampionService.fetchChampions()).thenThrow(RuntimeException("Network error"))

        viewModel.champions.test {
            Assert.assertEquals(emptyList<Champion>(), awaitItem()) // Initial empty list
            Assert.assertEquals(emptyList<Champion>(), awaitItem()) // Remains empty on error

            verify(repository).getAllChampions()
            verifyNoMoreInteractions(repository)
        }

        viewModel.isLoading.test {
            Assert.assertEquals(true, awaitItem()) // Loading starts
            Assert.assertEquals(false, awaitItem()) // Loading ends
        }
    }
}
