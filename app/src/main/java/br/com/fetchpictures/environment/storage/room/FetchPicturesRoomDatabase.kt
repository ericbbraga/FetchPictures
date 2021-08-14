package br.com.fetchpictures.environment.storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.fetchpictures.environment.storage.MediaImageDAO
import br.com.fetchpictures.environment.storage.room.model.MediaImageLocal

@Database(entities = [MediaImageLocal::class], version = 1, exportSchema = false)
abstract class FetchPicturesRoomDatabase: RoomDatabase() {
    abstract fun mediaImageDAO(): MediaImageDAO
}