package com.konkuk.plzfarmer.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getResourceIdOrThrow
import com.bumptech.glide.Glide
import com.konkuk.plzfarmer.R
import com.konkuk.plzfarmer.databinding.LayoutCustomSelectButtonBinding


class CustomSelectButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet
) : ConstraintLayout(context, attrs) {
    private var drawableId: Int? = null
    private lateinit var labelString: String

    val binding by lazy {
        LayoutCustomSelectButtonBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.layout_custom_select_button, this, false)
        )
    }

    init {
        initAttrs(attrs)
        initView()
    }

    private fun initAttrs(attrs: AttributeSet) {
        context.theme.obtainStyledAttributes(
            attrs, R.styleable.CustomSelectButton, 0, 0
        ).apply {
            try {
                drawableId = getResourceIdOrThrow(R.styleable.CustomSelectButton_image_src)
                labelString = getString(R.styleable.CustomSelectButton_label_string) ?: ""
            } catch (e: IllegalArgumentException) {
                Log.e("CustomSelectButton", "DRAWABLE Resource ID NOT FOUND")
            } finally {
                recycle()
            }
        }
    }

    private fun initView() {
        Glide.with(context).load(drawableId).into(binding.customButtonIcon)
        binding.customButtonText.text = labelString

        binding.root.setOnClickListener {
            onBtnSelected()
        }
        addView(binding.root)
    }

    fun setToggleClickListener(listener: () -> Unit) {
        binding.root.setOnClickListener {
           onBtnSelected()
           listener()
        }
    }

    fun onBtnSelected(){
        isSelected = !isSelected
        binding.customButtonCheck.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
    }


}