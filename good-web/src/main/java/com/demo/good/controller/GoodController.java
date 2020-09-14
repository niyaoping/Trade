package com.demo.good.controller;


import com.demo.good.common.CommonResult;
import com.demo.good.entity.Good;
import com.demo.good.entity.InfraInfo;
import com.demo.good.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 货物操作Controller类
 * Create By yaoping.ni
 */
@RestController
@RequestMapping("/good")
@CrossOrigin(origins = "*", maxAge = 3600)
public class GoodController {
    @Autowired
    @Qualifier("goodService")
    IRepository<Good> goodService;


 
    @RequestMapping(value = "/deleteGood/{id}", method = RequestMethod.DELETE)
    public CommonResult<String> deleteGood(@PathVariable int id) {
        goodService.delete(new Good(id));
        return CommonResult.success("success");
    }
    @RequestMapping(value = "/getGood/{id}", method = RequestMethod.GET)
    public CommonResult<Good> getGood(@PathVariable int id) {
        List<Good> lst=null;
        if((lst= goodService.select(new Good(id)))!=null &&  lst.size()>0){
            return CommonResult.success( lst.get(0));
        }
        return  CommonResult.success(null);

    }

    @RequestMapping(value = "/createGood", method = RequestMethod.POST)
    public CommonResult<String> createGood(@RequestBody Good good) {
        goodService.insert(good);
        return CommonResult.success("success");
    }

    @RequestMapping(value = "/updateGood", method = RequestMethod.POST)
    public CommonResult<String> updateGood(@RequestBody Good good) {
        goodService.update(good);
        return CommonResult.success("success");
    }
}
