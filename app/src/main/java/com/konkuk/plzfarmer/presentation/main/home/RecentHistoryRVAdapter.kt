package com.konkuk.plzfarmer.presentation.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.databinding.ItemRecentHistoryBinding
import com.konkuk.plzfarmer.presentation.main.home.RecentHistoryRVAdapter.*

class RecentHistoryRVAdapter (
    val itemClickedListener: (RecentHistoryItemVO) -> Unit
): RecyclerView.Adapter<MyViewHolder>() {

    var recentHistoryList = listOf<RecentHistoryItemVO>()

    inner class MyViewHolder(val binding: ItemRecentHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(recentHistory: RecentHistoryItemVO){
            Log.d("RecentHistoryRVAdapter", "${recentHistory.plantType}/${recentHistory.imgUrl}/${recentHistory.date}/${recentHistory.diseaseName}")
            binding.recentHistory = recentHistory
            recentHistory.imgUrl?.let {
                Glide.with(binding.root.context).load(it).into(binding.plantImageIv)
            }
            itemView.setOnClickListener {
                itemClickedListener(recentHistory)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecentHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = recentHistoryList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(recentHistoryList[position])
    }
}