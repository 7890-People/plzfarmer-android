package com.konkuk.plzfarmer.presentation.main.community.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.databinding.ItemCommentBinding
import com.konkuk.plzfarmer.databinding.ItemPostBinding
import com.konkuk.plzfarmer.presentation.main.community.detail.CommentRVAdapter.MyViewHolder


class CommentRVAdapter (
    val itemClickedListener: (CommentVO) -> Unit
): RecyclerView.Adapter<MyViewHolder>() {

    var commentList = listOf<CommentVO>()

    inner class MyViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentVO){
            Log.d("CommentRVAdapter", "${comment.commentWriterName}/${comment.commentProfileImage}/${comment.commentContent}/${comment.commentDateTime}")
            binding.comment = comment

            comment.commentProfileImage?.let {
                Glide.with(binding.root.context).load(it).into(binding.commentProfileImgIv)
            }?: run {
                // 프로필 이미지가 null인 경우에 실행되는 코드
            }
            itemView.setOnClickListener {
                itemClickedListener(comment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = commentList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(commentList[position])
    }
}