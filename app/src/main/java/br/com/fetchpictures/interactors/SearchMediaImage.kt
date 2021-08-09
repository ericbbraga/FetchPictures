package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImage
import java.lang.IllegalArgumentException

class SearchMediaImage(private val searchProvider: SearchProvider) {
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

        return searchProvider.searchBy(searchBy, page, limit)
    }
}