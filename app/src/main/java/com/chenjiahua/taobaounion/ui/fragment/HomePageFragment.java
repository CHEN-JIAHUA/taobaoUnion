package com.chenjiahua.taobaounion.ui.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chenjiahua.taobaounion.R;
import com.chenjiahua.taobaounion.base.BaseFragment;
import com.lcodecore.tkrefreshlayout.views.TbNestedScrollView;
import com.chenjiahua.taobaounion.model.domain.Categories;
import com.chenjiahua.taobaounion.model.domain.HomePageContent;
import com.chenjiahua.taobaounion.presenter.impl.HomePageCategoryPresenterImpl;
import com.chenjiahua.taobaounion.ui.adapter.HomePageContentAdapter;
import com.chenjiahua.taobaounion.ui.adapter.LooperViewPageAdapter;
import com.chenjiahua.taobaounion.utils.Constants;
import com.chenjiahua.taobaounion.utils.SizeUtils;
import com.chenjiahua.taobaounion.utils.ToastUtils;
import com.chenjiahua.taobaounion.view.ICategoryContentCallBack;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;


import java.util.List;

import butterknife.BindView;

public class HomePageFragment extends BaseFragment implements ICategoryContentCallBack {

    @BindView(R.id.home_page_parent)
    public LinearLayout homePageParent;

    @BindView(R.id.home_page_content_view)
    public RecyclerView recyclerView;

    @BindView(R.id.looperView)
    public ViewPager looperView;

    @BindView(R.id.categories_page_title)
    public TextView categoriesPageTile;

    @BindView(R.id.looper_point_container)
    public LinearLayout looperPointContainer;

    @BindView(R.id.home_page_refresh)
    public TwinklingRefreshLayout homePageRefresh;

    @BindView(R.id.home_page_nested_scroller)
    public TbNestedScrollView homePageNestedScoller;

    @BindView(R.id.home_page_header_container)
    public LinearLayout homePageHeaderContainer;

    private  HomePageCategoryPresenterImpl homePageCategoryPresenter;
    private int materialId;
    private HomePageContentAdapter mAdapter;
    private LooperViewPageAdapter mLooperViewPageAdapter;

    public static HomePageFragment newInstance(Categories.DataBean category){
        HomePageFragment homePageFragment = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_HOME_PAGE_TITLE,category.getTitle());
        bundle.putInt(Constants.KEY_HOME_PAGE_MATERIAL_ID,category.getId());
        homePageFragment.setArguments(bundle);  // 为HomePageFragment 提供 构造方法的参数 ，这个参数有Bundle携带
                                                // 如果这个fragment加入到fragmentManger，则无法调用
        return homePageFragment;
    }

    //home_page 部分的fragment
    @Override
    protected int getResRootId() {
        return R.layout.fragment_home_page;
//        return R.layout.testlayout;
    }

    @Override
    protected void initView(View rootView) {
//        super.initView(rootView);
//
        //设置布局管理器  l
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                    outRect.top = 4;
                    outRect.bottom = 4;
            }
        });
//        //创建适配器
        mAdapter = new HomePageContentAdapter();
//        //设置适配器
        recyclerView.setAdapter(mAdapter);
