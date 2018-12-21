package com.example.zhangjing20181217.bean;

import java.util.List;

public class GoodsBean {
    public String code;
    public List<Data> data;

    public String getCode() {
        return code;
    }

    public List<Data> getData() {
        return data;
    }

    public class Data{
        public String title;
        public String images;
        public Double price;
        public int pid;

        public String getTitle() {
            return title;
        }

        public String getImages() {
            return images;
        }

        public Double getPrice() {
            return price;
        }

        public int getPid() {
            return pid;
        }
    }

}
