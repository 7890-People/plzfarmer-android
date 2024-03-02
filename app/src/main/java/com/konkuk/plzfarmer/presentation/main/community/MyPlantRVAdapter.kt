package com.konkuk.plzfarmer.presentation.main.community

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.databinding.ItemPostBinding
import com.konkuk.plzfarmer.presentation.main.community.PostRVAdapter.MyViewHolder


class PostRVAdapter (
    val itemClickedListener: (PostVO) -> Unit
): RecyclerView.Adapter<MyViewHolder>() {

    var postList = listOf<PostVO>()

    inner class MyViewHolder(val binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostVO){
            Log.d("PostRVAdapter", "${post.postId}/${post.postType}/${post.postTitle}/${post.postMemberName}/${post.postDate}/ 좋아요 ${post.likeCount}/ 댓글 ${post.commentCount}")
            binding.post = post

            post.postImage?.let {
                Glide.with(binding.root.context).load(it).into(binding.postImageIv)
            }?: run {
                // postImage가 null인 경우에 실행되는 코드
                binding.postImageIv.visibility = View.GONE
            }
            itemView.setOnClickListener {
                itemClickedListener(post)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = postList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(postList[position])
    }
}