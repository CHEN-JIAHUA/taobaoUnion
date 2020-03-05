package com.chenjiahua.taobaounion.presenter;


import com.chenjiahua.taobaounion.base.IBasePresenter;
import com.chenjiahua.taobaounion.view.ICategoryContentCallBack;

public interface ICategoryPagePresenter extends IBasePresenter<ICategoryContentCallBack> {


    void getContentByCategoryId(int categoryId);

    /**
     * 加载更多的主页数据
     * @param categoryId
     */
    void loadMoreContent(int categoryId);

    /**
     * 重新加载数据
     */
    void reLoadContent(int categoryId);



}
