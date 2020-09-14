package com.demo.good.entity;


import com.demo.good.annotation.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 基础数据实体
 Create  By  Yaoping.NI
 */
@Table("infra_info")//表示数据库表名是
public class InfraInfo extends BaseObject implements Serializable {
     public InfraInfo(){

     }
    public InfraInfo(Integer id,Integer category){
         this.id=id;
         this.category=category;

    }
    /**
     * 基础数据名称
     */
	@Getter
	@Setter
    private String name;
    /**
     * 类型
     */
    @Getter
   	@Setter
    private Integer category;

}
