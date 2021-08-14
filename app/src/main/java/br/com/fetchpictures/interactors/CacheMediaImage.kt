package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImageSet

interface CacheMediaImage {
    fun save(mediaImageSet: MediaImageSet)
    fun restore(bucket: String, page: Int): MediaImageSet
}
