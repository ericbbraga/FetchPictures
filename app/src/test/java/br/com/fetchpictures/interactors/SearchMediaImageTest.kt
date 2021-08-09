package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImage
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchMediaImageTest {

    private lateinit var searchProvider: SearchProvider
    private lateinit var searchMediaImage: SearchMediaImage

    private var searchBy: String = "kite"
    private var pageNumber: Int = 1
    private var limitNumber: Int = 10

    @Before
    fun setUp() {
        searchProvider = mockk()
        searchMediaImage = SearchMediaImage(searchProvider)
        setDefaultProviderList(1)
    }

    @Test(expected = Exception::class)
    fun `if searchBy parameter is empty an exception should be thrown`() {
        searchBy = ""
        execute()
    }

    @Test(expected = Exception::class)
    fun `when page is an negative integer an exception should be thrown`() {
        pageNumber = -1
        execute()
    }

    @Test(expected = Exception::class)
    fun `when page is equals to zero an exception should be thrown`() {
        pageNumber = 0
        execute()
    }

    @Test(expected = Exception::class)
    fun `a limitNumber negative should throw an exception`() {
        limitNumber  = -1
        execute()
    }

    @Test(expected = Exception::class)
    fun `a limitNumber equals to 0 should throw an exception`() {
        limitNumber  = 0
        execute()
    }

    @Test(expected = Exception::class)
    fun `over the limitNumber value should throw an exception`() {
        limitNumber = 1000
        execute()
    }

    @Test
    fun `if searchMediaProvider returns emptyList searchProvider should be an empty`() {
        setSearchProviderEmptyList()
        val response = execute()
        Assert.assertTrue(response.isEmpty())
    }

    @Test
    fun `searchMediaImage should return same size of searchMediaProvider`() {
        val size = 10
        setDefaultProviderList(size)

        val response = execute()
        Assert.assertEquals(size, response.size)
    }

    private fun setSearchProviderEmptyList() {
        every { searchProvider.searchBy(any(), any(), any()) }.returns(emptyList())
    }

    private fun setDefaultProviderList(totalQuantity: Int) {
        val medias = mutableListOf<MediaImage>()

        for (x in 0 until totalQuantity) {
            medias.add(mockk())
        }

        every { searchProvider.searchBy(any(), any(), any()) }.returns(medias)
    }

    private fun execute(): List<MediaImage> {
        return searchMediaImage.exec(searchBy, pageNumber, limitNumber)
    }
}