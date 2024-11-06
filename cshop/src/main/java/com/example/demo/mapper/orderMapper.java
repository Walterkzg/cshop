package com.example.demo.mapper;

import com.example.demo.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface orderMapper {

    List<Order> list(Integer state, String username) ;

    @Insert("insert into uorder(pid,username,uname,oaddress,num,price,state,otime,oid) values " +
            "(#{pid},#{username},#{uname},#{oaddresee},#{num},#{price},#{state},#{otime},#{oid})")
    int addOrder(Order order);
    @Select("select uname,oaddress,num,price,state,otime from uorder where username = #{username}")
    List<Order> GetOrder(String username);

    List<Order> list1(String username);
}
