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
@Table("trade")//表示数据库表名是
public class Trade extends BaseObject implements Serializable {


    /**
     * 货物id
     */
	@Getter
	@Setter
    @Column(name = "good_id")
    private Integer goodId;
    /**
     * 总价格
     */
    @Getter
   	@Setter
    @Column(name = "total_price")
    private Double totalPrice;


    /**
     * 运输总数量
     */
    @Getter
    @Setter
    @Column(name = "total_quantity")
    private Integer totalQuantity;



    /**
     * 总重量
     */
    @Getter
    @Setter
    @Column(name = "total_weight")
    private Double totalWight;

    public  Trade(Integer id){this.id=id;}

    public Trade(){

    }
}
