package com.example.zhangjing20181217.utils;

public interface MCallBack {
    //请求成功
    void onSuccess(Object object);
    //请求失败
    void onFail(Exception e);
}
