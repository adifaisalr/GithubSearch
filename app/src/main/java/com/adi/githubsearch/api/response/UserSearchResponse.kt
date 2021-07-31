package com.adi.githubsearch.api.response

data class UserSearchResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)