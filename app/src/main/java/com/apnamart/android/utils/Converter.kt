package com.apnamart.android.utils

import com.apnamart.android.models.RepositoryModel
import com.apnamart.android.webservice.response.TrendingRepoResponse


fun TrendingRepoResponse.parseToTrendingModel(): List<RepositoryModel> {
    val list = mutableListOf<RepositoryModel>()
    for (i in items) {
        val model = RepositoryModel(
            author = i.name ?: "",
            description = i.description ?: "NA",
            forkCount = i.forks_count ?: 0,
            star = i.stargazers_count ?: 0,
            title = i.full_name ?: "NA",
            languages = i.language ?: "NA",
            profilePic = i.owner.avatar_url,
            isExpanded = false
        )
        list.add(model)

    }
    return list

}