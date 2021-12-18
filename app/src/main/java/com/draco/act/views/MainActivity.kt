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

        binding.recycler.apply {
            adapter = a
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }

        viewModel.activityList.observe(this) {
            if (it == null)
                return@observe
            a.activities = it
            a.notifyDataSetChanged()
        }

        viewModel.getAllActivities(this)
    }
}