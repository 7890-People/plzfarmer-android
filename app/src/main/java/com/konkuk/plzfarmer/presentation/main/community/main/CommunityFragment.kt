package com.konkuk.plzfarmer.presentation.main.community.main

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentCommunityBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.common.RecyclerViewItemDecorationVertical
import com.konkuk.plzfarmer.presentation.main.MainActivity
import com.konkuk.plzfarmer.presentation.main.community.CommunityViewModel
import com.konkuk.plzfarmer.presentation.main.community.detail.CommunityDetailFragment

class CommunityFragment : BaseFragment<FragmentCommunityBinding>() {
    override val TAG: String = "CommunityFragment"
    override val layoutRes: Int = R.layout.fragment_community
    private val communityViewModel: CommunityViewModel by activityViewModels()

    lateinit var postRVAdapter: PostRVAdapter

    override fun afterViewCreated() {
        initData()
        initObservers()
        initRecyclerView()
        initRadioButton()
    }

    private fun initRadioButton() {
        communityViewModel.getPostList("전체") //초기값
        binding.communityFilterRg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.community_filter_all_rb -> communityViewModel.getPostList("전체")
                R.id.community_filter_chat_rb -> communityViewModel.getPostList("잡담")
                R.id.community_filter_question_rb -> communityViewModel.getPostList("질문")
                else-> {}
            }
        }
    }

    private fun initData() {

    }

    private fun initObservers() {
        communityViewModel.postList.observe(this) {
            it.byState(
                onSuccess = {response ->
                    postRVAdapter.postList = response
                    postRVAdapter.notifyDataSetChanged()
                },
                onError = {

                },
                onLoading = {

                })
        }
    }

    private fun initRecyclerView() {
        binding.communityPostsListRv.addItemDecoration(RecyclerViewItemDecorationVertical(40)) // 수직 간격 설정
        postRVAdapter = PostRVAdapter(onPostItemClicked)
        binding.communityPostsListRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.communityPostsListRv.adapter = postRVAdapter
    }

    private val onPostItemClicked: (post: PostVO) -> Unit = {
        Log.d(TAG, "onPostItemClicked")
        // 커뮤니티 상세로 이동
        val act = requireActivity() as MainActivity
        val communityDetailFragment = CommunityDetailFragment()
        startFragment(communityDetailFragment, "communityDetailFragment")
        act.setBtnvVisibility(false)
    }

    private fun startFragment(fragment: Fragment, name: String){
        val transaction: FragmentTransaction = (context as MainActivity).supportFragmentManager.beginTransaction()
        transaction.addToBackStack(name).replace(R.id.mainFragmentContainer, fragment)
        transaction.commit()
    }
}