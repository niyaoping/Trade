package com.demo.good.controller;


import com.demo.good.common.CommonResult;
import com.demo.good.common.OperationEnum;
import com.demo.good.entity.Good;
import com.demo.good.entity.Trade;
import com.demo.good.entity.Transportation;
import com.demo.good.entity.vo.SplitVO;
import com.demo.good.entity.vo.TradeVO;
import com.demo.good.repository.IRepository;
import com.demo.good.service.ITradeServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易运输批次操作Controller类
 * Create By yaoping.ni
 */
@RestController
@RequestMapping("/trade")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TradeController {
    @Autowired
    ITradeServcie tradeServcieImpl;


 
    @RequestMapping(value = "/deleteTrade/{id}", method = RequestMethod.DELETE)
    public CommonResult<String> deleteTrade(@PathVariable int id) {
        tradeServcieImpl.deleteTrade(id);
        return CommonResult.success("success");
    }
    @RequestMapping(value = "/getTrade/{id}", method = RequestMethod.GET)
    public CommonResult<Trade> getTrade(@PathVariable int id) {
        return CommonResult.success(  tradeServcieImpl.getTrade(id));


    }

    /*
    url:localhost:8084/good/trade/createTrade
    body内容：
      {
        "trade": {
            "good_Id": 1,--货物id
            "total_Price": 1234.45,--该批货物总价格
            "total_Quantity": 400,--该批货物总数量
            "total_Wight": 12344.5--该批货物重量
        },
        "transportation": {
            "quantity": 400--默认第一次保存的时候在transportation表中保存一条运输记录，后续可以对个该记录进行拆分

        }
    }
     */
    @RequestMapping(value = "/createTrade", method = RequestMethod.POST)
    public CommonResult<String> createTrade(@RequestBody TradeVO tradeVO) {
        tradeServcieImpl.saveTrade(tradeVO.getTrade(),tradeVO.getTransportation());
        return CommonResult.success("success");
    }


    /**
    body内容：
     {
     "id":33,--表示要分割的记录id
     "quantity": [--表示分割中几份，每份多少个数量
     25,
     25
     ]
     }
     */
    @RequestMapping(value = "/split", method = RequestMethod.POST)
    public CommonResult<String> split(@RequestBody SplitVO splitVO) {
        tradeServcieImpl.split(splitVO.getId(),splitVO.getQuantity());
        return CommonResult.success("success");
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public CommonResult<TradeVO> test() {
        TradeVO vo=new TradeVO();
        Trade t=new Trade();
        t.setId(1);
        t.setTotalPrice(1234.45);
        t.setTotalQuantity(400);
        t.setTotalWight(12344.5);
        vo.setTrade(t);
        Transportation tt=new Transportation();
        tt.setQuantity(400);
        vo.setTransportation(tt);

        return CommonResult.success(vo);
    }


    /*
    url:http://localhost:8084/good/trade/merge
    body内容：
    [
        "1",
        "2",
        "3"
    ]
     */
    @RequestMapping(value = "/merge", method = RequestMethod.POST)
    public CommonResult<String> merge(@RequestBody List<String> ids) {
        tradeServcieImpl.merge(ids);
        return CommonResult.success("success");
    }

    //url:http://localhost:8084/good/trade/updateQuantityAdd/8/124表示对于id为8的交易记录，整体新增124个数量
    /**
     *进行整体加
     *
     * @param id
     * @param changeQuantity
     * @return
     */
    @RequestMapping(value = "/updateQuantityAdd/{id}/{changeQuantity}", method = RequestMethod.GET)
    public CommonResult<String> updateQuantityAdd(@PathVariable Integer id ,@PathVariable Integer changeQuantity) {
        tradeServcieImpl.updateQuantity(id,changeQuantity, OperationEnum.add);
        return CommonResult.success("success");
    }


    //url:http://localhost:8084/good/trade/updateQuantitySubtract/8/29表示对于id为8的交易记录，整体减29个数量
    /**
     *进行整体减
     *
     * @param id
     * @param changeQuantity
     * @return
     */
    @RequestMapping(value = "/updateQuantitySubtract/{id}/{changeQuantity}", method = RequestMethod.GET)
    public CommonResult<String> updateQuantitySubtract(@PathVariable Integer id ,@PathVariable Integer changeQuantity) {
        tradeServcieImpl.updateQuantity(id,changeQuantity, OperationEnum.subtract);
        return CommonResult.success("success");
    }


    //url:http://localhost:8084/good/trade/updateQuantityMultiply/8/2表示对于id为8的交易记录，整体增加2倍数量
    /**
     *进行整体加倍
     *
     * @param id
     * @param changeQuantity
     * @return
     */
    @RequestMapping(value = "/updateQuantityMultiply/{id}/{changeQuantity}", method = RequestMethod.GET)
    public CommonResult<String> updateQuantityMultiply(@PathVariable Integer id ,@PathVariable Integer changeQuantity) {
        tradeServcieImpl.updateQuantity(id,changeQuantity, OperationEnum.multiply);
        return CommonResult.success("success");
    }

    /**
     *进行整体除
     *
     * @param id
     * @param changeQuantity
     * @return
     */
    @RequestMapping(value = "/updateQuantityDivide/{id}/{changeQuantity}", method = RequestMethod.GET)
    public CommonResult<String> updateQuantityDivide(@PathVariable Integer id ,@PathVariable Integer changeQuantity) {
        tradeServcieImpl.updateQuantity(id,changeQuantity, OperationEnum.divide);
        return CommonResult.success("success");
    }
}
