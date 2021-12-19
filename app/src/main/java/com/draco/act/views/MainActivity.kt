package com.draco.act.views

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a = ActivityRecyclerAdapter(this@MainActivity, mutableListOf())
        a.setHasStableIds(true)

        binding.recycler.apply {
            adapter = a
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.activityList.observe(this) {
            if (it == null)
                return@observe
            a.activities = it
            a.notifyDataSetChanged()
        }

        a.starToggle = {
            a.activities[it].starred = !a.activities[it].starred
            a.sort()

            viewModel.save()
        }

        if (!viewModel.load())
            viewModel.getAllActivities(this)
        else
            a.sort()
    }
}