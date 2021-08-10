package br.com.fetchpictures.environment.network.retrofit

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class BaseRetrofitProviderTest {

    private lateinit var call: Call<Any>
    private lateinit var response: Response<Any>
    private lateinit var inheritableClass: BaseRetrofitProvider

    @Before
    fun setUp() {
        call = mockk()
        response = mockk()

        inheritableClass = object : BaseRetrofitProvider() {}
    }

    @Test
    fun `if execute method from call object throws an exception onError should be called`() {
        throwAnExceptionOnCallExecute()

        inheritableClass.execute(
            call,
            onSuccess = {
                Assert.fail("Should not call onSuccess")
            },

            onError = {}
        )
    }

    @Test
    fun `when method isSuccessful return false onError should be called`() {
        setSuccessfulResponse(false)

        inheritableClass.execute(
            call,
            onSuccess = {
                Assert.fail("Should not call onSuccess")
            },

            onError = {}
        )
    }

    @Test
    fun `when method isSuccessful return true and body is null an onError should be called`() {
        setSuccessfulResponse(true)
        setNullableResponseBody()

        inheritableClass.execute(
            call,
            onSuccess = {
                Assert.fail("Should not call onSuccess")
            },

            onError = {}
        )
    }

    @Test
    fun `when method isSuccessful return true and body is not null an onSuccess should be called`() {
        setSuccessfulResponse(true)
        setResponseBody()

        inheritableClass.execute(
            call,
            onSuccess = {},

            onError = {
                Assert.fail("onError should not be called")
            }
        )
    }

    private fun throwAnExceptionOnCallExecute() {
        every { call.execute() }.throws(Exception("An error occurred on call"))
    }

    private fun setSuccessfulResponse(value: Boolean) {
        every { response.isSuccessful }.returns(value)
        every { call.execute() }.returns(response)
    }

    private fun setNullableResponseBody() {
        every { response.body() }.returns(null)
    }

    private fun setResponseBody() {
        every { response.body() }.returns(object : Any(){ })
    }

}