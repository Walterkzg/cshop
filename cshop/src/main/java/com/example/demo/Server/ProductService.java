package com.example.demo.Server;

import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Product;

import java.util.List;

public interface ProductService {
    void add(Product product);

    void delete(int id);

    //条件分页列表查询
    PageBean<Product> list(Integer pageNum, Integer pageSize, Integer typeValue,String username);

    //更新头像
    void updateImg(String imgUrl, int pid);

    //更新数据
    void update(Product product);

    int getmax();
}
