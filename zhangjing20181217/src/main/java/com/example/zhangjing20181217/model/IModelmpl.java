package com.example.zhangjing20181217.model;

import com.example.zhangjing20181217.utils.MCallBack;
import com.example.zhangjing20181217.utils.MyCallBack;
import com.example.zhangjing20181217.utils.OkHttpUtils;

import java.util.Map;

public class IModelmpl implements IModel {
    @Override
    public void getData(String path, Map<String, String> params, Class clazz, final MyCallBack myCallBack) {
        OkHttpUtils.getInstance().postRequest(path, params, clazz, new MCallBack() {
            @Override
            public void onSuccess(Object object) {
                myCallBack.requestData(object);
            }

            @Override
            public void onFail(Exception e) {
            myCallBack.requestData(e);
            }
        });
    }
}
