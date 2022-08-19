package com.apnamart.android.dataSource

import androidx.room.*
import com.apnamart.android.models.RepositoryModel

@Dao
interface RepositoryDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepo(repo: RepositoryModel)

    @Transaction
    @Query("SELECT * from trendingRepository")
    suspend fun getAllTrendingRepos():List<RepositoryModel>

    @Delete
    suspend fun deleteRepo(repo: RepositoryModel)

}