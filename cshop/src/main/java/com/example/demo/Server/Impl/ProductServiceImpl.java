package com.example.demo.Server.Impl;

import com.example.demo.Server.ProductService;
import com.example.demo.mapper.productMapper;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Product;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private productMapper pMapper;
    @Override
    public void add(Product product) {
         pMapper.add(product);
    }

    @Override
    public void delete(int id) {
        pMapper.delete(id);
    }

    @Override
    public PageBean<Product> list(Integer pageNum, Integer pageSize, Integer typeValue,String username) {
        PageBean<Product> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用mapper
        List<Product> ps = pMapper.list(typeValue,username);
        Page<Product> p = (Page<Product>) ps;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public void updateImg(String imgUrl, int pid) {
        pMapper.updateImg(imgUrl,pid);
    }

    @Override
    public void update(Product product) {
        pMapper.update(product);
    }

    @Override
    public int getmax() {
       return pMapper.GetMax();
    }


}
