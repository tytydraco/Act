package com.draco.act.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.draco.act.models.Activity

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity")
    fun getAll(): List<Activity>

    @Insert
    fun insertAll(vararg activities: Activity)

    @Insert
    fun insertAll(activities: List<Activity>)

    @Delete
    fun delete(activity: Activity)
}