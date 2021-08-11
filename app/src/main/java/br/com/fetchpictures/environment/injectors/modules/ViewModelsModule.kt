package br.com.fetchpictures.environment.injectors.modules

import br.com.fetchpictures.interactors.SearchMediaImage
import br.com.fetchpictures.viewmodels.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun searchViewModel(searchMediaImage: SearchMediaImage): SearchViewModel {
        return SearchViewModel(searchMediaImage)
    }

}