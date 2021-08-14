package br.com.fetchpictures.environment.storage

import br.com.fetchpictures.environment.storage.room.FetchPicturesRoomDatabase
import br.com.fetchpictures.environment.storage.room.model.MediaImageLocal
import br.com.fetchpictures.interactors.CacheMediaImage
import br.com.fetchpictures.model.MediaImage
import br.com.fetchpictures.model.MediaImageSet

class MediaImageStorage(private val database: FetchPicturesRoomDatabase) : CacheMediaImage {
    override fun save(mediaImageSet: MediaImageSet) {
        val mediaImagesLocal = with(mediaImageSet) {
            images.map {
                MediaImageLocal(it, bucket, page)
            }
        }

        database.mediaImageDAO().insert(*mediaImagesLocal.toTypedArray())
    }

    override fun restore(bucket: String, page: Int): MediaImageSet {
        val mediaImagesLocal = database.mediaImageDAO().list(bucket, page)
        val images = mediaImagesLocal.map {
            it.parseTo()
        }

        return MediaImageSet(bucket, page, images)
    }

}