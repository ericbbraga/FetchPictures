package br.com.fetchpictures.environment.network.retrofit

import br.com.fetchpictures.environment.network.retrofit.exceptions.InvalidRetrofitResponse
import retrofit2.Call

abstract class BaseRetrofitProvider {

    fun <T, R> execute(
        call: Call<T>,
        onSuccess: (response: T) -> R,
        onError: (error: Throwable) -> R
    ): R {
        return try {
            val response = call.execute()
            if (response.isSuccessful) {
                val responseBody = response.body()

                responseBody?.let {
                    onSuccess.invoke(it)
                } ?: onError(InvalidRetrofitResponse("body is null"))
            } else {
                onError(InvalidRetrofitResponse("Server error", response.code()))
            }
        } catch (ex: Exception) {
            onError(InvalidRetrofitResponse("error =${ex.javaClass} ${ex.message ?: ""}"))
        }
    }

}