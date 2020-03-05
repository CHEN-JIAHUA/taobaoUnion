package com.chenjiahua.taobaounion.ui.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chenjiahua.taobaounion.R;
import com.chenjiahua.taobaounion.model.domain.HomePageContent;
import com.chenjiahua.taobaounion.utils.LogUtils;
import com.chenjiahua.taobaounion.utils.UrlUtils;
import com.lcodecore.tkrefreshlayout.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePageContentAdapter extends RecyclerView.Adapter<HomePageContentAdapter.InnerHolder> {

    //创建这个集合来保存数据
    List<HomePageContent.DataBean> data = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_content_view, parent, false);
//        LogUtils.d(HomePageContentAdapter.class,"加载出主页内容数据");
        return new InnerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HomePageContent.DataBean dataBean = data.get(position);
        holder.setData(dataBean);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setData(List<HomePageContent.DataBean> homePageContentList) {
        data.clear();
        data.addAll(homePageContentList);
        notifyDataSetChanged();
    }

    public void addData(List<HomePageContent.DataBean> contents) {
        int originalSize = data.size();
        data.addAll(contents);
        notifyItemRangeChanged(originalSize,contents.size());
    }

    class InnerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.goods_cover)
        ImageView cover;

        @BindView(R.id.goods_title_info)
        TextView title;

        @BindView(R.id.goods_off_price)
        TextView goodsOffPrice;

        @BindView(R.id.result_price)
        TextView resultPrice;

        @BindView(R.id.goods_original_price)
        TextView originalPrice;

        @BindView(R.id.goods_volume)
        TextView goodsVolume;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(HomePageContent.DataBean dataBean) {
            title.setText(dataBean.getTitle ());
            ViewGroup.LayoutParams layoutParams = cover.getLayoutParams();
            int height = layoutParams.height;
            int width = layoutParams.width;
            int coverSize = (height > width ? height : width) / 2;
            String urlPath = UrlUtils.getSetUrlPath(dataBean.getPict_url());
            LogUtils.d(HomePageContentAdapter.class,"urlPath --- >" + urlPath);
            String sizeUrl = UrlUtils.getPicSizeUrl(urlPath, coverSize);
//            LogUtils.d(HomePageContentAdapter.class,"sizeUrl ======= >>>>> " + sizeUrl );
            Glide.with(itemView.getContext()).load(sizeUrl).into(cover);
            long couponAmount = dataBean.getCoupon_amount();
            String price = dataBean.getZk_final_price();
            float calPrice = Float.parseFloat(price) - couponAmount;
            originalPrice.setText(String.format(itemView.getResources().getString(R.string.text_original_price),price));
            originalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            resultPrice.setText(String.format("%.2f",calPrice));
            goodsOffPrice.setText(String.format(itemView.getResources().getString(R.string.text_goods_off_price), couponAmount));
            int volume = (int) dataBean.getVolume();
//            LogUtils.d(HomePageContentAdapter.class,"goods_volume -- >" + l);
            goodsVolume.setText(String.format(itemView.getResources().getString(R.string.text_goods_volume),volume));
        }
    }

}
