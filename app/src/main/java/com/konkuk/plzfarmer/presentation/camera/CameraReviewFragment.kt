package com.konkuk.plzfarmer.presentation.camera

import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentCameraReviewBinding
import com.konkuk.plzfarmer.presentation.base.BaseFragment

class CameraReviewFragment : BaseFragment<FragmentCameraReviewBinding>() {
    override val TAG: String = "FragmentCheckPhoto"

    override val layoutRes: Int = R.layout.fragment_camera_review

    private lateinit var currentPhotoPath: String
    private lateinit var uri: String
    private lateinit var plantName: String

    override fun afterViewCreated() {
        arguments?.apply {
            uri = getString("photoUri")!!
            currentPhotoPath = getString("currentPhotoPath")!!
            plantName = getString("plantName")!!
        }

        binding.imageView.clipToOutline = true
        Glide.with(requireActivity())
            .load(uri)
            .into(binding.imageView)

        binding.btnGo.setOnClickListener {
            gotoResultActivity()
        }
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun gotoResultActivity() {
//        val intent = Intent(requireActivity(), DiagnosisResultActivity::class.java)
//        intent.putExtra("photoUri", uri)
//        intent.putExtra("currentPhotoPath", currentPhotoPath)
//        intent.putExtra("plantName", plantName)
//        startActivity(intent)
//        requireActivity().finish()
    }
}