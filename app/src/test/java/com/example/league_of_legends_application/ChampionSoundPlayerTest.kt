package com.example.league_of_legends_application

import android.content.Context
import android.media.MediaPlayer
import com.example.league_of_legends_application.utils.ChampionSounds
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], manifest = Config.NONE)
class ChampionSoundsTest {

    private val mockContext: Context = mock()
    private val mockMediaPlayer: MediaPlayer = mock()

    @Before
    fun setUp() {
        // Configura a factory para retornar um MediaPlayer simulado
        ChampionSounds.mediaPlayerFactory = { _, _ -> mockMediaPlayer }
    }

    @Test
    fun testGetSoundResId_validChampion() {
        val soundId = ChampionSounds.getSoundResId("Aatrox")
        assertEquals("Sound ID should match R.raw.aatrox", R.raw.aatrox, soundId)
    }

    @Test
    fun testGetSoundResId_invalidChampion() {
        val soundId = ChampionSounds.getSoundResId("InvalidChampion")
        assertEquals("Sound ID for invalid champion should be 0", 0, soundId)
    }

    @Test
    fun testGetSoundResId_caseInsensitive() {
        val soundIdLowercase = ChampionSounds.getSoundResId("aatrox")
        val soundIdUppercase = ChampionSounds.getSoundResId("AATROX")
        assertEquals("Sound IDs should be the same for case-insensitive match", soundIdLowercase, soundIdUppercase)
    }

    @Test
    fun testPlayChampionSound_validChampion() {
        val result = ChampionSounds.playChampionSound(mockContext, "Aatrox")
        assertNotNull("MediaPlayer should not be null for a valid champion", result)

        verify(mockMediaPlayer).start()
    }

    @Test
    fun testPlayChampionSound_invalidChampion() {
        val result =
            ChampionSounds.playChampionSound(mockContext, "InvalidChampion")
        assertNull("MediaPlayer should be null for an invalid champion", result)

        verify(mockMediaPlayer, never()).start()
    }
}
