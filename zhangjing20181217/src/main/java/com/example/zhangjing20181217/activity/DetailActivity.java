package com.example.zhangjing20181217.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zhangjing20181217.R;
import com.example.zhangjing20181217.adapter.ViewPagerAdapter;
import com.example.zhangjing20181217.bean.DetailBean;
import com.example.zhangjing20181217.presenter.IPresenterImpl;
import com.example.zhangjing20181217.utils.Path;
import com.example.zhangjing20181217.view.IView;

import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity implements IView{
    private IPresenterImpl iPresenter;
    private ViewPager viewPager;
    private TextView textView_title;
    private TextView textView;
    private String[] split;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //得到数据
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 2);
        iPresenter=new IPresenterImpl(this);
        initView(savedInstanceState);
        Map<String,String> params=new HashMap<>();
        params.put("pid",pid+"");
        iPresenter.startRequest(Path.path_detail,params,DetailBean.class);

    }

    private void initView(Bundle savedInstanceState) {
        viewPager = findViewById(R.id.viewPager);
        textView_title = findViewById(R.id.text_detail_title);
        textView = findViewById(R.id.text_detail_price);
    }

    @Override
    public void requestSuccess(Object data) {
        DetailBean bean= (DetailBean) data;
        DetailBean.DataBean data1 = bean.getData();
        textView.setText(data1.getPrice()+"");
        textView_title.setText(data1.getTitle());
        String images = data1.getImages();
        split = images.split("\\|");
        adapter=new ViewPagerAdapter(split,DetailActivity.this);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detchView();
    }
}
