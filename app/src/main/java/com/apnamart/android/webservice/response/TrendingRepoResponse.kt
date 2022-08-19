package com.apnamart.android.webservice.response

data class TrendingRepoResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)