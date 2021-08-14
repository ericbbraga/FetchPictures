package br.com.fetchpictures.environment.injectors.modules

import br.com.fetchpictures.interactors.*
import dagger.Module
import dagger.Provides

@Module
class InteractorsModule {

    @Provides
    fun searchMediaInteractor(
        provider: SearchProvider,
        saveMediaImage: SaveMediaImage,
        restoreMediaImage: RestoreMediaImage
    ): SearchMediaImage {
        return SearchMediaImage(provider, saveMediaImage, restoreMediaImage)
    }

    @Provides
    fun saveMediaInteractor(cacheMediaImage: CacheMediaImage): SaveMediaImage {
        return SaveMediaImage(cacheMediaImage)
    }

    @Provides
    fun restoreMediaInteractor(cacheMediaImage: CacheMediaImage): RestoreMediaImage {
        return RestoreMediaImage(cacheMediaImage)
    }

}