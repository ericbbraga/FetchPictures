package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImageSet

class RestoreMediaImage(private val cacheMediaImage: CacheMediaImage) {

    fun exec(bucket: String, page: Int): MediaImageSet {
        return cacheMediaImage.restore(bucket, page)
    }

}