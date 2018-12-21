package com.example.zhangjing20181217;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.zhangjing20181217.activity.DetailActivity;

public class MyBroadcase extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

    if(intent.getAction().equals("cn.jpush.android.intent.NOTIFICATION_OPENED")){
        Intent intent1=new Intent(context, DetailActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent1);
    }
    }
}
