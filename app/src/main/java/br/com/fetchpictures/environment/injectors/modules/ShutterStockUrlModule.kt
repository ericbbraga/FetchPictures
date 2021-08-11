package br.com.fetchpictures.environment.injectors.modules

import dagger.Module
import dagger.Provides
import java.net.URL

@Module
class ShutterStockUrlModule {
    @Provides
    fun serverUrl() = URL("https://api.shutterstock.com/")
}