package com.demo.good.entity;


import com.demo.good.annotation.Column;
import com.demo.good.annotation.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 运输记录实体
 Create  By  Yaoping.NI
 */
@Table("transportation")//表示数据库表名是
public class Transportation extends BaseObject implements Serializable {


    /**
     * 交易记录id
     */
	@Getter
	@Setter
    @Column(name = "trade_id")
    private Integer tradeId;
    /**
     * 物流公司id
     */
    @Getter
   	@Setter
    @Column(name = "express_id")
    private Integer expressId;


    /**
     * 运输数量
     */
    @Getter
    @Setter
    @Column(name = "quantity")
    private Integer quantity;

    /**
     * 父运输表id
     */
    @Getter
    @Setter
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 运输状态，比如0表示准备中，1表示发货，2表示运输中等
     */
    @Getter
    @Setter
    @Column(name = "status")
    private Integer status;

    /**
     * 快递单号
     */
    @Getter
    @Setter
    @Column(name = "express_no")
    private Integer express_No;


    /**
     * 发货时间
     */
    @Getter
    @Setter
    @Column(name = "transportation_time")
    private String transportation_Time;

    public Transportation(){

    }
    public Transportation(Integer id){
        this.id=id;
    }

}
