package com.apnamart.android.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trendingRepository")
data class RepositoryModel(
    @PrimaryKey
    val id :Int,
    val author: String,
    val title: String,
    val profilePic: String,
    val description: String,
    val forkCount: Int,
    val star: Int,
    val languages: String,
    var isExpanded: Boolean
)