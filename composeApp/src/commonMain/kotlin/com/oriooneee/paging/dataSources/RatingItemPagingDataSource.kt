package com.oriooneee.paging.dataSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oriooneee.paging.dataSources.remote.IRemoteRepository
import com.oriooneee.paging.dataSources.remote.RemoteRepository
import com.oriooneee.paging.domain.RatingEntity

class RatingItemPagingDataSource(
    private val remoteRepository: IRemoteRepository = RemoteRepository(),
    private val preloadedData: List<RatingEntity>,
) : PagingSource<Int, RatingEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RatingEntity> {
        val page = params.key ?: 0

        val data = try {
            if (page == 0 && preloadedData.isNotEmpty()) {
                preloadedData
            } else {
                remoteRepository.getRatings(page + 1)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
        return LoadResult.Page(
            data = data,
            prevKey = if (page == 0) null else page - 1,
            nextKey = if (data.isEmpty()) null else page + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, RatingEntity>): Int? {
        return state.anchorPosition
    }
}
