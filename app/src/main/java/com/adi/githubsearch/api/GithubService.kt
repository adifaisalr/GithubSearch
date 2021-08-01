package com.adi.githubsearch.api

import com.adi.githubsearch.api.response.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface GithubService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): Result<UserSearchResponse>

    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String, @Query("page") page: Int): Result<UserSearchResponse>
}
