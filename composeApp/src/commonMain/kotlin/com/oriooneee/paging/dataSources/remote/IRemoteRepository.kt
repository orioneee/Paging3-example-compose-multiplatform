package com.oriooneee.paging.dataSources.remote

import com.oriooneee.paging.domain.RatingEntity

interface IRemoteRepository {
    suspend fun getRatings(
        page: Int
    ): List<RatingEntity>
}