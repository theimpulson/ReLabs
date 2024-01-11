package io.aayush.relabs.network

import io.aayush.relabs.network.data.expo.ExpoData
import io.aayush.relabs.network.data.expo.PostData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ExpoInterface {

    companion object {
        const val BASE_URL = "https://exp.host/--/api/v2/"
    }

    @POST("push/getExpoPushToken")
    suspend fun getExpoPushToken(@Body expoData: ExpoData): Response<PostData>
}
