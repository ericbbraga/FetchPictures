package br.com.fetchpictures.model

data class MediaImageSet(
    val bucket: String,
    val page: Int,
    val images: List<MediaImage>
)