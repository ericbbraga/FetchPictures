package br.com.fetchpictures.interactors;

import br.com.fetchpictures.model.MediaImage

interface SearchProvider {
    fun searchBy(query: String, page: Int = 1, limit: Int = 20): List<MediaImage>
}
