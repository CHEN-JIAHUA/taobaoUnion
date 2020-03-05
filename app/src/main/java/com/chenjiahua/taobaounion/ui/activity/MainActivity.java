package com.chenjiahua.taobaounion.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.chenjiahua.taobaounion.R;
import com.chenjiahua.taobaounion.base.BaseFragment;
import com.chenjiahua.taobaounion.ui.fragment.ChoiceFragment;
import com.chenjiahua.taobaounion.ui.fragment.HomeFragment;
import com.chenjiahua.taobaounion.ui.fragment.RedPacketFragment;
import com.chenjiahua.taobaounion.ui.fragment.SearchFragment;
import com.chenjiahua.taobaounion.utils.LogUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.bottomnavigationbar)
    public BottomNavigationView mBtNavigation;
    public static final String TAG = "taobao";
    private HomeFragment homeFragment;
    private ChoiceFragment choiceFragment;
    private RedPacketFragment redPacketFragment;
    private SearchFragment searchFragment;
    private Unbinder mBind;

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);
//        initView();
        initFragment();
        initPresenter();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBind!=null) {
            mBind.unbind();
        }
    }

    private void initFragment() {
        homeFragment  = new HomeFragment();
        choiceFragment = new ChoiceFragment();
        redPacketFragment = new RedPacketFragment();
        searchFragment = new SearchFragment();
        switchFragment(homeFragment);
    }


    private void initListener() {
    //设置监听
        mBtNavigation.setOnNavigationItemSelectedListener(item -> {
            Log.d(TAG,"title == >" + item.getTitle());
            int itemId = item.getItemId();
            if(itemId == R.id.item_home){
                switchFragment(homeFragment);
//                    Log.d(TAG,"提示 -- > 切换到主页");
//                LogUtils.d(MainActivity.class,"提示 -- > 切换到主页");
            }else if (itemId == R.id.item_choice){
//                    Log.d(TAG,"提示 -- > 切换到精选");
                switchFragment(choiceFragment);
//                LogUtils.d(MainActivity.class,"提示 -- > 切换到精选");
            }else if(itemId == R.id.item_redpacket){
//                    Log.d(TAG,"提示 -- > 切换到特惠");
                switchFragment(redPacketFragment);
//                LogUtils.d(MainActivity.class,"提示 -- > 切换到特惠");
            }else if (itemId == R.id.item_search){
                switchFragment(searchFragment);
//                    Log.d(TAG,"提示 -- > 切换到搜索");
//                LogUtils.d(MainActivity.class,"提示 -- > 切换到搜索");
            }
            return true;
        });
    }

    private void switchFragment(BaseFragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,targetFragment);
        transaction.commit();
    }

    private void initPresenter() {
    }



}
