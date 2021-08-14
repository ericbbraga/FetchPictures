package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImage
import br.com.fetchpictures.model.MediaImageSet

class SearchMediaImage(
    private val searchProvider: SearchProvider,
    private val saveMediaImage: SaveMediaImage,
    private val restoreMediaImage: RestoreMediaImage
) {
    fun exec(searchBy: String, page: Int, limit: Int = 20): List<MediaImage> {
        if (searchBy.trim().isEmpty()) {
            throw IllegalArgumentException("searchBy should be non empty")
        }
        if (page <= 0) {
            throw IllegalArgumentException("Page should be positive integer")
        }

        if (limit <= 0 || limit >= 100) {
            throw IllegalArgumentException("Out of range limit: Should be Between 1 and 100")
        }

        return loadImages(searchBy, page, limit)
    }

    private fun loadImages(searchBy: String, page: Int, limit: Int): List<MediaImage> {
        val imagesRestored = restoreMediaImage.exec(searchBy, page).images
        return if (imagesRestored.isNotEmpty()) {
            imagesRestored
        } else {
            searchFromProvider(searchBy, page, limit)
        }
    }

    private fun searchFromProvider(
        searchBy: String,
        page: Int,
        limit: Int
    ): List<MediaImage> {
        val images = searchProvider.searchBy(searchBy, page, limit)
        val mediaImageSet = MediaImageSet(searchBy, page, images)

        saveMediaImagesLocal(mediaImageSet)
        return images
    }

    private fun saveMediaImagesLocal(mediaImageSet: MediaImageSet) {
        saveMediaImage.exec(mediaImageSet)
    }
}