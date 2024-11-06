package com.example.demo.Controller;

import com.example.demo.Server.Impl.OrderServiceImpl;
import com.example.demo.Server.OrderService;
import com.example.demo.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderService;

    @PutMapping("/getOrder")
    public List<Order> GetOrder(String username) {
        return orderService.GetOrder(username);
    }

    @GetMapping("/getOrderByPage")
    public Result<PageBean<Order>> GetOrderByPage(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer state,
            String username
    ) {
        PageBean<Order> Po;
        if(state == 3) {
                Po = orderService.list1(pageNum, pageSize, username);
        }else{
                Po = orderService.list(pageNum,pageSize,state,username);
        }
        System.out.println(state + username);
        return Result.success(Po);
    }
}
