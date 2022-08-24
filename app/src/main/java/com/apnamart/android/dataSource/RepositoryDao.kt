package com.apnamart.android.dataSource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apnamart.android.models.RepositoryModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepo(repo: RepositoryModel)

    @Transaction
    @Query("SELECT * from trendingRepository")
    suspend fun getAllTrendingRepos(): List<RepositoryModel>

    @Delete
    suspend fun deleteRepo(repo: RepositoryModel)

    @Query("Delete  from trendingRepository")
    suspend fun clearRepo()

}