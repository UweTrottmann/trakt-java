package com.redissi.trakt

import com.redissi.trakt.entities.TraktError
import retrofit2.Response
import kotlin.test.fail

interface TestResponse {

    fun assertSuccessfulResponse(response: Response<*>, trakt: Trakt) {
        if (!response.isSuccessful) {
            handleFailedResponse(response, trakt)
        }
    }

    private fun handleFailedResponse(response: Response<*>, trakt: Trakt) {
        if (response.code() == 401) {
            fail(
                "Authorization required, supply a valid OAuth access token: "
                    + response.code() + " " + response.message()
            )
        } else {
            var message = "Request failed: " + response.code() + " " + response.message()
            val error: TraktError? = trakt.checkForTraktError(response)
            if (error?.message != null) {
                message += " message: " + error.message
            }
            fail(message)
        }
    }

}