package br.com.fetchpictures.environment.application

import android.app.Application
import br.com.fetchpictures.environment.activities.MainActivity
import br.com.fetchpictures.environment.injectors.components.DaggerMainComponent
import br.com.fetchpictures.environment.injectors.components.MainComponent

class App: Application() {
    private lateinit var mainDaggerComponent: MainComponent

    override fun onCreate() {
        super.onCreate()
        mainDaggerComponent = DaggerMainComponent.create()
    }

    fun inject(activity: MainActivity) {
        mainDaggerComponent.inject(activity)
    }
}