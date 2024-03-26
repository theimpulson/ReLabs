package io.aayush.relabs.network.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenericPagingSource<T : Any>(
    private val block: suspend (Int) -> Map<Int?, List<T>>
) : PagingSource<Int, T>() {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20

        fun <T : Any> createPager(
            pageSize: Int = DEFAULT_PAGE_SIZE,
            enablePlaceholders: Boolean = false,
            block: suspend (Int) -> Map<Int?, List<T>> // Total pages & List of data
        ): Pager<Int, T> = Pager(
            config = PagingConfig(enablePlaceholders = enablePlaceholders, pageSize = pageSize),
            pagingSourceFactory = { GenericPagingSource(block) }
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            withContext(Dispatchers.IO) {
                val response = block(page)
                val totalPages = response.keys.firstOrNull() ?: 1
                LoadResult.Page(
                    data = response.values.first(),
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (page == totalPages) null else page + 1
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
