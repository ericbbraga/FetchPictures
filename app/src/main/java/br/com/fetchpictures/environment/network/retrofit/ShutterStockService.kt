package br.com.fetchpictures.environment.network.retrofit

import br.com.fetchpictures.environment.network.retrofit.model.ShutterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShutterStockService {
    @GET("v2/images/search")
    fun searchBy(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<ShutterResponse>
}