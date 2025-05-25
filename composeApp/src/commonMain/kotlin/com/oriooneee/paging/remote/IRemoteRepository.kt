package com.oriooneee.paging.remote

import com.oriooneee.paging.domain.RatingEntity

interface IRemoteRepository {
    suspend fun getRatings(
        page: Int
    ): List<RatingEntity>
}