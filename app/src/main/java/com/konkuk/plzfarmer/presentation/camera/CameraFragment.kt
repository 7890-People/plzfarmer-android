package com.konkuk.plzfarmer.presentation.camera

import BaseFragment
import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.FragmentCameraBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CameraFragment : BaseFragment<FragmentCameraBinding>() {

    companion object {
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
                add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }.toTypedArray()
    }

    override val TAG: String = "CameraFragment"
    override val layoutRes: Int = R.layout.fragment_camera

    private lateinit var cameraController: LifecycleCameraController

    private lateinit var cameraAnimationListener: Animation.AnimationListener
    private fun setCameraAnimationListener() {
        cameraAnimationListener = object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.shutterLayout.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        }
    }


    override fun afterViewCreated() {
        requestPermission()
        setCameraAnimationListener()

        binding.captureBtn.setOnClickListener {
            takePhoto()
        }

        arguments?.let {
            val plantStr = "촬영작물: ${it.getString("plantName")}"
            binding.textviewIndicator.text = plantStr
        }
    }


    private fun requestPermission() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                showToast("카메라 권한을 허용해주세요")
                requireActivity().finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        cameraController = LifecycleCameraController(requireContext())
        cameraController.bindToLifecycle(this)
        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        binding.previewView.controller = cameraController
    }

    private fun takePhoto() {
        binding.captureBtn.isEnabled = false

        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.KOREA)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/농부해")
            }
        }

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        loadShutterAnim()
        cameraController.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    binding.captureBtn.isEnabled = true
                    Toast.makeText(requireContext(), "사진 저장에 실패했습니다. 다시 시도해주세요", Toast.LENGTH_SHORT)
                        .show()
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)

                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    binding.captureBtn.isEnabled = true
                    Log.d(TAG, msg)

                    if (output.savedUri != null) {
                        val activity = requireActivity() as DiagnosisActivity
                        activity.showCheckFragment(output.savedUri!!)
                    } else {
                        showToast("이미지를 불러오는 데 실패하였습니다")
                    }

                }
            }
        )
    }

    private fun loadShutterAnim() {
//        val animation =
//            AnimationUtils.loadAnimation(requireContext(), R.anim.anim_shutter)
//        animation.setAnimationListener(cameraAnimationListener)
//        binding.shutterLayout.apply {
//            this.animation = animation
//            visibility = View.VISIBLE
//            startAnimation(animation)
//        }
    }

}