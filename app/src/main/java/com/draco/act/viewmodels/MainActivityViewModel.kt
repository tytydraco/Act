package com.draco.act.viewmodels

import android.app.Application
import android.content.ComponentName
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.draco.act.models.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Find all package activities that are available to us
     */
    fun getAllActivities(packageManager: PackageManager, callback: (List<Activity>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val activityList = mutableListOf<Activity>()

            val packages = packageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES)
            for (packageInfo in packages) {
                val activities = packageInfo.activities

                if (activities.isNullOrEmpty())
                    continue

                val applicationInfo = packageManager.getApplicationInfo(packageInfo.packageName, 0)
                val packageLabel = packageManager.getApplicationLabel(applicationInfo).toString()

                for (activity in activities) {
                    val componentName = ComponentName(packageInfo.packageName, activity.name)
                    val activityInfo = packageManager.getActivityInfo(componentName, 0)
                    val icon = activityInfo.loadIcon(packageManager)
                    val activityLabel = activityInfo.loadLabel(packageManager).toString()

                    val displayName = if (activityLabel != packageLabel)
                        activityLabel
                    else
                        packageLabel

                    activityList.add(
                        Activity(
                            displayName,
                            packageLabel,
                            activityInfo.packageName,
                            activityInfo.name,
                            icon
                        )
                    )
                }
            }

            activityList.sortWith(
                compareBy<Activity> { it.packageLabel }
                    .thenBy { it.displayLabel }
                    .thenBy { it.activity }
            )

            viewModelScope.launch {
                callback(activityList)
            }
        }
    }
}