package io.aayush.relabs.network

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.aayush.relabs.utils.CommonModule.ACCESS_TOKEN
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideXenforoInterface(@Named("OkHttpClient") okHttpClient: OkHttpClient): XenforoInterface {
        return Retrofit.Builder()
            .baseUrl(XenforoInterface.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(XenforoInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideExpoInterface(): ExpoInterface {
        return Retrofit.Builder()
            .baseUrl(ExpoInterface.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ExpoInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideXDAInterface(@Named("OkHttpClientForXDA") okHttpClient: OkHttpClient): XDAInterface {
        return Retrofit.Builder()
            .baseUrl(XDAInterface.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(XDAInterface::class.java)
    }

    @Named("OkHttpClientForXDA")
    @Singleton
    @Provides
    fun provideOkHttpClientForXDA(sharedPreferences: SharedPreferences): OkHttpClient {
        val interceptor = GetInterceptor(sharedPreferences, true)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Named("OkHttpClient")
    @Singleton
    @Provides
    fun provideOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        val interceptor = GetInterceptor(sharedPreferences)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private class GetInterceptor(
        private val sharedPreferences: SharedPreferences,
        private val isAudApp: Boolean = false
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "")
            val builder = chain.request().newBuilder()

            builder.header("authorization", "Bearer $accessToken")
            if (isAudApp) {
                builder.header(
                    "User-Agent",
                    "${System.getProperty("http.agent")} AudCommunityApp/android/0.15.41"
                )
            }
            return chain.proceed(builder.build())
        }
    }
}
