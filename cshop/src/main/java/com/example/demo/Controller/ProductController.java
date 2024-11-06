package com.example.demo.Controller;

import com.example.demo.Server.ProductService;
import com.example.demo.pojo.PageBean;
import com.example.demo.pojo.Product;
import com.example.demo.pojo.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PutMapping("/add")
    public Result add(@RequestBody Product product){
        int num =productService.getmax();
        product.setPid(num+1);
        productService.add(product);
        return Result.success();
    }


    @DeleteMapping("/delete")
    public String delete(int id){
        productService.delete(id);
        return "删除成功";
    }

    //获取分页数据
    @GetMapping("/getPage")
    public Result<PageBean<Product>> list(
            Integer pageNum,
            Integer pageSize,
            String username,
            @RequestParam(required = false) Integer typeValue
    ){
       PageBean<Product> Pro = productService.list(pageNum,pageSize,typeValue,username);
       return Result.success(Pro);
    }

    //更新图片
    @PatchMapping("/updateImg")
    public Result updateImg(@RequestParam String imgUrl,int pid){
        productService.updateImg(imgUrl,pid);
        return Result.success();
    }

    //更新数据
    @PutMapping("/update")
    public Result update(@RequestBody Product product){
        System.out.println(product);
        productService.update(product);
        return Result.success();
    }



}
