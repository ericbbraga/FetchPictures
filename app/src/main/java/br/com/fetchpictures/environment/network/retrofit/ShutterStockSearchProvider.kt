package br.com.fetchpictures.environment.network.retrofit

import br.com.fetchpictures.interactors.SearchProvider
import br.com.fetchpictures.model.MediaImage

class ShutterStockSearchProvider(private val stockService: ShutterStockService) :
    BaseRetrofitProvider(),
    SearchProvider {

    override fun searchBy(query: String, page: Int, limit: Int): List<MediaImage> {

        if (query.trim().isEmpty()) {
            throw IllegalArgumentException("query should be non empty")
        }

        if (page <= 0) {
            throw IllegalArgumentException("Page should be positive integer")
        }

        if (limit <= 0 || limit >= 100) {
            throw IllegalArgumentException("Out of range limit: Should be Between 1 and 100")
        }

        val call = stockService.searchBy(query, page, limit)

        return execute(call,
            onSuccess = { imageResponse ->
                imageResponse.images.map {
                    it.parseTo()
                }
            },
            onError = { throw it }
        )
    }
}