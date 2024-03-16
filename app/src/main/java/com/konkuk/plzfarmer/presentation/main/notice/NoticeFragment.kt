package com.konkuk.plzfarmer.presentation.main.notice

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentNoticeBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment
import com.konkuk.plzfarmer.presentation.common.RecyclerViewItemDecorationVertical


class NoticeFragment  : BaseFragment<FragmentNoticeBinding>() {
    override val TAG: String = "NoticeFragment"
    override val layoutRes: Int = R.layout.fragment_notice
    private val noticeViewModel: NoticeViewModel by activityViewModels()

    lateinit var noticeRVAdapter: NoticeRVAdapter

    override fun afterViewCreated() {
        initData()
        initRecyclerView()
        initObservers()
    }

    private fun initData() {
        noticeViewModel.getNoticeList() //알람 정보 조회
    }

    private fun initRecyclerView() {
        binding.noticeListRv.addItemDecoration(RecyclerViewItemDecorationVertical(40)) // 수직 간격 설정
        noticeRVAdapter = NoticeRVAdapter()
        binding.noticeListRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.noticeListRv.adapter = noticeRVAdapter
    }

    private fun initObservers() {
        noticeViewModel.noticeList.observe(this) {
            it.byState(
                onSuccess = {response ->
                    noticeRVAdapter.noticeList = response
                    noticeRVAdapter.notifyDataSetChanged()
                    binding.noticeNum = noticeRVAdapter.noticeList.size
                },
                onError = {

                },
                onLoading = {

                })
        }
    }
}