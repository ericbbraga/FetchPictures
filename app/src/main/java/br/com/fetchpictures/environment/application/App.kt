package br.com.fetchpictures.environment.application

import android.app.Application
import br.com.fetchpictures.environment.activities.MainActivity
import br.com.fetchpictures.environment.injectors.components.DaggerMainComponent
import br.com.fetchpictures.environment.injectors.components.MainComponent
import br.com.fetchpictures.environment.injectors.modules.ContextModule

class App : Application() {
    private lateinit var mainDaggerComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        val context = applicationContext
        mainDaggerComponent =
            DaggerMainComponent.builder()
                .contextModule(ContextModule(context))
                .build()
    }

    fun inject(activity: MainActivity) {
        mainDaggerComponent.inject(activity)
    }
}