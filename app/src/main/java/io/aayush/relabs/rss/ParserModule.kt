package io.aayush.relabs.rss

import com.prof18.rssparser.RssParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ParserModule {

    @Singleton
    @Provides
    fun provideParserInstance(): RssParser {
        return RssParser()
    }
}
