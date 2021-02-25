package com.gabrielribeiro.suacorrida.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gabrielribeiro.suacorrida.R
import com.gabrielribeiro.suacorrida.model.Run
import com.gabrielribeiro.suacorrida.utils.TrackingUtility
import kotlinx.android.synthetic.main.item_run.view.*
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewRun : ImageView = itemView.findViewById(R.id.imageViewRunImage)
        val textViewDateItem : TextView = itemView.findViewById(R.id.textViewDateItem)
        val textViewTimeItem : TextView = itemView.findViewById(R.id.textViewTimeItem)
        val textViewDistanceItem : TextView = itemView.findViewById(R.id.textViewDistanceItem)
        val textViewAvgSpeedItem : TextView = itemView.findViewById(R.id.textViewAvgSpeedItem)
        val textViewCaloriesItem : TextView = itemView.findViewById(R.id.textViewCaloriesItem)

    }


    val differCallback = object : DiffUtil.ItemCallback<Run>(){
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, differCallback)
    fun submitList(runList : List<Run>){
        differ.submitList(runList)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false))
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]

        holder.itemView.apply {
            holder.apply {
                Glide.with(context).load(run.image).into(imageViewRun)
                val calender = Calendar.getInstance().apply {
                    timeInMillis = run.timestamp

                }

                val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                textViewDateItem.text = "Data \n ${dateFormat.format(calender.time)}"
                val avgSpeed = "Velocidade \n ${run.avgSpeedInKMH}KM/H"
                textViewAvgSpeedItem.text = avgSpeed
                val distanceInMeters = "Metros \n ${run.distanceInMeters / 1000F}"
                textViewDistanceItem.text = distanceInMeters
                textViewTimeItem.text = "Tempo \n ${TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)}"
                val caloriesBurned = "Calorias \n${run.caloriesBurned} Kcal"
                textViewCaloriesItem.text = caloriesBurned

            }



        }
    }

    override fun getItemCount(): Int = differ.currentList.size
}