package br.com.fetchpictures.environment.loaders

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import br.com.fetchpictures.environment.adapters.MediaImageLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideImageLoader(context: Context): MediaImageLoader {

    private val requestManager: RequestManager = Glide.with(context.applicationContext)

    override fun loadInto(uri: Uri, imageView: ImageView) {
        requestManager.load(uri).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(imageView)
    }
}