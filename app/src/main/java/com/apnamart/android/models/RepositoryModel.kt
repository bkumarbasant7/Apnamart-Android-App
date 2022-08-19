package com.apnamart.android.models

data class RepositoryModel(
    val author: String,
    val title: String,
    val description: String,
    val forkCount: Int,
    val star: Int,
    val languages: String,
    var isExpanded: Boolean
)