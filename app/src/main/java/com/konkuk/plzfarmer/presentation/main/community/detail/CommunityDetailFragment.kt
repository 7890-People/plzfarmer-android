package com.konkuk.plzfarmer.presentation.main.community.detail

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentCommunityDetailBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.common.RecyclerViewItemDecorationVertical
import com.konkuk.plzfarmer.presentation.main.MainActivity
import com.konkuk.plzfarmer.presentation.main.community.CommunityViewModel


class CommunityDetailFragment : BaseFragment<FragmentCommunityDetailBinding>() {
    override val TAG: String = "CommunityDetailFragment"
    override val layoutRes: Int = R.layout.fragment_community_detail
    private val communityViewModel: CommunityViewModel by activityViewModels()

    lateinit var commentRVAdapter: CommentRVAdapter
    override fun afterViewCreated() {
        initData()
        initClickListener()
        initRecyclerView()
        initObservers()
    }
    private fun initData() {
        communityViewModel.getCommentList()
    }

    private fun initClickListener() {
        binding.communityDetailBackIv.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun initRecyclerView() {
        binding.communityDetailCommentRv.addItemDecoration(RecyclerViewItemDecorationVertical(15)) // 수직 간격 설정
        commentRVAdapter = CommentRVAdapter(onCommentItemClicked)
        binding.communityDetailCommentRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.communityDetailCommentRv.adapter = commentRVAdapter
    }

    private val onCommentItemClicked: (comment: CommentVO) -> Unit = {
        Log.d(TAG, "onCommentItemClicked")
    }

    private fun initObservers() {
        communityViewModel.commentList.observe(this) {
            it.byState(
                onSuccess = {response ->
                    commentRVAdapter.commentList = response
                    commentRVAdapter.notifyDataSetChanged()
                },
                onError = {

                },
                onLoading = {

                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val act = requireActivity() as MainActivity
        act.setBtnvVisibility(true)
    }

}