package com.adi.githubsearch.data.repository

import com.adi.githubsearch.api.GithubService
import com.adi.githubsearch.api.Result
import com.adi.githubsearch.api.response.UserSearchResponse
import com.adi.githubsearch.domain.repository.UserRepository

class UserRepositoryImpl(
    private val githubService: GithubService
) : UserRepository {

    override suspend fun searchUsers(query: String): Result<UserSearchResponse> {
        return githubService.searchUsers(query)
    }

    override suspend fun searchUsers(query: String, page: Int): Result<UserSearchResponse> {
        return githubService.searchUsers(query, page)
    }
}