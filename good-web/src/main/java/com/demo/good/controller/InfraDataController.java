package com.demo.good.controller;


import com.demo.good.common.CommonResult;
import com.demo.good.entity.InfraInfo;

import com.demo.good.repository.IRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础数据操作Controller类
 * Create By yaoping.ni
 */
@RestController
@RequestMapping("/infra")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InfraDataController {
    @Autowired
    @Qualifier("infraInfoService")
    IRepository<InfraInfo> infraDataService;


 
    @RequestMapping(value = "/deleteInfraInfo/{id}", method = RequestMethod.DELETE)
    public CommonResult<String> deleteInfraData(@PathVariable int id) {

        infraDataService.delete(new InfraInfo(id,null));
        return CommonResult.success("success");
    }
    @RequestMapping(value = "/getInfraInfo/{id}", method = RequestMethod.GET)
    public CommonResult<InfraInfo> getInfraInfo(@PathVariable int id) {
        List<InfraInfo> lst=null;
        if((lst= infraDataService.select(new InfraInfo(id,null)))!=null &&  lst.size()>0){
            return CommonResult.success( lst.get(0));
        }
        return  CommonResult.success(null);

    }
    @RequestMapping(value = "/getInfraInfoList/{category}", method = RequestMethod.GET)
    public CommonResult<List<InfraInfo>> getInfraInfoDtoList(@PathVariable int category ) {
        return CommonResult.success( infraDataService.select(new InfraInfo(null,category)));
    }

    @RequestMapping(value = "/createInfraInfo", method = RequestMethod.POST)
    public CommonResult<String> createInfraInfo(@RequestBody InfraInfo infraInfo) {
        infraDataService.insert(infraInfo);
        return CommonResult.success("success");
    }

    @RequestMapping(value = "/updateInfraInfo", method = RequestMethod.POST)
    public CommonResult<String> updateInfraInfo(@RequestBody InfraInfo infraInfo) {
        infraDataService.update(infraInfo);
        return CommonResult.success("success");
    }
}
