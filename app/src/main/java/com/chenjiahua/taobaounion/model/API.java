package com.chenjiahua.taobaounion.model;

import com.chenjiahua.taobaounion.model.domain.Categories;
import com.chenjiahua.taobaounion.model.domain.HomePageContent;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface API {

    @GET("discovery/categories")
    Call<Categories> getCategories();

    @GET
    Call<HomePageContent> getHomePageContent(@Url String url);
}
