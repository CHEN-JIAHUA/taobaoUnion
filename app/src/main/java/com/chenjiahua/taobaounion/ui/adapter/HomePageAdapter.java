package com.chenjiahua.taobaounion.ui.adapter;

import android.icu.text.CaseMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.chenjiahua.taobaounion.model.domain.Categories;
import com.chenjiahua.taobaounion.ui.fragment.HomePageFragment;

import java.util.ArrayList;
import java.util.List;

public class HomePageAdapter extends FragmentPagerAdapter {

    private List<Categories.DataBean> categoriesDataList = new ArrayList<>();

    public HomePageAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

//        return super.getPageTitle(position);
        return categoriesDataList.get(position).getTitle();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Categories.DataBean categoryDataBean = categoriesDataList.get(position);
        HomePageFragment homePageFragment = HomePageFragment.newInstance(categoryDataBean);

        return homePageFragment;
    }

    @Override
    public int getCount() {
        return categoriesDataList.size();
    }

    public void setCategories(Categories categories) {
//先把列清空
        categoriesDataList.clear();

        List<Categories.DataBean> categoriesData = categories.getData();
        categoriesDataList.addAll(categoriesData);
        notifyDataSetChanged();
    }
}
