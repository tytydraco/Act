package com.draco.act.viewmodels

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.draco.act.models.Activity
import com.draco.act.recyclers.ActivityRecyclerAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val _activityList = MutableLiveData<MutableList<Activity>>(mutableListOf())
    val activityList: LiveData<MutableList<Activity>> = _activityList

    /**
     * Find all package activities that are available to us
     */
    fun getAllActivities(context: Context) {
        val packageManager = context.packageManager

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

                    if (!activityInfo.exported)
                        continue

                    activityInfo.permission?.let {

                    }

                    if (activityInfo.permission != null &&
                        ContextCompat.checkSelfPermission(context, activityInfo.permission) != PackageManager.PERMISSION_GRANTED) {
                            continue
                    }

                    activityInfo.permission?.also {
                        Log.d("PERM", it)
                    }

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

                    activityList.sortWith(ActivityRecyclerAdapter.sortComparator)
                }

                _activityList.postValue(activityList)
            }
        }
    }
}