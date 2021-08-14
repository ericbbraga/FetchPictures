package br.com.fetchpictures.environment.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.fetchpictures.environment.storage.room.model.MediaImageLocal

@Dao
interface MediaImageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg mediaImage: MediaImageLocal)

    @Query("DELETE FROM media_images WHERE page = :page")
    fun delete(page: Int)

    @Query("SELECT * FROM media_images WHERE bucket = :bucket AND page = :page")
    fun list(bucket: String, page: Int): List<MediaImageLocal>
}