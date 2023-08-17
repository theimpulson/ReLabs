package io.aayush.relabs.rss

import android.content.Context
import com.prof.rssparser.Parser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.nio.charset.Charset
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    @Singleton
    @Provides
    fun provideParserInstance(@ApplicationContext context: Context): Parser {
        return Parser.Builder()
            .context(context)
            .charset(Charset.forName("ISO-8859-7"))
            .cacheExpirationMillis(1L * 60L * 60L * 1000L)
            .build()
    }
}
