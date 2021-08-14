package br.com.fetchpictures.model

data class MediaImage(
    val id: String,
    val description: String,
    val lowQualityUrl: String,
    val highQualityUrl: String
)