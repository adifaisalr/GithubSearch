package com.adi.githubsearch.domain.repository

import com.adi.githubsearch.api.Result
import com.adi.githubsearch.api.response.UserSearchResponse

interface UserRepository {
    suspend fun searchUsers(query: String): Result<UserSearchResponse>
    suspend fun searchUsers(query: String, page: Int): Result<UserSearchResponse>
}