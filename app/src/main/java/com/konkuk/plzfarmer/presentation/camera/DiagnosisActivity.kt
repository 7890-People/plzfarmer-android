package com.konkuk.plzfarmer.presentation.camera

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.commit
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.ActivityDiagnosisBinding
import com.konkuk.plzfarmer.presentation.base.BaseActivity

class DiagnosisActivity : BaseActivity<ActivityDiagnosisBinding>() {
    override val TAG: String = "CameraActivity"
    override val layoutRes: Int = R.layout.activity_diagnosis

    private lateinit var plantName: String

    override fun afterViewCreated() {
        plantName = intent.getStringExtra("plantName")!!
        val bundle = Bundle()
        bundle.putString("plantName", plantName)
        val cameraFragment = CameraFragment()
        cameraFragment.arguments = bundle
        supportFragmentManager.commit {
//            setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            replace(R.id.frameLayout, cameraFragment, cameraFragment.javaClass.name)
            addToBackStack(null)
        }
        setBackBtn()
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            when (supportFragmentManager.fragments.first { it.isVisible }) {
                is CameraFragment -> {
                    finish()
                }

                is CameraReviewFragment -> {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }

    private fun setBackBtn() {
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    fun showCheckFragment(uri: Uri) {
        val cameraReviewFragment = CameraReviewFragment()
        val bundle = Bundle()
        bundle.putString("photoUri", uri.toString())
        bundle.putString("currentPhotoPath", absolutePath(uri))
        bundle.putString("plantName", plantName)
        cameraReviewFragment.arguments = bundle

        supportFragmentManager.commit {
//            setCustomAnimations(
//                R.anim.slide_in_right,
//                R.anim.fade_out,
//                R.anim.fade_in,
//                R.anim.slide_out_right
//            )
            replace(R.id.frameLayout, cameraReviewFragment)
            addToBackStack(null)
        }

        binding.frameLayout.visibility = View.VISIBLE
    }

    private fun absolutePath(uri: Uri?): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = contentResolver.query(uri!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)
        c?.close()

        return result!!
    }
}