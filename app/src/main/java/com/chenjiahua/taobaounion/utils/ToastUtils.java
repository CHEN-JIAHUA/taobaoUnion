package com.chenjiahua.taobaounion.utils;

import android.widget.Toast;

import com.chenjiahua.taobaounion.base.BaseApplication;


public class ToastUtils {

    private static Toast toast;

    public static void showToast(String tips){
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getAppContext(), tips, Toast.LENGTH_SHORT);
            toast.show();
        }else {
           toast.setText(tips);
        }

    }
}
