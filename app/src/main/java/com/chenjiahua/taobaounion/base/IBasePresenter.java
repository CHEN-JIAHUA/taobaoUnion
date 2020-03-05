package com.chenjiahua.taobaounion.base;


public interface IBasePresenter<T> {


    /**
     **/
    void registerViewCallBack(T callBack);

    /**
     *
     * */
    void unRegisterViewCallBack(T callBack);

}
