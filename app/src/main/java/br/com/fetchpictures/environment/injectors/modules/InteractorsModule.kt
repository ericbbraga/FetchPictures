package br.com.fetchpictures.environment.injectors.modules

import br.com.fetchpictures.interactors.SearchMediaImage
import br.com.fetchpictures.interactors.SearchProvider
import dagger.Module
import dagger.Provides

@Module
class InteractorsModule {

    @Provides
    fun searchMediaInteractor(provider: SearchProvider): SearchMediaImage {
        return SearchMediaImage(provider)
    }

}