package br.com.fetchpictures.environment.injectors.modules

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

@Module
class RetrofitModule {

    @Provides
    fun createRetrofit(okHttpClient: OkHttpClient, url: URL): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}