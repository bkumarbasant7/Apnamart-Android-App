package com.apnamart.android.dataSource

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apnamart.android.models.RepositoryModel
import com.apnamart.android.utils.clearCache
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [RepositoryModel::class], version = 1)
abstract class RepositoryDb : RoomDatabase() {
    abstract fun repoDao(): RepositoryDao

    companion object {
        @Volatile
        private var instance: RepositoryDb? = null
        private val LOCK = Any()

        @OptIn(InternalCoroutinesApi::class)
        operator fun invoke(context: Context) =
            instance ?: kotlinx.coroutines.internal.synchronized(
                LOCK
            ) {
                instance ?: buildRepositoryDatabase(context).also {
                    instance = it
                }
            }

        private fun buildRepositoryDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            RepositoryDb::class.java,
            "RepositoryDatabase"
        ).build()
        fun clearCache(context: Context){
            context.applicationContext.clearCache()
        }
    }
}