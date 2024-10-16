package com.example.league_of_legends_application.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Buffer
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [28]) // Set an appropriate SDK level for Robolectric
class ImageLoaderUtilsTest {

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
    fun testLoadImageFromUrl_success() = runBlockingTest {
        val imageByteArray = byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A) // PNG header
        val buffer = Buffer().write(imageByteArray) // Use Buffer to wrap binary data
        mockWebServer.enqueue(MockResponse().setBody(buffer).setResponseCode(200))

        val imageUrl = mockWebServer.url("/test-image.png").toString()
        val bitmapMock = mock<Bitmap>()
        Mockito.mockStatic(BitmapFactory::class.java).use { bitmapFactory ->
            bitmapFactory.`when`<Bitmap> { BitmapFactory.decodeStream(Mockito.any()) }.thenReturn(bitmapMock)
        }

        val imageBitmap = loadImageFromUrl(imageUrl)
        assertNotNull(imageBitmap)
    }

    @Test
    fun testLoadImageFromUrl_failure() = runBlockingTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(404)) // Not found response

        val imageUrl = mockWebServer.url("/non-existent-image.png").toString()
        val imageBitmap = loadImageFromUrl(imageUrl)
        assertNull(imageBitmap)
    }
}
