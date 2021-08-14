package br.com.fetchpictures.interactors

import br.com.fetchpictures.model.MediaImageSet

class SaveMediaImage(private val cache: CacheMediaImage) {

   fun exec(mediaImageSet: MediaImageSet) {
      if (mediaImageSet.images.isNotEmpty()) {
         cache.save(mediaImageSet)
      }
   }

}