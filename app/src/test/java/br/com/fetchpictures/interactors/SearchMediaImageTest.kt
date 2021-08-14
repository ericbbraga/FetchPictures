package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImage
import br.com.fetchpictures.model.MediaImageSet
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchMediaImageTest {
    private lateinit var searchProvider: SearchProvider
    private lateinit var saveMediaImage: SaveMediaImage
    private lateinit var restoreMediaImage: RestoreMediaImage
    private lateinit var searchMediaImage: SearchMediaImage

    private var searchBy: String = "kite"
    private var pageNumber: Int = 1
    private var limitNumber: Int = 10

    @Before
    fun setUp() {
        searchProvider = mockk()
        saveMediaImage = mockk(relaxed = true)
        restoreMediaImage = mockk()


        searchMediaImage = SearchMediaImage(searchProvider, saveMediaImage, restoreMediaImage)
        setDefaultProviderList(1)
        setDefaultRestoreMediaImage(1)
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
    fun `when restoredMediaImage returns nonEmpty list searchProvider should not be called`() {
        val size = 10
        setDefaultRestoreMediaImage(size)
        execute()

        verify(exactly = 0) { searchProvider.searchBy(any(), any(), any()) }
    }

    @Test
    fun `when restoredMediaImage returns nonEmpty list response should be nonEmpty`() {
        val size = 10
        setDefaultRestoreMediaImage(size)
        val response = execute()

        Assert.assertTrue(response.isNotEmpty())
    }

    @Test
    fun `when restoredMediaImage returns nonEmpty list response have same size`() {
        val size = 10
        setDefaultRestoreMediaImage(size)
        val response = execute()

        Assert.assertEquals(size, response.size)
    }

    @Test
    fun `if restoreMediaImage and searchMediaProvider returns emptyList searchProvider should be an empty`() {
        setRestoreMediaImageEmptyList()
        setSearchProviderEmptyList()
        val response = execute()
        Assert.assertTrue(response.isEmpty())
    }

    @Test
    fun `if restoreMediaImage returns emptyList searchMediaImage should return same size of searchMediaProvider`() {
        val size = 10
        setRestoreMediaImageEmptyList()
        setDefaultProviderList(size)

        val response = execute()
        Assert.assertEquals(size, response.size)
    }

    @Test
    fun `if restoreMediaImage returns emptyList searchProvider should be called`() {
        setRestoreMediaImageEmptyList()
        setSearchProviderEmptyList()

        execute()
        verify(exactly = 1) { searchProvider.searchBy(any(), any(), any()) }
    }

    @Test
    fun `if restoreMediaImage returns emptyList saveMedia should be called`() {
        setRestoreMediaImageEmptyList()
        setSearchProviderEmptyList()

        execute()
        verify(exactly = 1) { saveMediaImage.exec(any()) }
    }

    private fun setDefaultRestoreMediaImage(totalQuantity: Int) {

        val medias = mutableListOf<MediaImage>()

        for (x in 0 until totalQuantity) {
            medias.add(mockk())
        }

        val mediaImageSet = MediaImageSet("some", 1, medias)
        every { restoreMediaImage.exec(any(), any()) }.returns(mediaImageSet)
    }

    private fun setRestoreMediaImageEmptyList() {
        val emptyMediaImageSet = MediaImageSet("some", 1, emptyList())
        every { restoreMediaImage.exec(any(), any()) }.returns(emptyMediaImageSet)
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