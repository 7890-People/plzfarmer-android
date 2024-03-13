package com.konkuk.plzfarmer.presentation.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ItemMyPlantBinding
import com.konkuk.plzfarmer.presentation.main.home.MyPlantRVAdapter.MyViewHolder

class MyPlantRVAdapter (
    val itemClickedListener: (MyPlantVO) -> Unit
): RecyclerView.Adapter<MyViewHolder>() {

    var myPlantList = listOf<MyPlantVO>()

    inner class MyViewHolder(val binding: ItemMyPlantBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(myPlant: MyPlantVO){
            Log.d("MyPlantRVAdapter", "${myPlant.plantTypeKor}/${myPlant.plantTypeEng}/${myPlant.imgUrl}")
            binding.myPlant = myPlant
            //식물 이미지
            binding.plantImageIv.setImageResource(R.drawable.home_icon_graph)
            itemView.setOnClickListener {
                itemClickedListener(myPlant)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMyPlantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = myPlantList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(myPlantList[position])
    }
}