package br.com.fetchpictures.environment.storage.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.fetchpictures.model.MediaImage

@Entity(tableName = "media_images")
class MediaImageLocal {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: String

    @ColumnInfo(name = "bucket")
    val bucket: String

    @ColumnInfo(name = "page")
    val page: Int

    @ColumnInfo(name = "description")
    val description: String

    @ColumnInfo(name = "low_quality_url")
    val lowQualityUrl: String

    @ColumnInfo(name = "high_quality_url")
    val highQualityUrl: String

    constructor(
        id: String,
        bucket: String,
        page: Int,
        description: String,
        lowQualityUrl: String,
        highQualityUrl: String
    ) {
        this.id = id
        this.bucket = bucket
        this.page = page
        this.description = description
        this.lowQualityUrl = lowQualityUrl
        this.highQualityUrl = highQualityUrl
    }

    constructor(mediaImage: MediaImage, bucket: String, page: Int) {
        this.id = mediaImage.id
        this.bucket = bucket
        this.page = page

        this.description = mediaImage.description
        this.lowQualityUrl = mediaImage.lowQualityUrl
        this.highQualityUrl = mediaImage.highQualityUrl
    }

    fun parseTo(): MediaImage {
        return MediaImage(id, description, lowQualityUrl, highQualityUrl)
    }
}
