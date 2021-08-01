package com.adi.githubsearch.domain.usecase

import com.adi.githubsearch.api.Result
import com.adi.githubsearch.api.response.UserSearchResponse
import com.adi.githubsearch.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(query: String): Result<UserSearchResponse> =
        withContext(Dispatchers.IO) {
            return@withContext userRepository.searchUsers(query)
        }

    suspend operator fun invoke(query: String, page: Int): Result<UserSearchResponse> =
        withContext(Dispatchers.IO) {
            return@withContext userRepository.searchUsers(query, page)
        }
}