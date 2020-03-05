package com.chenjiahua.taobaounion.view;

import com.chenjiahua.taobaounion.base.IBaseCallBack;
import com.chenjiahua.taobaounion.model.domain.HomePageContent;

import java.util.List;

public interface ICategoryContentCallBack extends IBaseCallBack {
    /**
     * 数据加载回来
     * @param homePageContentList
     */
    void onGetCategoryContent(List<HomePageContent.DataBean> homePageContentList);

    /**
     * 加载更多数据时出现请求异常
     */
    void onLoadMoreError();

    /**
     *
     * @return int
     */
    int getCategoryId();

    /**
     * 加载更多数据时数据为空
     */
    void onLoadMoreEmpty();

    /**
     *
     * @param contents
     */
   void onLoadMoreLoaded(List<HomePageContent.DataBean> contents);

    /**
     *
     * @param contents
     */
   void onLooperListLoaded(List<HomePageContent.DataBean> contents);



}
