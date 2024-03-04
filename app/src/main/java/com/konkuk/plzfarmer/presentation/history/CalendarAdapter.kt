package com.konkuk.plzfarmer.presentation.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.databinding.ItemCalendarDateBinding

data class HistoryUiModel(
    val date: String,
    val diseaseKrName: String,
    val diseaseEngName: String,
    val imgUrl: String,
    val plantName: String
)

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    var historyUiModels: MutableList<HistoryUiModel> = mutableListOf()

    inner class CalendarViewHolder(private val binding: ItemCalendarDateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(historyUiModel: HistoryUiModel) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CalendarViewHolder(binding)
    }

    override fun getItemCount(): Int = historyUiModels.size

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.bind(historyUiModels[position])
    }

    override fun onViewRecycled(holder: CalendarViewHolder) {
        super.onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: CalendarViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }
}