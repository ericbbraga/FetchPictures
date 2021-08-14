package br.com.fetchpictures.environment.injectors.modules

import android.content.Context
import androidx.room.Room
import br.com.fetchpictures.environment.storage.MediaImageStorage
import br.com.fetchpictures.environment.storage.room.FetchPicturesRoomDatabase
import br.com.fetchpictures.interactors.CacheMediaImage
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun cacheMediaImage(context: Context): CacheMediaImage {
        val database = Room.databaseBuilder(
            context.applicationContext,
            FetchPicturesRoomDatabase::class.java,
            "fetch_pictures_database"
        ).build()

        return MediaImageStorage(database)
    }

}