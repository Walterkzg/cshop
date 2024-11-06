package com.example.demo.mapper;

import com.example.demo.pojo.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface productMapper {
    @Update("update product set photo = #{imgUrl} where pid=#{pid}")
    void updateImg(String imgUrl,int pid);

    List<Product> list(Integer typeValue, String username);

    @Update("update product set pname = #{productName} where pid = #{id}")
    int changePname(String productName, String id);
    @Update("update product set price = #{productPrice} where pid = #{id}")
    int changePprice(int productPrice,String id);

    @Insert("insert into product (pid,username,pname,price,ptype,photo) values (#{pid},#{username},#{pname},#{price},#{ptype},#{photo})")
    int add(Product product);

    @Delete("delete from product where pid = #{id}")
    int delete(int id);
    @Update("update product set pname=#{pname},price=#{price},ptype=#{ptype} where pid=#{pid}")
    void update(Product product);
    @Select("select max(pid) from product")
    int GetMax();
}
