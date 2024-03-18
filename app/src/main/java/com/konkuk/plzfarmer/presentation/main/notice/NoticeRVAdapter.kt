package com.konkuk.plzfarmer.presentation.main.notice

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.konkuk.plzfarmer.R
import androidx.recyclerview.widget.RecyclerView
import com.konkuk.plzfarmer.databinding.ItemNoticeBinding
import com.konkuk.plzfarmer.presentation.main.notice.NoticeRVAdapter.*


class NoticeRVAdapter (): RecyclerView.Adapter<MyViewHolder>() {

    var noticeList = listOf<NoticeVO>()

    inner class MyViewHolder(val binding: ItemNoticeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(notice: NoticeVO){
            Log.d("NoticeRVAdapter", "${notice.noticeId}/${notice.noticeContent1}/${notice.noticeContent2}")

            binding.notice = notice
            // 알림 아이콘 설정
            if (notice.noticeType == "댓글"){
                binding.postImageIv.setImageResource(R.drawable.notice_icon_comment)
            }else if (notice.noticeType == "좋아요"){
                binding.postImageIv.setImageResource(R.drawable.notice_icon_like)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNoticeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = noticeList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }
}