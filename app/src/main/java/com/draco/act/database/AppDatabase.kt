package com.draco.act.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.draco.act.models.Activity

@Database(entities = [Activity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao
}