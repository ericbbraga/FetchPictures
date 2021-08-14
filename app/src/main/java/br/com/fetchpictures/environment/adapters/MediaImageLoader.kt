package br.com.fetchpictures.environment.adapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import java.net.URI

interface MediaImageLoader {
    fun loadInto(uri: Uri, imageView: ImageView)
}