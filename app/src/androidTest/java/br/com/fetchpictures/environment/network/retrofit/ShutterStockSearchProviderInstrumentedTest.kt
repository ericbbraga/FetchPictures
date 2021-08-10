package br.com.fetchpictures.environment.network.retrofit

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import br.com.fetchpictures.AssetsHelper
import br.com.fetchpictures.model.MediaImage
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShutterStockSearchProviderInstrumentedTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var mockUrl: HttpUrl
    private lateinit var shutterStockSearchProvider: ShutterStockSearchProvider

    private lateinit var context: Context

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start(5000)

        mockUrl = mockWebServer.url("/shutter_stock_mock/")

        val retrofit = Retrofit.Builder()
            .baseUrl(mockUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        shutterStockSearchProvider = ShutterStockSearchProvider(retrofit)
        context = InstrumentationRegistry.getInstrumentation().context
    }

    @Test(expected = Exception::class)
    fun whenAnErrorOccursOnServerConnectionAnExceptionShouldOccurs() {
        mockWebServer.enqueue(MockResponse().setResponseCode(500))
        searchByExecute()
    }

    @Test
    fun whenServerResponseIsSuccessWithItemsResponseShouldBeANonEmptyList() {
        val successContent = AssetsHelper.readContent(context, "shutter.response.json")
        mockWebServer.enqueue(MockResponse().setBody( successContent ))

        val searchByResponse = searchByExecute()
        Assert.assertTrue(searchByResponse.isNotEmpty())
    }

    private fun searchByExecute(): List<MediaImage> {
        return shutterStockSearchProvider.searchBy("kite", 1, 10)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}