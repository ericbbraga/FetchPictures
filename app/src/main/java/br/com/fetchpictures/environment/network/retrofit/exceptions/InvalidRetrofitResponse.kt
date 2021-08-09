package br.com.fetchpictures.environment.network.retrofit.exceptions

import java.lang.Exception

class InvalidRetrofitResponse(error: String, code: Int = 0):
    Exception("An exception occurred on Server $error / $code")