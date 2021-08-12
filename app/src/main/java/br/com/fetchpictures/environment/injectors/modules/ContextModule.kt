package br.com.fetchpictures.environment.injectors.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ContextModule(private val context: Context) {

    @Provides
    fun applicationContext() : Context {
        return context.applicationContext
    }

}