//       setUpStatus(State.SUCCESS);
        //创建轮播图适配器
        mLooperViewPageAdapter = new LooperViewPageAdapter();
        //设置适配器
        looperView.setAdapter(mLooperViewPageAdapter);
        //设置相关属性内容
        homePageRefresh.setEnableRefresh(false);
        homePageRefresh.setAutoLoadMore(true);
    }

    @Override
    protected void initListener() {
//        super.initListener();
        homePageParent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int headerHeight = homePageHeaderContainer.getMeasuredHeight();
//                LogUtils.d(HomePageFragment.class,"headerHeight ==== >  " + headerHeight);
                homePageNestedScoller.setHeaderHeight(headerHeight);
                int measuredHeight = homePageParent.getMeasuredHeight();
//                LogUtils.d(HomePageFragment.class,"measureHeight ----> " + measuredHeight);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) recyclerView.getLayoutParams();
                layoutParams.height = measuredHeight;
                recyclerView.setLayoutParams(layoutParams);
                if (measuredHeight!=0) {
                    homePageParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

        //设置轮播监听
        looperView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(mLooperViewPageAdapter.getLooperDataSize() != 0 || mLooperViewPageAdapter == null){
                    int targetPosition = position % mLooperViewPageAdapter.getLooperDataSize();
//               切换指示器
                    updateLooperIndicator(targetPosition);
                }
//                LogUtils.d(HomePageFragment.class,"onPageSelected ========>>>>> " + mLooperViewPageAdapter.getLooperDataSize());

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       homePageRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
           @Override
           public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
               //TODO:实现列表下拉加载更多的数据
//               super.onLoadMore(refreshLayout);
               homePageCategoryPresenter.loadMoreContent(materialId);

           }
       });
    }

    /**
     * 切换指示器
     * @param targetPoint
     */
    private void updateLooperIndicator(int targetPoint) {
        for (int i = 0; i < looperPointContainer.getChildCount(); i++) {
            View point = looperPointContainer.getChildAt(i);
            if(i == targetPoint){
                point.setBackgroundResource(R.drawable.shape_indicate_selected_point);
            }else {
                point.setBackgroundResource(R.drawable.shape_indicate_normal_point);
            }
        }

    }

    @Override
    protected void loadData() {
//        super.loadData();
//        复写loadData数据

        Bundle bundle = getArguments();
        String title = bundle.getString(Constants.KEY_HOME_PAGE_TITLE);
        materialId = bundle.getInt(Constants.KEY_HOME_PAGE_MATERIAL_ID);
        //TODO:加载数据
//        LogUtils.d(HomePageFragment.class,"title -- > " + title);
//        LogUtils.d(HomePageFragment.class,"materialId -- > " + materialId) ;
        if ( homePageCategoryPresenter!=null) {
            homePageCategoryPresenter.getContentByCategoryId(materialId);
            //TODO:页面title设置
            categoriesPageTile.setText(title);
        }
    }

    /**
     * 加载Presenter
     */
    @Override
    protected void initPresenter() {

//        super.initPresenter();
        homePageCategoryPresenter = HomePageCategoryPresenterImpl.getsInstance();
        homePageCategoryPresenter.registerViewCallBack(this);
    }

    @Override
    public void onGetCategoryContent(List<HomePageContent.DataBean> homePageContentList) {
        //数据列表加载
        //TODO: 更新UI
        setUpStatus(State.SUCCESS);
        mAdapter.setData(homePageContentList);

    }

    @Override
    public void onLoading() {

        setUpStatus(State.LOADING);
    }

    @Override
    public void onError() {
        setUpStatus(State.ERROR);
    }

    @Override
    public void onEmpty() {
        setUpStatus(State.EMPTY);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.showToast("网络异常，请检查后再加载");
//        homePageRefresh.finishLoadmore();

    }

    @Override
    public int getCategoryId() {
        return materialId;
    }

    @Override
    public void onLoadMoreEmpty() {
        ToastUtils.showToast("没有更多数据...");
    }

    @Override
    public void onLoadMoreLoaded(List<HomePageContent.DataBean> contents) {
//        添加数据到适配器的底部
        mAdapter.addData(contents);
        if (homePageRefresh != null) {
            homePageRefresh.finishLoadmore();
//            Toast.makeText(getContext(),"加载了" + contents.size() + "条数据",Toast.LENGTH_SHORT).show();
            ToastUtils.showToast("加载了" + contents.size() + "条数据");
        }

    }

    @Override
    public void onLooperListLoaded(List<HomePageContent.DataBean> contents) {
//        LogUtils.d(HomePageFragment.class,"looperData Size -- > " + contents.size());
        mLooperViewPageAdapter.setData(contents);
        looperPointContainer.removeAllViews();
        int dx = (Integer.MAX_VALUE / 2) % contents.size();
        int targetCenterPosition = (Integer.MAX_VALUE / 2) - dx;
        //设置中间值
        looperView.setCurrentItem(targetCenterPosition);
//        LogUtils.d(HomePageFragment.class,"url pic -- > " + contents.get(0).getPict_url());
        //TODO：轮播图 数据来时添加点
        for (int i = 0; i < contents.size(); i++) {
            View mPoint = new View(getContext());
            int size = SizeUtils.dip2px(getContext(), 8);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size,size);
            layoutParams.leftMargin = SizeUtils.dip2px(getContext(),5);
            layoutParams.rightMargin = SizeUtils.dip2px(getContext(),5);
            mPoint.setLayoutParams(layoutParams);
            looperPointContainer.addView(mPoint);
        }



    }

    @Override
    protected void release() {
//        super.release();
        //destroy的时候释放资源
        if (homePageCategoryPresenter !=null) {
            homePageCategoryPresenter.unRegisterViewCallBack(this);
        }
    }

}
