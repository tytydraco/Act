package com.draco.act.views

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.draco.act.databinding.ActivityAddBinding
import com.draco.act.databinding.ActivityMainBinding
import com.draco.act.recyclers.AddRecyclerAdapter
import com.draco.act.viewmodels.AddActivityViewModel
import com.draco.act.viewmodels.MainActivityViewModel

class AddActivity : AppCompatActivity() {
    private val viewModel: AddActivityViewModel by viewModels()
    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val a = AddRecyclerAdapter(this@AddActivity, mutableListOf())
        a.setHasStableIds(true)

        binding.recycler.apply {
            adapter = a
            layoutManager = LinearLayoutManager(this@AddActivity)
        }

        viewModel.activityList.observe(this) {
            if (it == null)
                return@observe
            a.activities = it
            a.notifyDataSetChanged()
        }

        a.starToggle = {
            a.activities[it].starred = !a.activities[it].starred
            a.notifyItemChanged(it)
        }

        viewModel.getAllActivities(this)
    }
}