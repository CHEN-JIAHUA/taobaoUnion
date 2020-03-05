package com.chenjiahua.taobaounion.view;

import com.chenjiahua.taobaounion.base.IBaseCallBack;
import com.chenjiahua.taobaounion.model.domain.Categories;

/**
 * 通知UI更新
 * */
public interface IHomeCallBack extends IBaseCallBack {

    void onCategoriesLoad(Categories categories);

}
