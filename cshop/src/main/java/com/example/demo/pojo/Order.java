package com.example.demo.pojo;


import lombok.Data;

@Data
public class Order {
    private String pname;
    private String username;
    private String uname;
    private String oaddress;
    //物品数量
    private int num;
    private int price;
    //订单状态
    private int state;
    //下单时间
    private String otime;


}
