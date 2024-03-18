package com.konkuk.plzfarmer.presentation.common;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewItemDecorationVertical extends RecyclerView.ItemDecoration {

    private final int divHeight;

    public RecyclerViewItemDecorationVertical(int divHeight)
    {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        super.getItemOffsets(outRect, view, parent, state);

        int position = parent.getChildAdapterPosition(view); // 현재 아이템의 위치
        int itemCount = parent.getAdapter().getItemCount(); // RecyclerView의 아이템 개수

        if (position == itemCount - 1 && itemCount > 0) { // 현재 아이템이 마지막 아이템이고 아이템이 1개 이상인 경우
            outRect.bottom = divHeight * 5; // 마지막 아이템인 경우 bottom margin을 크게 설정
        } else {
            outRect.bottom = divHeight; // 마지막 아이템이 아닌 경우 기본적인 bottom margin 설정
        }
    }
}