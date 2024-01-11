package io.aayush.relabs.network

import io.aayush.relabs.network.data.Success
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

// TODO: Migrate all API endpoints from XenforoInterface class
interface XDAInterface {

    companion object {
        const val BASE_URL = "https://xdaforums.com/api/"
    }

    @POST("audapp-push-subscriptions")
    suspend fun postExpoPushToken(@Body body: RequestBody): Response<Success>
}
