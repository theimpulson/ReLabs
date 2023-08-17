package io.aayush.relabs.network

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.aayush.relabs.utils.CommonModule.ACCESS_TOKEN
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideXenforoInterface(okHttpClient: OkHttpClient): XenforoInterface {
        return Retrofit.Builder()
            .baseUrl(XenforoInterface.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(XenforoInterface::class.java)
    }

    @Singleton
    @Provides
    fun provideInterceptor(sharedPreferences: SharedPreferences): Interceptor {
        val accessToken = sharedPreferences.getString(ACCESS_TOKEN, "")
        val cacheControl = CacheControl.Builder()
            .maxAge(10, TimeUnit.DAYS)
            .build()

        return Interceptor { chain ->
            val builder = chain.request().newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .header("authorization", "Bearer $accessToken")
            return@Interceptor chain.proceed(builder.build())
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: Interceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(Cache(File(context.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            .build()
    }
}
