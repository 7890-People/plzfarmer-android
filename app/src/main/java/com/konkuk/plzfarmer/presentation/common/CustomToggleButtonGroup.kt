package com.konkuk.plzfarmer.presentation.common

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout

class CustomToggleButtonGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val childHashMap: HashMap<Int, CustomSelectButton> = hashMapOf()

    init {
        orientation = HORIZONTAL
    }

    var selectedId: Int? = null


    override fun onFinishInflate() {
        super.onFinishInflate()
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            if (child is CustomSelectButton) {
                childHashMap[child.id] = child
                child.setToggleClickListener {
                    Log.d("Custom", "button 클릭됨 id: ${child.id}")
                    //isSelected 상태 변경 이후
                    if (child.isSelected) {
                        childHashMap[selectedId ?: -1]?.binding?.root?.performClick()
                        selectedId = child.id
                    } else {
                        selectedId = null
                    }
                }
            }
        }
    }

}
