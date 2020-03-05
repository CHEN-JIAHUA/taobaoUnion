package com.chenjiahua.taobaounion.ui.fragment;

import android.view.View;

import com.chenjiahua.taobaounion.R;
import com.chenjiahua.taobaounion.base.BaseFragment;

public class ChoiceFragment extends BaseFragment {


    @Override
    protected int getResRootId() {
        return R.layout.fragment_choice;
    }

    @Override
    protected void initView(View rootView) {
//        super.initView(rootView);
    setUpStatus(State.SUCCESS);
    }
}
