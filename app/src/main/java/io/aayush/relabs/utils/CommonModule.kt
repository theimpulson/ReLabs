package io.aayush.relabs.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.browser.customtabs.CustomTabsIntent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    const val ACCESS_TOKEN = "accessToken"

    /**
     * Provides an instance of SharedPreferences
     */
    @Singleton
    @Provides
    fun provideSharedPrefInstance(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun providesCustomTabIntent(): CustomTabsIntent {
        return CustomTabsIntent.Builder().build()
    }

    @Singleton
    @Provides
    fun provideDesignQuoteSpan(): DesignQuoteSpan {
        return DesignQuoteSpan()
    }
}
