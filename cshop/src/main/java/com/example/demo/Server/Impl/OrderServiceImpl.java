package com.example.demo.Server.Impl;

import com.example.demo.Server.OrderService;
import com.example.demo.mapper.orderMapper;
import com.example.demo.pojo.Order;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Product;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private orderMapper oMapper;

    @Override
    public PageBean<Order> list(Integer pageNum, Integer pageSize, Integer state, String username) {
        PageBean<Order> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用mapper
        List<Order> ps = oMapper.list(state,username);
        Page<Order> p = (Page<Order>) ps;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
    @Override
    public int addOrder(Order order) {
         return oMapper.addOrder(order);

    }

    @Override
    public List<Order> GetOrder(String username) {
        return oMapper.GetOrder(username);
    }


    public PageBean<Order> list1(Integer pageNum, Integer pageSize, String username) {
        PageBean<Order> pb = new PageBean<>();
        //开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        //调用mapper
        List<Order> ps = oMapper.list1(username);
        Page<Order> p = (Page<Order>) ps;

        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }
}
