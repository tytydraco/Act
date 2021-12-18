package com.draco.act.models

import android.content.ComponentName
import android.graphics.drawable.Drawable

data class Activity(
    val packageLabel: String,
    val packageId: String,
    val activity: String,
    val icon: Drawable
)