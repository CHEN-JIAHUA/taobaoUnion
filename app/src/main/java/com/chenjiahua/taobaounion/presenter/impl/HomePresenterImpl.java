package com.chenjiahua.taobaounion.presenter.impl;

import com.chenjiahua.taobaounion.model.API;
import com.chenjiahua.taobaounion.model.domain.Categories;
import com.chenjiahua.taobaounion.presenter.IHomePresenter;
import com.chenjiahua.taobaounion.utils.LogUtils;
import com.chenjiahua.taobaounion.utils.RetrofitManger;
import com.chenjiahua.taobaounion.view.IHomeCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {
    private IHomeCallBack callBack = null;

    @Override
    public void getCategories() {

        //加载分类的数据
        if (callBack!=null) {
            callBack.onLoading();
        }
        Retrofit retrofit = RetrofitManger.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        api.getCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                int code = response.code();
//                Log.d("taobao","code == >" + response.code());
                LogUtils.d(HomePresenterImpl.class,"reponseCode == > " + code);
                if(code == HttpURLConnection.HTTP_OK){
                    Categories categories = response.body();
                    if(callBack != null){
                        if (categories == null || categories.getData().size() == 0) {
                            callBack.onEmpty();
                    }
                    LogUtils.d(HomePresenterImpl.class,"result == > " + categories.getData().toString());
//                    Log.d("taobao","result -- > " + response.body().toString());
                        callBack.onCategoriesLoad(categories);  //将数据更新到UI去
                    }
                }else{
                    //请求失败
                    if (callBack!=null) {
                        callBack.onError();
                    }
                }
             }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                //请求失败的结果
                LogUtils.d(HomePresenterImpl.class,"OnFault -- > " + t.toString());
                if (callBack!=null) {
                    callBack.onError();
                }
            }
        });
    }

    @Override
    public void registerViewCallBack(IHomeCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void unRegisterViewCallBack(IHomeCallBack callBack) {
        this.callBack = null;
    }
}
