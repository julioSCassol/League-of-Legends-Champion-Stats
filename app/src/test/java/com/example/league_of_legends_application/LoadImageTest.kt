package com.example.league_of_legends_application

import androidx.compose.ui.graphics.ImageBitmap
import com.example.league_of_legends_application.utils.loadImageFromUrl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
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
class LoadImageFromUrlTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testLoadImageFromUrl_invalidUrl() = runTest {
        val imageUrl = "http://invalid_url"
        val result: ImageBitmap? = loadImageFromUrl(imageUrl)

        assertNull("ImageBitmap should be null for an invalid URL", result)
    }

    @Test
    fun testLoadImageFromUrl_serverError() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))

        val imageUrl = mockWebServer.url("/error_image.jpeg").toString()
        val result: ImageBitmap? = loadImageFromUrl(imageUrl)

        assertNull("ImageBitmap should be null for server error", result)
    }
}
