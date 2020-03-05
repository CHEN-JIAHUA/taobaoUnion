package com.chenjiahua.taobaounion.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.chenjiahua.taobaounion.R;
import com.chenjiahua.taobaounion.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    private State currentState = null;
    private View successView ;
    private View loadingView ;
    private View errorView ;
    private View emptyView ;

    public enum State {
        NONE,LOADING,SUCCESS,ERROR,EMPTY
    }

    private Unbinder mBind;
    private FrameLayout blankFrameLayout;


    @OnClick(R.id.linearLayout_network_error)
    public void retryNetWork(){
        //点击刷新网络、
//        Toast.makeText(getContext(),"刷新",Toast.LENGTH_SHORT).show();
        onRetryNetWork();
    }

    protected void onRetryNetWork(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = loadRootView(inflater,container);
        blankFrameLayout = rootView.findViewById(R.id.base_container);
        loadStatusView(inflater,container);
        mBind = ButterKnife.bind(this, rootView);
        initView(rootView);
        initPresenter();
        initListener();
        loadData();


        return rootView;

    }

    /**
     * 子类需要的话就复写
     */
    protected void initListener() {
    }

    protected View loadRootView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_base_layout, container, false);
    }

    /**
        * 加载各种状态下的View
        * */
    private void loadStatusView(LayoutInflater inflater, ViewGroup container) {
        //成功的View
        successView = loadSuccessView(inflater, container);
        blankFrameLayout.addView(successView);

        //设置一个加载中的LoadingView
        this.loadingView = loadLoadingView(inflater, container);
        blankFrameLayout.addView(loadingView);
        //错误页面
        this.errorView = loadErrorView(inflater,container);
        blankFrameLayout.addView(errorView);
        //空白页面
        this.emptyView = loadEmptyView(inflater, container);
        blankFrameLayout.addView(emptyView);

        setUpStatus(State.NONE);

    }

    protected View loadEmptyView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_empty_layout,container,false);
    }

    protected View loadErrorView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_error_layout,container,false);
    }

    /**
     * 加载loading界面
     * @param inflater
     * @param container
     * @return
     */

    protected View loadLoadingView(LayoutInflater inflater, ViewGroup container) {

        return inflater.inflate(R.layout.fragment_loading_view,container,false);
    }

    protected View loadSuccessView(LayoutInflater inflater, ViewGroup container) {
        int resRootId = getResRootId();
//        LogUtils.d(BaseFragment.class,"resRootId == > " + resRootId);
        return inflater.inflate(resRootId, container, false);
    }
    protected abstract int getResRootId();


    /**
     * 子类通过这个方法来切换状态页面即可
     * @param status
     */
    public void setUpStatus(State status){
        //        if(currentStates == State.SUCCESS){
//            successView.setVisibility(View.VISIBLE);
//        }else {
//            successView.setVisibility(View.GONE);
//        }
//        if(currentStates == State.LOADING){
//            loadLoadingView.setVisibility(View.VISIBLE);
//        }else {
//            loadLoadingView.setVisibility(View.GONE);
//        }
        this.currentState = status;
        successView.setVisibility(status == State.SUCCESS ? View.VISIBLE : View.GONE);
        loadingView.setVisibility(status == State.LOADING ? View.VISIBLE : View.GONE);
        errorView.setVisibility(status == State.ERROR ? View.VISIBLE : View.GONE);
        emptyView.setVisibility(status == State.EMPTY ? View.VISIBLE : View.GONE);
    }


    protected void initView(View rootView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mBind.unbind();
        if (mBind!=null) {
            mBind.unbind();
        }
        release();
    }

    protected void release() {
        //释放资源

    }

    protected void initPresenter() {
        //创建Presenter
    }

    protected void loadData() {
    //加载数据


    }




}
