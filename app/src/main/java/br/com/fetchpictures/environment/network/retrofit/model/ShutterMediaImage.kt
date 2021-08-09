package br.com.fetchpictures.environment.network.retrofit.model

import br.com.fetchpictures.model.MediaImage
import retrofit2.http.Field

data class ShutterMediaImage(
    @Field("assets/preview_1000/url")
    val url: String,
    @Field("assets/preview_1000/width")
    val width: Int,
    @Field("assets/preview_1000/height")
    val height: Int,
    @Field("description")
    val description: String
) {
    fun parseTo(): MediaImage {
        return MediaImage(
            description,
            width,
            height,
            url
        )
    }

}