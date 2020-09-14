package com.demo.good.entity;

import com.demo.good.annotation.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * good 表实体
 Create  By  Yaoping.NI
 */
@Table("good")//表示数据库表名是
public class Good extends BaseObject{

    /**
     * 货物单价
     */
	@Getter
	@Setter
    private Double price;
    /**
     * 货物库存
     */
    @Getter
   	@Setter
    private Integer stock;



    /**
     * 货物单重
     */
    @Getter
    @Setter
    private Double weight;

    /**
     * 货物名称
     */
    @Getter
    @Setter
    private String name;


    public Good(){

    }

    public Good(Integer id){
        this.id=id;

    }
}
