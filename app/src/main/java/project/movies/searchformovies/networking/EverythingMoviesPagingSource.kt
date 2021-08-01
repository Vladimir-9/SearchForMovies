package project.movies.searchformovies.networking

import androidx.paging.PagingSource
import androidx.paging.PagingState
import project.movies.searchformovies.remote.MoviesData

class EverythingMoviesPagingSource(
    private val networkingApi: NetworkingApi
) : PagingSource<Int, MoviesData>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesData>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesData> {
        return try {
            val pageNumber = params.key ?: 1
            // val pageSize = params.loadSize.coerceAtMost(NetworkingApi.MAX_PAGE_SIZE)
            val response = networkingApi.popularMovies(page = pageNumber)

            val nextPageNumber = if (response.results.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(response.results, prevPageNumber, nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


}