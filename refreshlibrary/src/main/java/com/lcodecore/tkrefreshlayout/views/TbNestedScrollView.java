package com.lcodecore.tkrefreshlayout.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

public class TbNestedScrollView extends NestedScrollView {


    private int mHeaderHeight = 0;
    private int originalScroll = 0;
    private RecyclerView mRecyclerView;

    public TbNestedScrollView(@NonNull Context context) {
        super(context);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TbNestedScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


//    @Override
//    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed,
//                               int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
//        super.onNestedScroll(target,dxConsumed,dyConsumed,dxUnconsumed,dyUnconsumed,type,consumed);
//    }

    /**
     * 获取recycleView以上的布局的高度数据
     * @param headerHeight
     */
    public void setHeaderHeight(int headerHeight){
        this.mHeaderHeight = headerHeight;
    }

    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed,
                                  @ViewCompat.NestedScrollType int type) {
        /**
         * 如果这里不加这个判断的话，则recyclerView无法继续往下拖动
         */
        if(target instanceof RecyclerView){
            this.mRecyclerView = (RecyclerView)target;
        }

        if(originalScroll <= mHeaderHeight){
            scrollBy(dx,dy);
            consumed[0] = dx;
            consumed[1] = dy;
        }
        super.onNestedPreScroll(target,dx,dy,consumed,type);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.originalScroll = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 判断是否到达底部
     * @return
     */
    public boolean isInBottom() {
        if (mRecyclerView != null) {
            boolean canScrollVertically = !mRecyclerView.canScrollVertically(1);
            Log.d("isToBottom","isToBottom -- > " + canScrollVertically);
            return canScrollVertically;
        }
        return false;
    }
}
