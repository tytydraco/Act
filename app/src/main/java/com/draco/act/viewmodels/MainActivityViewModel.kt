package com.draco.act.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.draco.act.models.Activity

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    fun getActivities(context: Context): List<Activity> {
        val packageManager = context.packageManager
        // TODO: fetch
        return emptyList()
    }
}