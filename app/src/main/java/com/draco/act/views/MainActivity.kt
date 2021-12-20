package com.draco.act.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.draco.act.databinding.ActivityMainBinding
import com.draco.act.recyclers.ActivityRecyclerAdapter
import com.draco.act.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var activityRecyclerAdapter: ActivityRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPrefs = getPreferences(Context.MODE_PRIVATE)

        activityRecyclerAdapter = ActivityRecyclerAdapter(this@MainActivity, mutableListOf())
        activityRecyclerAdapter.setHasStableIds(true)

        binding.recycler.apply {
            adapter = activityRecyclerAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        val starred = sharedPrefs.getStringSet("starred", null) ?: emptySet()
        viewModel.getAllActivities(this, starred) {
            runOnUiThread {
                activityRecyclerAdapter.updateActivityList(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.save(sharedPrefs, activityRecyclerAdapter.getActivityList())
    }
}