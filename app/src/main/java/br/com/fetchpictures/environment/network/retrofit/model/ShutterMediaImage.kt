package br.com.fetchpictures.environment.network.retrofit.model

import br.com.fetchpictures.model.MediaImage
import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class ShutterResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("data")
    val images: List<ShutterMediaImage>
)

data class ShutterMediaImage(
    @SerializedName("id")
    val id: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("assets")
    val assets: Map<String, Assets>

) {
    fun parseTo(): MediaImage {
        val defaultAsset = Assets("")
        val lowQuality = assets["large_thumb"] ?: defaultAsset
        val highQuality = assets["huge_thumb"] ?: defaultAsset

        return MediaImage(
            description,
            lowQuality.url,
            highQuality.url
        )
    }
}

data class Assets (
    @Field("url")
    val url: String
)