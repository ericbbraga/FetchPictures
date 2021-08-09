package br.com.fetchpictures.environment.network.retrofit

import br.com.fetchpictures.interactors.SearchProvider
import br.com.fetchpictures.model.MediaImage
import retrofit2.Retrofit

class ShutterStockSearchProvider(private val retrofit: Retrofit) :
    BaseRetrofitProvider(), SearchProvider {

    override fun searchBy(query: String, page: Int, limit: Int): List<MediaImage> {
        val stockService = retrofit.create(ShutterStockService::class.java)
        val call = stockService.searchBy(query, page, limit)

        return execute(call,
            onSuccess = { medias ->
                medias.map {
                    it.parseTo()
                }
            },
            onError = { throw it }
        )
    }
}