package com.oriooneee.paging.presentation.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.oriooneee.paging.dataSources.RatingItemPagingDataSource
import com.oriooneee.paging.dataSources.remote.RemoteRepository

class RatingViewModel : ViewModel() {
    val ratingPager = Pager(
        PagingConfig(
            pageSize = RemoteRepository.PAGE_SIZE,
        )
    ) {
        RatingItemPagingDataSource(preloadedData = RemoteRepository().generateRatings(1))
    }.flow
        .cachedIn(viewModelScope)
}