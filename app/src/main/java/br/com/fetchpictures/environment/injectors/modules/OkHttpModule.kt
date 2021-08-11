package br.com.fetchpictures.environment.injectors.modules

import br.com.fetchpictures.BuildConfig
import br.com.fetchpictures.environment.constants.Constants
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@Module
class OkHttpModule {

    @Provides
    fun createOkHttp(): OkHttpClient {
        val headerInterceptor = createHeaderInterceptor()
        return OkHttpClient.Builder().addInterceptor(headerInterceptor).build()
    }

    private fun createHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(Constants.AUTHORIZATION_HEADER, "${Constants.BEARER} ${BuildConfig.API_TOKEN}")
                .addHeader(Constants.USER_AGENT, BuildConfig.APPLICATION_ID)
                .build()

            chain.proceed(request)
        }
    }

}