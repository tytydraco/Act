package com.draco.act.models

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity(
    val displayLabel: String,
    val packageLabel: String,
    @PrimaryKey val packageId: String,
    val activity: String,
    val icon: Drawable,
    var starred: Boolean = false
)