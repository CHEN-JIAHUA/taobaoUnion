package com.chenjiahua.taobaounion.ui.fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.chenjiahua.taobaounion.R;
import com.chenjiahua.taobaounion.base.BaseFragment;
import com.chenjiahua.taobaounion.model.domain.Categories;
import com.chenjiahua.taobaounion.presenter.impl.HomePresenterImpl;
import com.chenjiahua.taobaounion.ui.adapter.HomePageAdapter;
import com.chenjiahua.taobaounion.view.IHomeCallBack;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends BaseFragment implements IHomeCallBack {

    private HomePresenterImpl mHomePresenter;
    @BindView(R.id.home_indicator)
    public TabLayout mTabLayout;

    @BindView(R.id.home_page)
    public ViewPager mViewPager;
    //    private HomePageAdapter homePageAdapter;
    private HomePageAdapter mHomePageAdapter;

    @Override
    protected int getResRootId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void loadData() {
        //加载数据
//        super.loadData();
//        setUpStatus(State.LOADING);
        mHomePresenter.getCategories();


    }

    @Override
    protected void initView(View rootView) {
//        super.initView(rootView);
        mTabLayout.setupWithViewPager(mViewPager);
        //给ViewPage设置适配器
        mHomePageAdapter = new HomePageAdapter(getChildFragmentManager());              //获取fragmentManger 在fragment中是getChildFragmentManger
        mViewPager.setAdapter(mHomePageAdapter);                                        // activity --getSupportFragmentManger
    }



    @Override
    protected void initPresenter() {
        //创建Presenter
//        super.initPresenter();
        mHomePresenter = new HomePresenterImpl();
        mHomePresenter.registerViewCallBack(this);
    }

    @Override
    protected void onRetryNetWork() {
        //重新刷新加载数据
        if (mHomePresenter != null) {
            mHomePresenter.getCategories();
        }
    }

    @Override
    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
//        return super.loadRootView(inflater, container);
        return inflater.inflate(R.layout.fragment_base_home_layout,container,false);
    }

    @Override
    protected void release() {
//        super.release();
        if (mHomePresenter != null) {
            mHomePresenter.unRegisterViewCallBack(this);
        }
    }

    @Override
    public void onCategoriesLoad(Categories categories) {

        //
//        if (mHomePageAdapter != null) {
//            mHomePageAdapter.setCategories(categories);
//            LogUtils.d(this.getClass(), "categories -- > " + categories.getData().toString());
//            if (categories.getData().size() == 0) {
//                setUpStatus(State.EMPTY);
//            } else {
//                setUpStatus(State.SUCCESS);
//            }
//        }

        setUpStatus(State.SUCCESS);
        if (mHomePageAdapter!=null) {
            mHomePageAdapter.setCategories(categories);
//            这里设置setOffscreenPageLimit -- > 加载ViewPage多少页 --> 默认是2 页 ，也可以同时加载
//            mViewPager.setOffscreenPageLimit(categories.getData().size());
        }
    }

    @Override
    public void onError() {
        setUpStatus(State.ERROR);
//        onRetryNetWork();
    }

    @Override
    public void onLoading() {
        setUpStatus(State.LOADING);
    }

    @Override
    public void onEmpty() {
        setUpStatus(State.EMPTY);
    }
}
