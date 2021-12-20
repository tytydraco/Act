package com.draco.act.models

import android.graphics.drawable.Drawable

data class Activity(
    val displayLabel: String,
    val packageLabel: String,
    val packageId: String,
    val activity: String,
    val icon: Drawable,
    var starred: Boolean = false
) {
    companion object {
        val sortComparator = compareBy<Activity> { !it.starred }
            .thenBy { it.packageLabel }
            .thenBy { it.displayLabel }
            .thenBy { it.activity }
    }
}