package com.example.zhangjing20181217.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhangjing20181217.R;
import com.example.zhangjing20181217.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<GoodsBean.Data> list;
    private Context context;
    private ObjectAnimator animator;

    public RecyclerAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();//实例化List
    }

    public void setList(List<GoodsBean.Data> list) {
        this.list.clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<GoodsBean.Data> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
             viewHolder.textView_title.setText(list.get(i).getTitle());
             viewHolder.textView_price.setText(list.get(i).getPrice()+"");
            String images = list.get(i).getImages();
            String[] split = images.split("\\|");
            Glide.with(context).load(split[0]).into(viewHolder.imageView);

            //删除
       viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(itemClickListener!=null){

                    animator = ObjectAnimator.ofFloat(v, "alpha", 1.0f,0f);
                    animator.setDuration(3000);
                    itemClickListener.delData(i);


                }
                return true;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClick!=null){
                    itemClick.getChange(list.get(i).getPid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView_title,textView_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView_image);
            textView_price=itemView.findViewById(R.id.textView_price);
            textView_title=itemView.findViewById(R.id.textView_title);
        }
    }
    public longItemClickListener itemClickListener;
    public void setOnlongItemClickListener(longItemClickListener clickListener){
        itemClickListener=clickListener;
    }
    public interface longItemClickListener{
        void delData(int position);
    }
    //删除
    public void delItem(final int position){
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                list.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    //动画
    public void animatorItem(View view,int position){

    }
    public onItemClickListener itemClick;
    public void setOnItemClickListener(onItemClickListener onClickItem){
        itemClick=onClickItem;
    }

    //点击事件
    public interface onItemClickListener{
        void getChange(int poistion);
    }
}
