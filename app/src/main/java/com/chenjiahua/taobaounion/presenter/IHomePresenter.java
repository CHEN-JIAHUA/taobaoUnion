package com.chenjiahua.taobaounion.presenter;

import com.chenjiahua.taobaounion.base.IBasePresenter;
import com.chenjiahua.taobaounion.view.IHomeCallBack;

public interface IHomePresenter extends IBasePresenter<IHomeCallBack> {

    /**
     * 获取商品分类
     * */
    void getCategories();

}
