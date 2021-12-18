package com.draco.act.models

import android.graphics.drawable.Drawable

data class Activity(
    val displayLabel: String,
    val packageLabel: String,
    val packageId: String,
    val activity: String,
    val icon: Drawable,
    var starred: Boolean = false
)