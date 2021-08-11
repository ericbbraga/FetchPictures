package br.com.fetchpictures.environment.injectors.components

import br.com.fetchpictures.environment.activities.MainActivity
import br.com.fetchpictures.environment.injectors.modules.*
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    InteractorsModule::class,
    OkHttpModule::class,
    ProvidersModule::class,
    RetrofitModule::class,
    ShutterStockUrlModule::class,
    ViewModelsModule::class,
])
@Singleton
interface MainComponent {
    fun inject(activity: MainActivity)
}