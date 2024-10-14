package com.example.aerospace.ui.satelliteList

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aerospace.R
import com.example.aerospace.data.model.SatelliteListResponseItem
import com.example.aerospace.databinding.ItemSatelliteBinding

class SatelliteListAdapter(
    private val context: Context,
    private val satelliteList: ArrayList<SatelliteListResponseItem>
) :
    RecyclerView.Adapter<SatelliteListAdapter.DataViewHolder>() {

    private var onClickListener: ((SatelliteListResponseItem) -> Unit)? = null
    private var spaceItemListFiltered: ArrayList<SatelliteListResponseItem> =
        ArrayList(satelliteList)

    fun setOnClickListener(listener: (SatelliteListResponseItem) -> Unit) {
        onClickListener = listener
    }

    class DataViewHolder(private val binding: ItemSatelliteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            context: Context,
            item: SatelliteListResponseItem,
            onItemClick: (SatelliteListResponseItem) -> Unit
        ) {
            binding.tvMissionName.text =
                "${context.getString(R.string.mission_name)} ${item.mission_name}"
            binding.tvLaunchYear.text =
                "${context.getString(R.string.launch_year)} ${item.launch_year}"
            binding.tvRocketName.text =
                "${context.getString(R.string.rocket_name)} ${item.rocket.rocket_name}"

            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        return DataViewHolder(
            ItemSatelliteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return spaceItemListFiltered.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(context, spaceItemListFiltered[position]) { clickedItem ->
            onClickListener?.invoke(clickedItem)
        }
    }

    fun addData(list: List<SatelliteListResponseItem>) {
        satelliteList.addAll(list)
        spaceItemListFiltered = ArrayList(satelliteList)
    }

    fun filter(query: String) {
        spaceItemListFiltered = if (query.isEmpty()) {
            ArrayList(satelliteList)
        } else {
            val filteredList = satelliteList.filter {
                it.mission_name.contains(query, ignoreCase = true)
            }
            ArrayList(filteredList)
        }
        notifyDataSetChanged()
    }
}