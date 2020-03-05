package com.chenjiahua.taobaounion.presenter.impl;

import com.chenjiahua.taobaounion.model.API;
import com.chenjiahua.taobaounion.model.domain.HomePageContent;
import com.chenjiahua.taobaounion.presenter.ICategoryPagePresenter;
import com.chenjiahua.taobaounion.utils.LogUtils;
import com.chenjiahua.taobaounion.utils.RetrofitManger;
import com.chenjiahua.taobaounion.utils.UrlUtils;
import com.chenjiahua.taobaounion.view.ICategoryContentCallBack;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePageCategoryPresenterImpl implements ICategoryPagePresenter {


    private Map<Integer,Integer> pagesInfo = new HashMap<>();
    List<ICategoryContentCallBack> callBackList = new ArrayList<>();
    public static final int DEFAULT_PAGE = 1;
    private Integer currentPage;

    //私有的构造方法
    private  HomePageCategoryPresenterImpl(){ }

    private static HomePageCategoryPresenterImpl sInstance = null;

    public static  HomePageCategoryPresenterImpl getsInstance(){
        if (sInstance == null) {
            sInstance = new HomePageCategoryPresenterImpl();
        }
        return sInstance;
    }

    @Override
    public void getContentByCategoryId(int categoryId) {
        //根据商品分类id加载内容
        for (ICategoryContentCallBack callBack : callBackList) {
            if(callBack.getCategoryId() == categoryId)
            callBack.onLoading();
        }

        Integer currentPage = pagesInfo.get(categoryId);
        if(currentPage == null){
            currentPage = DEFAULT_PAGE;
            pagesInfo.put(categoryId,currentPage);
        }
        Call<HomePageContent> task = createTask(categoryId, currentPage);
        task.enqueue(new Callback<HomePageContent>() {
            @Override
            public void onResponse(Call<HomePageContent> call, Response<HomePageContent> response) {
                int code = response.code();
//                LogUtils.d(HomePageCategoryPresenterImpl.class,"response code == > " + code);
                if(code == HttpURLConnection.HTTP_OK){
                    HomePageContent body = response.body();
//                    LogUtils.d(HomePageCategoryPresenterImpl.class,"result -- > " + body);
                    handleHomePageContentResult(body,categoryId);
                }else {
                    handleHomePageContentError(categoryId);
                }
            }

            @Override
            public void onFailure(Call<HomePageContent> call, Throwable t) {
//                LogUtils.e(HomePageCategoryPresenterImpl.class,"OnFailure -- > " + t.toString());
                handleHomePageContentError(categoryId);
            }
        });
    }

    private Call<HomePageContent> createTask(int categoryId, Integer currentPage) {
        Retrofit retrofit = RetrofitManger.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        String homePageUrl = UrlUtils.createHomePageUrl(categoryId, currentPage);
        Call<HomePageContent> task = api.getHomePageContent(homePageUrl);
//        LogUtils.d(HomePageCategoryPresenterImpl.class,"homePageUrl -- > " + homePageUrl);
        return task;
    }

    private void handleHomePageContentError(int categoryId) {
        for (ICategoryContentCallBack callBack : callBackList) {
            if(callBack.getCategoryId() == categoryId)
            callBack.onError();
        }
    }

    private void handleHomePageContentResult(HomePageContent content, int categoryId) {
        //通知UI层更新数据
        List<HomePageContent.DataBean> data = content.getData();
        for (ICategoryContentCallBack callBack : callBackList) {
            if (callBack.getCategoryId() == categoryId) {
                if(content == null || data.size() == 0){
                    callBack.onEmpty();
                }else {
                    List<HomePageContent.DataBean> looperDataList = data.subList(data.size() - 5, data.size());
                    callBack.onLooperListLoaded(looperDataList);
                    callBack.onGetCategoryContent(data);
                }
            }
            }
    }

    @Override
    public void loadMoreContent(int categoryId) {
        //加载更多数据
        //1.拿到当前页面
        currentPage = pagesInfo.get(categoryId);
        if (currentPage == null) {
            currentPage = 1;
        }
        currentPage++;
        Call<HomePageContent> task = createTask(categoryId, currentPage);
        task.enqueue(new Callback<HomePageContent>() {
            @Override
            public void onResponse(Call<HomePageContent> call, Response<HomePageContent> response) {
                //成功的结果
                int code = response.code();
                LogUtils.d(HomePageCategoryPresenterImpl.class,"resCode -- > " + code);
               if(code == HttpURLConnection.HTTP_OK){
                   HomePageContent responseData = response.body();
                   LogUtils.d(HomePageCategoryPresenterImpl.class,"responseData MoreData ===== >>>" + responseData);
                   handleLoadMoreData(responseData,categoryId);
               }else {
                   //todo:失败
               }

            }

            @Override
            public void onFailure(Call<HomePageContent> call, Throwable t) {
//              Failure 的结果
//                LogUtils.e(HomePageCategoryPresenterImpl.class,"OnFailure -- > " + t.toString());
                handleLoadMoreError(categoryId);
            }
        });
        //2.页码++
        //3.加载数据
        //4.处理数据结果
    }

    private void handleLoadMoreData(HomePageContent responseData, int categoryId) {
        for (ICategoryContentCallBack callBack : callBackList) {
            if (callBack.getCategoryId() == categoryId) {
                if (responseData.getData() == null || responseData.getData().size() == 0) {
                   callBack.onLoadMoreEmpty();
                }else {
                    callBack.onLoadMoreLoaded(responseData.getData());
                }

            }
        }
    }

    private void handleLoadMoreError(int categoryId) {
        currentPage--;
        pagesInfo.put(categoryId,currentPage);
        for (ICategoryContentCallBack callBack : callBackList) {
            if (callBack.getCategoryId() == categoryId) {
                callBack.onLoadMoreError();
            }
        }
    }

    @Override
    public void reLoadContent(int categoryId) {

    }



    @Override
    public void registerViewCallBack(ICategoryContentCallBack callBack) {
        if(!this.callBackList.contains(callBack)){
             callBackList.add(callBack);
        }

    }

    @Override
    public void unRegisterViewCallBack(ICategoryContentCallBack callBack) {
        this.callBackList.remove(callBack);
    }
}
