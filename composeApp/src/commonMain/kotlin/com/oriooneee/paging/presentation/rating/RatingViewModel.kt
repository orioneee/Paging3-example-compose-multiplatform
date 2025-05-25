package com.oriooneee.paging.presentation.rating

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.oriooneee.paging.domain.RatingEntity
import com.oriooneee.paging.remote.PagingDataSource
import com.oriooneee.paging.remote.RemoteRepository

class RatingViewModel(
    private val preloadedData: List<RatingEntity> = emptyList(),
) : ViewModel() {
    val ratingPager = Pager(PagingConfig(pageSize = RemoteRepository.PAGE_SIZE)) {
        PagingDataSource(preloadedData = RemoteRepository().generateRatings(1))
    }.flow.cachedIn(viewModelScope)
}