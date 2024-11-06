package com.example.demo.Server;

import com.example.demo.pojo.Order;
import com.example.demo.pojo.PageBean;

import java.util.List;

public interface OrderService {
    PageBean<Order> list(Integer pageNum, Integer pagesize, Integer state,String username);

    public int addOrder(Order order);

    public List<Order> GetOrder(String username);
}
