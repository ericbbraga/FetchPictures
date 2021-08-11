package br.com.fetchpictures.environment.injectors.modules

import br.com.fetchpictures.environment.network.retrofit.ShutterStockSearchProvider
import br.com.fetchpictures.environment.network.retrofit.ShutterStockService
import br.com.fetchpictures.interactors.SearchProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ProvidersModule {

    @Provides
    fun searchProvider(retrofit: Retrofit): SearchProvider {
        val stockService = retrofit.create(ShutterStockService::class.java)
        return ShutterStockSearchProvider(stockService)
    }

}