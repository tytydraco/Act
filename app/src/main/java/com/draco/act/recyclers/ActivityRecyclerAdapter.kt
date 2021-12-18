package com.draco.act.recyclers

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.draco.act.databinding.ActivityItemBinding
import com.draco.act.models.Activity

class ActivityRecyclerAdapter(
    private val context: Context,
    var activities: MutableList<Activity>
): RecyclerView.Adapter<ActivityRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ActivityItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ActivityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = activities[position]
        holder.binding.label.text = activity.displayLabel
        holder.binding.activity.text = activity.activity
        holder.binding.img.setImageDrawable(activity.icon)

        val intent = Intent()
            .setComponent(ComponentName(activity.packageId, activity.activity))
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        holder.itemView.setOnClickListener {
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }
    }

    override fun getItemCount() = activities.size

    override fun getItemId(position: Int) = activities[position].hashCode().toLong()
}