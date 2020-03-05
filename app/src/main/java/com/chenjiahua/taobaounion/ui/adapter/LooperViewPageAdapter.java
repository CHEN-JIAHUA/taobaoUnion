package com.chenjiahua.taobaounion.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.chenjiahua.taobaounion.model.domain.HomePageContent;
import com.chenjiahua.taobaounion.utils.LogUtils;
import com.chenjiahua.taobaounion.utils.UrlUtils;

import java.util.ArrayList;
import java.util.List;

public class LooperViewPageAdapter extends PagerAdapter {

    List<HomePageContent.DataBean> looperContents = new ArrayList<>();
    private ImageView iv;

    public int getLooperDataSize(){
        return looperContents.size();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        return super.instantiateItem(container, position);

        int realPosition = position % looperContents.size();
        HomePageContent.DataBean dataBean = looperContents.get(realPosition);
        int height = container.getMeasuredHeight();
        int width = container.getMeasuredWidth();
        int containerSize = (height > width ? height : width) / 2;
        String urlPath = UrlUtils.getSetUrlPath(dataBean.getPict_url());

        String picSizeUrl = UrlUtils.getPicSizeUrl(urlPath, containerSize);
//        LogUtils.d(LooperViewPageAdapter.class,"looperPic ====== > > > " + picSizeUrl);
        iv = new ImageView(container.getContext());
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(layoutParams);
        iv.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(container.getContext()).load(picSizeUrl).into(iv);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }



    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setData(List<HomePageContent.DataBean> contents) {
            looperContents.clear();
            looperContents.addAll(contents);
            notifyDataSetChanged();
    }

}
