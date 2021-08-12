package br.com.fetchpictures.environment.injectors.modules

import android.content.Context
import br.com.fetchpictures.environment.adapters.MediaImageLoader
import br.com.fetchpictures.environment.loaders.GlideImageLoader
import dagger.Module
import dagger.Provides

@Module
class ImageLoaderModule {

    @Provides
    fun createImageLoader(context: Context): MediaImageLoader {
        return GlideImageLoader(context)
    }

}