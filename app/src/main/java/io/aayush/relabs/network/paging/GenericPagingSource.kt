package io.aayush.relabs.network.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GenericPagingSource<T : Any>(
    private val totalPages: Int? = null,
    private val block: suspend (Int) -> List<T>
) : PagingSource<Int, T>() {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20

        fun <T : Any> createPager(
            totalPages: Int? = null,
            pageSize: Int = DEFAULT_PAGE_SIZE,
            enablePlaceholders: Boolean = false,
            block: suspend (Int) -> List<T>
        ): Pager<Int, T> = Pager(
            config = PagingConfig(enablePlaceholders = enablePlaceholders, pageSize = pageSize),
            pagingSourceFactory = { GenericPagingSource(totalPages, block) }
        )
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1
        return try {
            withContext(Dispatchers.IO) {
                val response = block(page)
                LoadResult.Page(
                    data = response,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (totalPages != null && page == totalPages) null else page + 1
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