package com.redissi.trakt

import com.redissi.trakt.entities.TraktError
import org.assertj.core.api.Assertions
import retrofit2.Response

interface TestResponse {

    fun assertSuccessfulResponse(response: Response<*>, trakt: Trakt) {
        if (!response.isSuccessful) {
            handleFailedResponse(response, trakt)
        }
    }

    private fun handleFailedResponse(response: Response<*>, trakt: Trakt) {
        if (response.code() == 401) {
            Assertions.fail<Any>(
                "Authorization required, supply a valid OAuth access token: "
                        + response.code() + " " + response.message()
            )
        } else {
            var message = "Request failed: " + response.code() + " " + response.message()
            val error: TraktError? = trakt.checkForTraktError(response)
            if (error?.message != null) {
                message += " message: " + error.message
            }
            Assertions.fail<Any>(message)
        }
    }

}