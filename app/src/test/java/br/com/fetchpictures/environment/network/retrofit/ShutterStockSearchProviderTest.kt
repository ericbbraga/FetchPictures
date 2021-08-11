package br.com.fetchpictures.environment.network.retrofit

import br.com.fetchpictures.environment.network.retrofit.model.ShutterMediaImage
import br.com.fetchpictures.environment.network.retrofit.model.ShutterResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class ShutterStockSearchProviderTest {
    private lateinit var response: Response<ShutterResponse>
    private lateinit var call: Call<ShutterResponse>
    private lateinit var stockService: ShutterStockService

    private lateinit var shutterProvider: ShutterStockSearchProvider

    private var query: String = "kite"
    private var pageNumber: Int = 1
    private var limitNumber: Int = 10

    @Before
    fun setUp() {
        response = mockk()
        call = mockk()
        stockService = mockk()

        every { call.execute() }.returns(response)
        every { stockService.searchBy(any(), any(), any()) }.returns(call)

        shutterProvider = ShutterStockSearchProvider(stockService)
    }

    @Test(expected = Exception::class)
    fun `when query is empty an exception should be thrown`() {
        query = ""
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `when page is a negative number an exception should be thrown`() {
        pageNumber = -1
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `when page is equals to zero an exception should be thrown`() {
        pageNumber = 0
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `when limitNumber is negative exception should be thrown`() {
        limitNumber = -1
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `when limitNumber is over the limit exception should be thrown`() {
        limitNumber = 1000
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `if searchBy returns a call which is isSuccessful is false an error should be thrown`() {
        setSuccessfulResponse(false)
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `if call throw an exception an error should be thrown`() {
        doThrowAsCallExecute()
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test(expected = Exception::class)
    fun `when isSuccessful is true and body is null an error should be thrown`() {
        setSuccessfulResponse(true)
        setNoneResponse()
        shutterProvider.searchBy(query, pageNumber, limitNumber)
    }

    @Test
    fun `when isSuccessful is true and body is empty return should be empty`() {
        setSuccessfulResponse(true)
        setResponseBody(emptyList())

        val searchByResponse = shutterProvider.searchBy(query, pageNumber, limitNumber)
        Assert.assertTrue(searchByResponse.isEmpty())
    }

    @Test
    fun `when isSuccessful is true and body has elements return should not be empty`() {
        setSuccessfulResponse(true)
        setResponseBody(
            listOf(
                ShutterMediaImage("url", "desc", mapOf())
            )
        )

        val searchByResponse = shutterProvider.searchBy(query, pageNumber, limitNumber)
        Assert.assertTrue(searchByResponse.isNotEmpty())
    }

    @Test
    fun `when isSuccessful is true and body has elements return should has same size of elements`() {
        setSuccessfulResponse(true)
        val totalItems = 5

        val items = mutableListOf<ShutterMediaImage>()
        for (x in 0 until totalItems) {
            items.add(ShutterMediaImage("url", "desc", mapOf()))
        }

        setResponseBody(items)

        val searchByResponse = shutterProvider.searchBy(query, pageNumber, limitNumber)
        Assert.assertEquals(totalItems, searchByResponse.size)
    }

    private fun setSuccessfulResponse(value: Boolean) {
        every { response.isSuccessful }.returns(value)
    }

    private fun doThrowAsCallExecute() {
        every { call.execute() }.throws(Exception("Exception on Call execute"))
    }

    private fun setNoneResponse() {
        every { response.body() }.returns(null)
    }

    private fun setResponseBody(mediaItems: List<ShutterMediaImage>) {
        val imagesResponse = ShutterResponse(1, mediaItems)
        every { response.body() }.returns(imagesResponse)
    }

}