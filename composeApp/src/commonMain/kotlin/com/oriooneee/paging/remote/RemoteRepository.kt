package com.oriooneee.paging.remote

import com.oriooneee.paging.domain.RatingEntity
import kotlinx.coroutines.delay

class RemoteRepository : IRemoteRepository {
    companion object{
        const val PAGE_SIZE = 20
    }
    override suspend fun getRatings(page: Int): List<RatingEntity> {
        println("Fetching ratings for page $page")
        delay(1_500) // Simulate network delay

        // Generate 10 random ratings per page
        return generateRatings(page)
    }

    fun generateRatings(page: Int): List<RatingEntity> {
        return List(PAGE_SIZE) { index ->
            val id = (page - 1) * PAGE_SIZE + index + 1
            RatingEntity(
                id = id,
                position = id,
                name = randomName(),
                surname = randomSurname()
            )
        }
    }

    private fun randomName(): String {
        val names = listOf("Alice", "Bob", "Charlie", "Diana", "Ethan", "Fiona", "George", "Hannah")
        return names.random()
    }

    private fun randomSurname(): String {
        val surnames =
            listOf("Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis")
        return surnames.random()
    }
}