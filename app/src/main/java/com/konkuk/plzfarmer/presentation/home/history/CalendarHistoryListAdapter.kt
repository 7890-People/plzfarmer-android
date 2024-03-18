package com.konkuk.plzfarmer.presentation.home.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.databinding.ItemHistoryCalendarBinding
import com.konkuk.plzfarmer.remote.response.DiseaseHistory

class CalendarHistoryListAdapter : RecyclerView.Adapter<CalendarHistoryListAdapter.MyViewHolder>() {

    var historyList = listOf<DiseaseHistory>()

    inner class MyViewHolder(private val binding: ItemHistoryCalendarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(disease: DiseaseHistory) {
            binding.diseaseNameKrTextView.text = disease.diseaseKrName
            binding.diseaseNameEngTextView.text = disease.diseaseEngName
            Glide.with(binding.imageView).load(disease.imageUrl).into(binding.imageView)
            binding.imageView.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemHistoryCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(historyList[position])
    }

}