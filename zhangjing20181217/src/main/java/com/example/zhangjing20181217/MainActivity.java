package com.example.zhangjing20181217;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.zhangjing20181217.activity.DetailActivity;
import com.example.zhangjing20181217.adapter.RecyclerAdapter;
import com.example.zhangjing20181217.bean.GoodsBean;
import com.example.zhangjing20181217.presenter.IPresenterImpl;
import com.example.zhangjing20181217.utils.Path;
import com.example.zhangjing20181217.view.IView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.VISIBLE;
import static android.view.View.X;

public class MainActivity extends AppCompatActivity implements IView{
    private IPresenterImpl iPresenter;
    private ImageView imageView_search,imageView_change;
    private EditText editText_goods;
    private XRecyclerView xRecyclerView_list,xRecyclerView_grid;
    private RecyclerAdapter adapter;
    private int mPage;
    private final int lineCount=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);

        //得到LinearLayoutManager
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(OrientationHelper.VERTICAL);//设置方向
        xRecyclerView_list.setLayoutManager(manager);

        GridLayoutManager manager1=new GridLayoutManager(MainActivity.this,lineCount);
        manager1.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView_grid.setLayoutManager(manager1);

        //切换
        imageChange();
        adapter=new RecyclerAdapter(MainActivity.this);
        xRecyclerView_list.setAdapter(adapter);
        xRecyclerView_grid.setAdapter(adapter);
        //允许刷新加载
        xRecyclerView_list.setPullRefreshEnabled(true);
        xRecyclerView_list.setLoadingMoreEnabled(true);
        mPage=1;
        xRecyclerView_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getInfo();
            }

            @Override
            public void onLoadMore() {
                getInfo();
            }
        });
        getInfo();
        //长按删除
        adapter.setOnlongItemClickListener(new RecyclerAdapter.longItemClickListener() {
            @Override
            public void delData(int position) {
                adapter.delItem(position);
            }
        });
        //跳转到详情页面
        adapter.setOnItemClickListener(new RecyclerAdapter.onItemClickListener() {
            @Override
            public void getChange(int poistion) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("pid",poistion);
                startActivity(intent);
            }
        });

    }
    //视图切换
    private void imageChange() {
       imageView_change.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(xRecyclerView_list.getVisibility()==VISIBLE){
                   xRecyclerView_grid.setVisibility(VISIBLE);
                   xRecyclerView_list.setVisibility(View.INVISIBLE);
               }else {
                   xRecyclerView_grid.setVisibility(View.INVISIBLE);
                   xRecyclerView_list.setVisibility(VISIBLE);
               }
           }
       });
    }

    private void getInfo() {
        imageView_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goods = editText_goods.getText().toString();
                Map<String,String> params=new HashMap<>();
                params.put("keywords",goods);
                params.put("page",mPage+"");
                iPresenter.startRequest(Path.path_goods,params, GoodsBean.class);
            }
        });

    }

    //获取资源ID
    private void initView(Bundle savedInstanceState) {
        imageView_search = findViewById(R.id.image_search);
        imageView_change=findViewById(R.id.image_change);
        editText_goods = findViewById(R.id.edit_goods);
        xRecyclerView_list = findViewById(R.id.x_recycler_list);
        xRecyclerView_grid=findViewById(R.id.x_recycler_grid);
    }

    //请求成功地数据
    @Override
    public void requestSuccess(Object data) {
          GoodsBean bean= (GoodsBean) data;
        List<GoodsBean.Data> data1 = bean.getData();
        if(mPage==1){
            adapter.setList(data1);
        }else {
            adapter.addList(data1);
        }
        mPage++;
        xRecyclerView_list.refreshComplete();
        xRecyclerView_list.loadMoreComplete();
    }

    //解除绑定
    @Override
    protected void onDestroy() {
        super.onDestroy();
        iPresenter.detchView();
    }
}
